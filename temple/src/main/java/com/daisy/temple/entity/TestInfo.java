package com.daisy.temple.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_test_info")
@TableName(value="t_test_info")
@Data
public class TestInfo {
    @TableId("id")
    @Id
    String id;
    @Column(length = 20)
    String model;
    @Column(length = 20)
    String type;
    @Column(length = 200)
    String filename;
    @Column(length = 50)
    String uptime;
}
