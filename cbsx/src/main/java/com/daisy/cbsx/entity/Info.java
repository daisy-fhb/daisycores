package com.daisy.cbsx.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "t_info")
@TableName(value = "t_info")
@Data
public class Info {
    @Id
    @GeneratedValue
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
