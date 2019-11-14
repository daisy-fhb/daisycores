package com.daisy.bangsen.entity.financial;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;


/**
 * 工资单
 */
@Entity
@Table(name = "t_salary")
@TableName(value = "t_salary")
@Data
public class Salary {
    @TableId("id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //按数据库策略递增
    private Long id;
    String staffName; //员工姓名
    String department; //所在部门
    BigDecimal wagesContract; //合同工资
    BigDecimal cardinalNumber; //基数
    BigDecimal unitPension; //养老单位
    BigDecimal personalPension; //养老个人
    BigDecimal unitMedical; //医疗单位
    BigDecimal personalMedical; //医疗个人
    BigDecimal unitUnemployment; //失业单位
    BigDecimal personalUnemployment; //失业个人
    BigDecimal unitInductrialInjury; //工伤单位
    BigDecimal personalInductrialInjury; //工伤个人
    BigDecimal unitMaternity; //生育单位
    BigDecimal fiveInsuranceEnterprisesTotal; //五险单位合计
    BigDecimal fiveInsurancePersonalTotal; //五险个人合计
    BigDecimal fiveInsuranceTotal; //五险合计
    BigDecimal reserveFundBase; //公积金基础
    BigDecimal reserveFundEnterprises; //公积金单位
    BigDecimal reserveFundPersonal; //公积金个人
    BigDecimal reserveFundTotal; //公积金合计
    BigDecimal socialSecurityFundEnterprisesTotal; //社保公积金单位承担总额
    BigDecimal socialSecurityFundPersonalTotal; //社保公积金个人承担总额
    BigDecimal travelGrants; //差旅补助
    BigDecimal computerGrants; //电脑补助
    BigDecimal elseGrants; //其他补助
    BigDecimal casualLeave; //事假
    BigDecimal sickLeave; //病假
    BigDecimal beLate; //迟到
    BigDecimal otherDeduction; //其它扣除
    BigDecimal taxableSalary; //计税工资
    BigDecimal TaxRevenue; //报税收入
    BigDecimal childrenEducation; //子女教育
    BigDecimal housingLoanInterest; //住房贷款利息
    BigDecimal supportOld; //赡养老人
    BigDecimal monthTax; //本月纳税
    BigDecimal annualAccumulatedTax; //全年纳税总计
    BigDecimal salary; //应发工资
    BigDecimal afterTaxDeduction; //税后扣除
    BigDecimal netPayroll; //实发工资
}
