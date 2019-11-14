package com.daisy.bangsen.entity.bussiness;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.*;


/**
 * 物资调拨单
 */
@Entity
@Table(name = "t_allocation")
@TableName(value = "t_allocation")
@Data
public class Allocation {
    @TableId("id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //按数据库策略递增
    private Long id;
    String receiptNumber; //票据号
    String serialName; //出入库物品名称
    String intoWarehouse; //调入仓库
    String rollOffWarehouse; //调出仓库
    String allocateDate; //调拨日期
    String mark; //备注
    long count; //数量
}
