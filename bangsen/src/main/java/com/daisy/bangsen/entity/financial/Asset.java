package com.daisy.bangsen.entity.financial;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;


/**
 * 固定资产单
 */
@Entity
@Table(name = "t_asset")
@TableName(value = "t_asset")
@Data
public class Asset {
    @TableId("id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //按数据库策略递增
    private Long id;
    String assetName; //资产名称
    BigDecimal worth; //价值
    String storagePlace; //储放位置
    String wreck; //折损
    int isEnabled; //是否启用
    String department; //所属部门
    long count; //数量
    String type; //类别
    String nature; // 资产性质 ：工治具/正常资产
    int status; //状态
}
