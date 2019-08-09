package com.daisy.myblog.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tags")
@TableName(value="tags")
@Data
public class Tags {
    @TableId("id")
    @Id
    private Long id;
    private String tagName;
}
