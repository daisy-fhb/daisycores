package com.daisy.bangsen.entity.bussiness;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;


/**
 * 供应商价格单
 */
@Entity
@Table(name = "t_supplierprice")
@TableName(value="t_supplierprice")
@Data
public class SupplierPrice {
    @TableId("id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //按数据库策略递增
    private Long id;
    BigDecimal unitPrice; //单价
    BigDecimal price; //价格
    BigDecimal taxRate; //税率
    BigDecimal tax; //税费
    long countMax;//数量上限
    long countMin;//数量下限
    String itemName;//物品名
    String supplierName; //供应商名称
    String receiptNumber; //票据号
}
