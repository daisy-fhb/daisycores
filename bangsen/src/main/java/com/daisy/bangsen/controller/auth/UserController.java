package com.daisy.bangsen.controller.auth;

import cn.hutool.json.JSONObject;
import com.daisy.bangsen.service.UserService;
import com.daisy.bangsen.util.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@CrossOrigin
public class UserController {

    @Autowired
    UserService userService;

     @RequestMapping(value = "/regist" , method = RequestMethod.POST)
     public RespBean regist(@RequestBody String jsondata){
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
     public RespBean query(String name){
        RespBean re;
        JSONObject jsondata=new JSONObject();
        jsondata.put("name",name);
            re=userService.query(jsondata.toString());
        return re;
    }

    @RequestMapping(value = "/signin" , method = RequestMethod.POST)
     public RespBean signin(@RequestBody String jsondata){
        return  userService.signin(jsondata);
    }

    @RequestMapping(value = "/signout" , method = RequestMethod.POST)
     public RespBean signup(@RequestBody String jsondata){
        return  userService.signup(jsondata);
    }

    @RequestMapping(value = "/roles" , method = RequestMethod.GET)
     public RespBean roles(){
        RespBean  re=userService.getRoles();
        return re;
    }
}
