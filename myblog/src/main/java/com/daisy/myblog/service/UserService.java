package com.daisy.myblog.service;

import com.daisy.myblog.entity.User;

import java.util.List;

public interface UserService {
     int reg(User user);

     List<User> getUserByNickname(String nickname);

     int updateUserEnabled(Boolean enabled, Long uid);

     int deleteUserById(Long uid);

     User getUserById(Long id);
}
