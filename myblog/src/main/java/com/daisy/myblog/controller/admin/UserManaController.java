package com.daisy.myblog.controller.admin;

import com.daisy.myblog.entity.User;
import com.daisy.myblog.service.Impl.UserServiceImpl;
import com.daisy.myblog.util.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by daisy
 */
@RestController
@CrossOrigin
@RequestMapping("/admin")
public class UserManaController {
    @Autowired
    UserServiceImpl userService;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public List<User> getUserByNickname(String nickname) {
        return userService.getUserByNickname(nickname);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }


    @RequestMapping(value = "/user/enabled", method = RequestMethod.PUT)
    public RespBean updateUserEnabled(Boolean enabled, Long uid) {
        if (userService.updateUserEnabled(enabled, uid) == 1) {
            return new RespBean(true,"success", "更新成功!");
        } else {
            return new RespBean(false,"error", "更新失败!");
        }
    }

    @RequestMapping(value = "/user/{uid}", method = RequestMethod.DELETE)
    public RespBean deleteUserById(@PathVariable Long uid) {
        if (userService.deleteUserById(uid) == 1) {
            return new RespBean(true,"success", "删除成功!");
        } else {
            return new RespBean(false,"error", "删除失败!");
        }
    }
}
