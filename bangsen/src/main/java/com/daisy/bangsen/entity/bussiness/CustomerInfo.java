package com.daisy.bangsen.entity.bussiness;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.*;


/**
 * 客户信息单
 */
@Entity
@Table(name = "t_customerinfo")
@TableName(value = "t_customerinfo")
@Data
public class CustomerInfo {
    @TableId("id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //按数据库策略递增
    private Long id;
    String customerName; //客户姓名
    String industryInvolved; //行业
    String contact; //客户联系人
    String inResper; //我司对接人
    String phone;//联系电话
    String addressVisit;//拜访地址
    String addressShip;//发货地址
    String discoverDate; //发掘日期
    String stopDate; //停用日期
    String bankAccount; //银行账户
    String depositBank; //开户行
    String depositBankName; //开户行账号名
}
