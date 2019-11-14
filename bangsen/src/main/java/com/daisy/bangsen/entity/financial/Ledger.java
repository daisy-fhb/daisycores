package com.daisy.bangsen.entity.financial;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;


/**
 * 总账单
 */
@Entity
@Table(name = "t_ledger")
@TableName(value = "t_ledger")
@Data
public class Ledger {
    @TableId("id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //按数据库策略递增
    private Long id;
    String projectName; //项目名称
    String amountInitialDirection; //金额期初方向
    BigDecimal amountInitial; //金额期初
    BigDecimal debitSum; //借方金额
    BigDecimal lenderSum; //贷方金额
    String amountEndingDirection; //金额期末方向
    BigDecimal amountEnding; //金额期末
    BigDecimal accountingYear; //会计年度
    BigDecimal subtotalCost; //成本
    BigDecimal subtotalSales; //销售额
    BigDecimal subtotalProfit; //盈利
}
