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
@Table(name = "leavemsg")
@TableName(value="leavemsg")
@Data
public class LeaveMsg {
    @TableId("id")
    @Id
    String id;
    @Column(length = 200)
    String content;
    @Column(length = 100)
    String areainfo;
    Timestamp time;
}
