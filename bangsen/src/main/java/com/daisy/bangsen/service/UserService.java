package com.daisy.bangsen.service;


import com.daisy.bangsen.util.RespBean;

public interface UserService {
    RespBean save(String postData);

    RespBean delete(String id);

    RespBean update(String postData);

    RespBean query(String postData);

    RespBean signin(String jsondata);

    RespBean signout(String jsondata);

    RespBean getRoles();

    RespBean getEmailCode(String email);

    RespBean validationName(String name);

    RespBean validationEmail(String postData);

    RespBean validationEmailCode(String postData);

    RespBean resetPassword(String postData);
}
