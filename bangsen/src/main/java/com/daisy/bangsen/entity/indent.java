package com.daisy.bangsen.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "t_indent")
@TableName(value="t_indent")
@Data
public class indent {
    @TableId("id")
    @Id
    String id;
    @Column(length = 100)
    String purchaseRequestDate;
    @Column(length = 100)
    String departmentNumber;
    @Column(length = 100)
    String purchaseName;
    @Column(length = 100)
    String uptime;
}
