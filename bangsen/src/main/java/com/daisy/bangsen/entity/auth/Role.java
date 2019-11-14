package com.daisy.bangsen.entity.auth;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @desc 角色
 * @author daisy
 * @date 2019/08/19
 */
@Entity
@Table(name = "t_role")
@TableName(value="t_role")
@Data
public class Role {
    @TableId("id")
    @Id
    String id;
    @Column(length = 30)
    String name; //角色名称
}
