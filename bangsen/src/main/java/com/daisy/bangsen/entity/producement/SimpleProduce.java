package com.daisy.bangsen.entity.producement;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.*;


/**
 * 应付单
 */
@Entity
@Table(name = "t_simpleproduce")
@TableName(value = "t_simpleproduce")
@Data
public class SimpleProduce {
    @TableId("id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //按数据库策略递增
    private Long id;
    String receiptNumber; //单据号
    String productName; //项目名称
    String customerName; //客户名称
    String contractNumber; //合同编号
    String salesDate; //销售日期
    String estimateDate; //预完工日期
    String estimateSendDate; //预发货日期
    String productionCompletion; //生产完成度
    String consigneeName; //收货人姓名
    String consigneePhone; //收货人电话
    long count; //数量
    String transportMode; //运输方式
    String productionSupervisor; //生产负责人
    int productionStatus; //生产状态
}
