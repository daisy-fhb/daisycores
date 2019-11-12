package com.daisy.bangsen.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.daisy.bangsen.service.UserService;
import com.daisy.bangsen.util.CaptchaUtils;
import com.daisy.bangsen.util.ControllerUtils;
import com.daisy.bangsen.util.DataCacheUtil;
import com.daisy.bangsen.util.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("user")
@CrossOrigin
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    ControllerUtils controllerUtils;



    @RequestMapping(value = "/login/authcode" , method = RequestMethod.GET)
     public void authcode(HttpServletRequest request, HttpServletResponse response){
        String timecode=request.getHeader("timecode");
        DataCacheUtil.aucodeMap.put(timecode, CaptchaUtils.getCaptCha(response));
    }

     @RequestMapping(value = "/regist" , method = RequestMethod.POST)
     public RespBean regist(@RequestBody String jsondata){
        JSONObject jo= JSONUtil.parseObj(jsondata);
        if (!jo.containsKey("timecode")||!jo.getStr("authcode").equals(DataCacheUtil.aucodeMap.get(jo.getStr("timecode")))){
            return new RespBean(2000,"验证码错误",null);
        }
        return userService.save(jsondata);
    }

    @RequestMapping(value = "/del" , method = RequestMethod.DELETE)
     public RespBean del(@RequestBody String jsondata){
        return userService.delete(jsondata);
    }

    @RequestMapping(value = "/update" , method = RequestMethod.PUT)
     public RespBean update(@RequestBody String jsondata){
        return userService.update(jsondata);
    }

    @RequestMapping(value = "/query" , method = RequestMethod.GET)
     public RespBean query(@RequestParam String name, HttpServletRequest request, HttpServletResponse response){
        RespBean re;
        JSONObject jsondata=new JSONObject();
        jsondata.put("name",name);
        RespBean authre=controllerUtils.auth(request,response);
        if (authre.getStatus()==200){
            re=userService.query(jsondata.toString());
        }else{
            re=authre;
        }
        return re;
    }

    @RequestMapping(value = "/signin" , method = RequestMethod.POST)
     public RespBean signin(@RequestBody String jsondata){
        JSONObject jo= JSONUtil.parseObj(jsondata);
        if (!jo.containsKey("timecode")||!jo.getStr("authcode").equals( DataCacheUtil.aucodeMap.get(jo.getStr("timecode")))){
            return new RespBean(2000,"验证码错误",null);
        }
        return  userService.signin(jsondata);
    }

    @RequestMapping(value = "/signout" , method = RequestMethod.POST)
     public RespBean signup(@RequestBody String jsondata){
        return  userService.signup(jsondata);
    }

    @RequestMapping(value = "/roles" , method = RequestMethod.GET)
     public RespBean signup(HttpServletRequest request, HttpServletResponse response){
        RespBean re;
        RespBean authre=controllerUtils.auth(request,response);
        if (authre.getStatus()==200){
            re=userService.getRoles();
        }else{
            re=authre;
        }
        return re;
    }
}
