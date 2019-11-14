package com.daisy.bangsen.entity.bussiness;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;


/**
 * 销售单
 */
@Entity
@Table(name = "t_csale")
@TableName(value = "t_csale")
@Data
public class Csale {
    @TableId("id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //按数据库策略递增
    private Long id;
    String receiptNumber; //票据号
    String supplierName; //供应商名称
    String departmentNumber; //部门编号
    String salesName; //销售品名称
    String contractNumber; //合同号
    String salesDate; //销售日期
    String estimateDate; //预计完工日期
    String estimateSendDate; //预计发货日期
    String consigneeName; //收货人姓名
    String consigneePhone; //收货人电话
    String customerName; //客户公司名称
    long count; //数量
    BigDecimal quotation; //报价
    BigDecimal price; //单价
    BigDecimal taxRate; //税率
    BigDecimal tax; //税费
    BigDecimal discountRate; //折扣率
    BigDecimal discount; //折扣额
    BigDecimal unitPrice; //单价
    BigDecimal realTotalSales; //实际销售总价
    String transportMode; //运输方式
    int isPaid; //是否付款
    String salesDirector; //销售主管
    String salesStatus; //销售状态
}
