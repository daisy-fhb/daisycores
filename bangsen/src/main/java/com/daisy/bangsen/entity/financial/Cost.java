package com.daisy.bangsen.entity.financial;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;


/**
 * 成本单
 */
@Entity
@Table(name = "t_cost")
@TableName(value = "t_cost")
@Data
public class Cost {
    @TableId("id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //按数据库策略递增
    private Long id;
    String projectName; //项目名称
    String projectEarning; //项目收入
    BigDecimal researchExpense; //研发费
    BigDecimal materialsExpense; //材料费
    BigDecimal leaseExpense; //租赁费
    String subpackageExpense; //分包费
    BigDecimal staffWage; //人工成本
    BigDecimal entertainmentExpenses; //业务招待费
    BigDecimal reimburseExpenses; //报销费
    BigDecimal taxationExpenses; //税费
    BigDecimal otherExpenses; //其他费用
    BigDecimal margin; //毛利
    BigDecimal marginRatio; //毛利率
}
