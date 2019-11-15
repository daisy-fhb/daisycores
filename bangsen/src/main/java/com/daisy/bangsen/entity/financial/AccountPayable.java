package com.daisy.bangsen.entity.financial;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;


/**
 * 应付单
 */
@Entity
@Table(name = "t_accountpayable")
@TableName(value = "t_accountpayable")
@Data
public class AccountPayable {
    @TableId("id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //按数据库策略递增
    private Long id;
    String projectName; //项目名称
    String supplierName; //客商名称
    BigDecimal procurement; //原始金额
    BigDecimal originalAmount; //原始金额
    BigDecimal paidAmount; //已收金额
    BigDecimal unpaidAmount; //未收金额
}
