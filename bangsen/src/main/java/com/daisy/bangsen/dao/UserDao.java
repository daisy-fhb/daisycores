package com.daisy.bangsen.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.daisy.bangsen.entity.auth.Role;
import com.daisy.bangsen.entity.auth.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Mapper
@Repository
public interface UserDao extends BaseMapper<User> {

    List<User> selectUser(HashMap paraMap);

    List<Role> selectRoles();
}
