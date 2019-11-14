package com.daisy.bangsen.entity.financial;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;


/**
 * 应收单
 */
@Entity
@Table(name = "t_accountreceivable")
@TableName(value = "t_accountreceivable")
@Data
public class AccountReceivable {
    @TableId("id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //按数据库策略递增
    private Long id;
    String projectName; //项目名称
    String merchantName; //客商名称
    BigDecimal salesman; //原始金额
    BigDecimal originalAmount; //原始金额
    BigDecimal receivedAmount; //已收金额
    BigDecimal outstandingAmount; //未收金额
}
