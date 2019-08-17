package com.daisy.myblog.service.Impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.daisy.myblog.dao.UserMapper;
import com.daisy.myblog.entity.LeaveMsg;
import com.daisy.myblog.entity.User;
import com.daisy.myblog.service.UserService;
import com.daisy.myblog.util.IPUtils;
import com.daisy.myblog.util.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by daisy.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;


    /**
     * @param user
     * @return 0表示成功
     * 1表示用户名重复
     * 2表示失败
     */
    @Override
    public int reg(User user) {
        User loadUserByUsername = userMapper.loadUserByUsername(user.getUsername());
        if (loadUserByUsername != null) {
            return 1;
        }
        //插入用户,插入之前先对密码进行加密
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        user.setEnabled(true);//用户可用
        long result = userMapper.reg(user);
        //配置用户的角色，默认都是普通用户
        String[] roles = new String[]{"2"};
        boolean b =result == 1;
        if (b) {
            return 0;
        } else {
            return 2;
        }
    }

    @Override
    public List<User> getUserByNickname(String nickname) {
        List<User> list = userMapper.getUserByNickname(nickname);
        return list;
    }

    @Override
    public int updateUserEnabled(Boolean enabled, Long uid) {
        return userMapper.updateUserEnabled(enabled, uid);
    }

    @Override
    public int deleteUserById(Long uid) {
        return userMapper.deleteUserById(uid);
    }

    @Override
    public User getUserById(Long id) {
        return userMapper.getUserById(id);
    }

    @Override
    public RespBean addLeaveMsg(String jsondata, HttpServletRequest request) {
        try {
            String decode= URLUtil.decode(jsondata);
            JSONObject jo= JSONUtil.parseObj(decode.substring(0,decode.lastIndexOf("=")));
            JSONObject area= IPUtils.getRequestIPInfo(request);
            StringBuffer sb=new StringBuffer();
            sb.append(area.getStr("Area"));
            sb.append(" - ");
            sb.append(area.getStr("Os"));
            sb.append(" - ");
            sb.append(area.getStr("Browser"));
            Map param=new HashMap();
            param.put("id",IdUtil.fastUUID().split("-")[1]);
            param.put("content",jo.getStr("data"));
            param.put("areainfo",sb.toString());
            param.put("time", DateUtil.now());
            int re= userMapper.addLeaveMsg(param);
            if (re>0){
                return new RespBean(true,"success", "留言成功!");
            }else{
                return new RespBean(false,"failed", "留言失败!");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new RespBean(false,"failed", "留言失败!"+e.getMessage());
        }
    }

    @Override
    public RespBean getlmsg() {
        try {
            List<HashMap> result=userMapper.getlmsg();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (HashMap h:result){
                h.put("time",DateUtil.format(sdf.parse(h.get("time").toString()),"yyyy-MM-dd HH:mm:ss"));
            }
            return new RespBean(true,"success", JSONUtil.parseArray(result).toString());
        }catch (Exception e){
            e.printStackTrace();
            return new RespBean(false,"failed", e.getMessage());
        }
    }
}
