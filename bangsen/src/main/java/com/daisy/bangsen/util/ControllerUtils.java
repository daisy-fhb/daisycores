package com.daisy.bangsen.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class ControllerUtils {

    @Autowired
    RedisTemplate redisTemplate;

    public RespBean auth(HttpServletRequest request, HttpServletResponse response){
        RespBean re=new RespBean();
        String token=request.getHeader("token");
        String userid=request.getHeader("userid");
        Map<String, String> TokenUserMap= redisTemplate.opsForHash().entries("TokenUserMap");
        Map<String, String> CurrentUserMap= redisTemplate.opsForHash().entries("CurrentUserMap");

        if (!CurrentUserMap.containsKey(userid)){
            re.setStatus(401);
            re.setMsg("请先登录");
        }else{
            if (TokenUserMap.containsKey(userid)){
                if (!token.equals(TokenUserMap.get(userid))){
                    re.setStatus(401);
                    re.setMsg("登录信息失效");
                }else{
                    re.setStatus(200);
                }
            }else{
                re.setStatus(403);
                re.setMsg("无接口访问权限");
            }
        }
        return re;
    }
}
