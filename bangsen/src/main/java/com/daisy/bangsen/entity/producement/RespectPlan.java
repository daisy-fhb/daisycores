package com.daisy.bangsen.entity.producement;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.*;

/**
 * 计划产量
 */
@Entity
@Table(name = "t_respectplan")
@TableName(value = "t_respectplan")
@Data
public class RespectPlan {
    @TableId("id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //按数据库策略递增
            Long id;
            long jan;
            long feb;
            long march;
            long april;
            long may;
            long jun;
            long july;
            long aug;
            long sep;
            long oct;
            long nov;
            long dece;
}
