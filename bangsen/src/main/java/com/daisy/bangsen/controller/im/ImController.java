package com.daisy.bangsen.controller.im;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/daisy/{userId}")
@Component
public class ImController {
    //静态变量，用来记录当前在线连接数
    private static int onlineCount = 0;
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    //使用map对象，便于根据userId来获取对应的WebSocket
    private static ConcurrentHashMap<String, ImController> websocketList = new ConcurrentHashMap<>();
    //接收sid
    private String userId="";
    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session,@PathParam("userId") String userId) {
        this.session = session;
        websocketList.put(userId,this);
         System.out.println("在线用户信息为:"+ websocketList.keySet());
        addOnlineCount();           //在线数加1
         System.out.println("有新窗口开始监听:"+userId+",当前在线人数为" + getOnlineCount());
        this.userId=userId;
        try {
            sendMessage("连接成功");
        } catch (Exception e) {
            System.out.println("websocket IO异常");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if(websocketList.get(this.userId)!=null){
            websocketList.remove(this.userId);
            subOnlineCount();           //在线数减1
             System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {
         System.out.println("收到来自窗口"+userId+"的信息:"+message);
        if(StringUtils.isNotBlank(message)){
                try {
                    //解析发送的报文
                    JSONObject object =JSONUtil.createObj();
                    object.put("fromUserId",this.userId);
                    object.put("msg",message);
//                    sendInfo(message,"6"); //传送给对应用户的websocket
                    sendInfo(message,""); //推送给所有客户端
                }catch (Exception e){
                    e.printStackTrace();
                }
        }
    }

    /**
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }
    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) {
        this.session.getAsyncRemote().sendText(message);
    }


    /**
     * 群发自定义消息
     * */
    public static void sendInfo(String message,String userId) throws IOException {
         System.out.println("推送消息到窗口"+userId+"，推送内容:"+message);
        for (Map.Entry<String, ImController> entry: websocketList.entrySet()) {
            try {
                if (StringUtils.isBlank(userId)){
                    entry.getValue().sendMessage(message);
                }else if(entry.getKey().equals(userId)){
                    entry.getValue().sendMessage(message);
                }
            } catch (Exception e) {
                continue;
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        ImController.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        ImController.onlineCount--;
    }
   }
