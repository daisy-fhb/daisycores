package com.daisy.bangsen.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @desc 部门
 * @author daisy
 * @date 2019/08/19
 */
@Entity
@Table(name = "t_department")
@TableName(value="t_department")
@Data
public class Department {
    @TableId("id")
    @Id
    String id;
    @Column(length = 20)
    String name;  //部门名称
    @Column(length = 20)
    String parentid; //父部门id
    @Column(length = 30)
    String address; //地址
    @Column(length = 30)
    String mark; //备注
}
