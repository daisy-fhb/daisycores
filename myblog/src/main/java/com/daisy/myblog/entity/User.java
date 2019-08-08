package com.daisy.myblog.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;


@Entity
@Table(name = "user")
@TableName(value="user")
@Data
public class User {
    @TableId("id")
    @Id
    private Long id;
    private String username;
    private String password;
    private String nickname;
    private boolean enabled;
    private String email;
    private String userface;
    private Timestamp regTime;
}
