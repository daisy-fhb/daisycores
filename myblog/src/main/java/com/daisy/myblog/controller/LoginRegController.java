package com.daisy.myblog.controller;

import com.daisy.myblog.entity.User;
import com.daisy.myblog.service.Impl.UserServiceImpl;
import com.daisy.myblog.util.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by daisy.
 */
@RestController
@CrossOrigin
public class LoginRegController {

    @Autowired
    UserServiceImpl userService;

    @RequestMapping("/login_error")
    public RespBean loginError() {
        return new RespBean(false,"error", "登录失败!");
    }

    @RequestMapping("/login_success")
    public RespBean loginSuccess() {
        return new RespBean(true,"success", "登录成功!");
    }

    /**
     * 如果自动跳转到这个页面，说明用户未登录，返回相应的提示即可
     * <p>
     * 如果要支持表单登录，可以在这个方法中判断请求的类型，进而决定返回JSON还是HTML页面
     *
     * @return
     */
    @RequestMapping("/login_page")
    public RespBean loginPage() {
        return new RespBean(false,"error", "尚未登录，请登录!");
    }

    @RequestMapping("/reg")
    public RespBean reg(User user) {
        int result = userService.reg(user);
        if (result == 0) {
            //成功
            return new RespBean(true,"success", "注册成功!");
        } else if (result == 1) {
            return new RespBean(false,"error", "用户名重复，注册失败!");
        } else {
            //失败
            return new RespBean(false,"error", "注册失败!");
        }
    }

    @RequestMapping(value = "leavemsg", method = RequestMethod.POST)
    RespBean leavemsg(@RequestBody String data, HttpServletRequest request){
        return userService.addLeaveMsg(data,request);
    }

    @RequestMapping(value = "getlmsg", method = RequestMethod.GET)
    RespBean getlmsg(){
        return userService.getlmsg();
    }

    @RequestMapping("/login")
    public RespBean login(User user) {
        return new RespBean(true,"success", "注册成功!");
    }
}
