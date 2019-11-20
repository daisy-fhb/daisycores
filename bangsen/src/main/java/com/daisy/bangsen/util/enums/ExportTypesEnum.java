package com.daisy.bangsen.util.enums;

import cn.hutool.core.collection.CollUtil;

import java.util.List;

/**
 * 导出表头类型
 */
public enum ExportTypesEnum {

    indent(CollUtil.newArrayList( "地址","部门名称","备注")),
    supplierprice(CollUtil.newArrayList("车牌号", "作业开始时间", " 作业垃圾重量（kg）","作业结束时间","作业垃圾桶数（桶）")),
    allocation(CollUtil.newArrayList("车牌号", "距离（米）",  "名称","作业开始时间")),
    customerinfo(CollUtil.newArrayList("收运次数", "地址", "收运总重量(kg)", "微信账号","垃圾桶数量","执照","负责人","线路编号","电话","签约时间","协议","商家名称","坐标点","商家编号")),
    houseinout(CollUtil.newArrayList("生日", "学历", "角色编号", "性别","部门编号","是否聘用","更新时间","入职时间","密文","专业","电话","姓名","编号","账号")),
    csale(CollUtil.newArrayList("车牌号", "作业开始时间", "重量(kg)", "作业结束时间","作业时间(小时)","里程(米)")),
    accountpayable(CollUtil.newArrayList("车牌号", "作业开始时间", "重量(kg)", "作业结束时间","作业时间(小时)","里程(米)")),
    accountreceivable(CollUtil.newArrayList("车牌号", "作业开始时间", "重量(kg)", "作业结束时间","作业时间(小时)","里程(米)")),
    asset(CollUtil.newArrayList("车牌号", "作业开始时间", "重量(kg)", "作业结束时间","作业时间(小时)","里程(米)")),
    cost(CollUtil.newArrayList("车牌号", "作业开始时间", "重量(kg)", "作业结束时间","作业时间(小时)","里程(米)")),
    ledger(CollUtil.newArrayList("车牌号", "作业开始时间", "重量(kg)", "作业结束时间","作业时间(小时)","里程(米)")),
    salary(CollUtil.newArrayList("车牌号", "作业开始时间", "重量(kg)", "作业结束时间","作业时间(小时)","里程(米)")),
    assimpleproduceset(CollUtil.newArrayList("车牌号", "作业开始时间", "重量(kg)", "作业结束时间","作业时间(小时)","里程(米)")),
    user(CollUtil.newArrayList("车牌号", "作业开始时间", "重量(kg)", "作业结束时间","作业时间(小时)","里程(米)"));

    private List<?> re;
    ExportTypesEnum(List<?> re){
        this.re=re;
    };

    public List<?> getTypeHead(){
        return re;
    }

}
