package com.daisy.bangsen.entity.bussiness;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.*;


/**
 * 库存单
 */
@Entity
@Table(name = "t_inventory")
@TableName(value = "t_inventory")
@Data
public class Inventory {
    @TableId("id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //按数据库策略递增
    private Long id;
    String receiptNumber; //票据号
    String serialNumber; //物品编号
    String serialName; //物品名称
    String warehouseName; //库房名称
    String models; //型号
    long count; //数量
    long failCount; //不合格数量
    long avaliable; //可用数量
    String producedDate; //生产日期
}
