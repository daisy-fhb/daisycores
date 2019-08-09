package com.daisy.myblog.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "category")
@TableName(value="category")
@Data
public class Category {
    @TableId("id")
    @Id
    private Long id;
    @Column(name="cateName")
    private String cateName;
    private Timestamp date;
}
