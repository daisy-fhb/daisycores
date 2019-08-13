package com.daisy.myblog.controller;

import com.daisy.myblog.service.Impl.UserServiceImpl;
import com.daisy.myblog.util.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by daisy
 */
@RestController
@CrossOrigin
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @RequestMapping("/currentUserName")
    public String currentUserName() {
        return "daisy";
    }

    @RequestMapping("/currentUserId")
    public Long currentUserId() {
        return 1L;
    }

    @RequestMapping("/isAdmin")
    public Boolean isAdmin() {
        return true;
    }
}
