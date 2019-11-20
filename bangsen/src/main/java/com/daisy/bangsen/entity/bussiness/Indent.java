package com.daisy.bangsen.entity.bussiness;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;


/**
 * 采购单
 */
@Entity
@Table(name = "t_indent")
@TableName(value = "t_indent")
@Data
public class Indent {
    @TableId("id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //按数据库策略递增
    private Long id;
    long count; //数量
    BigDecimal unitPrice; //单价
    BigDecimal price; //价格
    BigDecimal taxRate; //税率
    String paymentStatus;//付款状态
    int purchaseStatus;//采购状态
    String purchaseRequestDate;//申请采购日期
    String departmentNumber; //部门编号
    String purchaseName; //采购品名
    String supplierName; //供应商名称
    String transportMode; //运输方式
    String warehouseName; //库房
    String purchaseRequirementDate; //采购需求日期
    String executiveBuyer; //请购人
    String receiptNumber; //票据号
}
