package com.daisy.bangsen.entity.bussiness;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;


/**
 * 出入库单
 */
@Entity
@Table(name = "t_houseinout")
@TableName(value = "t_houseinout")
@Data
public class HouseInOut {
    @TableId("id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //按数据库策略递增
    private Long id;
    String receiptNumber; //票据号
    String inOutDate; //出入库日期
    String inOutName; //出入路物品名称
    String warehouseName; //库房名称
    String specifications; //规格
    long count; //数量
    BigDecimal price; //价格
    BigDecimal amount; //金额
    String shelfLife; //保质期
    String producedDate; //生产日期
    String inOutType; //出入库类型
    String operator; //操作员
}
