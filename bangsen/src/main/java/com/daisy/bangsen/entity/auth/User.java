package com.daisy.bangsen.entity.auth;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;


/**
 * @desc 用户
 * @author daisy
 * @date 2019/08/19
 */
@Entity
@Table(name = "t_user")
@TableName(value="t_user")
@Data
public class User {
    @TableId("id")
    @Id
    long id;
    @Column(length = 20)
    String name;   //姓名
    String account;   //姓名
    int sex;    //性别
    @Column(length = 30)
    String birthday;  //生日
    @Column(length = 30)
    String interviewtime; //入职时间
    @Column(length = 10)
    String education; //学历
    @Column(length = 30)
    String major; //专业
    @Column(length = 5)
    String ishire; //是否聘用
    @Column(length = 30)
    String phone; //电话
    @Column(length = 30)
    String password; //密码
    @Column(length = 30)
    String deptid; //部门id
    @Column(length = 30)
    String roleid; //角色id
    String email; //邮箱
    String emailcode; //邮箱验证码
    Timestamp uptime;
}
