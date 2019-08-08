package com.daisy.myblog.dao;

import com.daisy.myblog.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by daisy.
 */
@Mapper
@Repository
public interface UserMapper {

    User loadUserByUsername(@Param("username") String username);

    long reg(User user);

    List<User> getUserByNickname(@Param("nickname") String nickname);

    int updateUserEnabled(@Param("enabled") Boolean enabled, @Param("uid") Long uid);

    int deleteUserById(Long uid);

    User getUserById(@Param("id") Long id);
}
