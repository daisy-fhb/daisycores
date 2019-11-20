package com.daisy.bangsen.service.Impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.daisy.bangsen.dao.*;
import com.daisy.bangsen.service.IndexService;
import com.daisy.bangsen.util.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Service
@Transactional
public class IndexServiceImpl implements IndexService {
    @Autowired
    AccountPayableDao accountPayableDao;
    @Autowired
    AccountReceivableDao accountReceivableDao;
    @Autowired
    AllocationDao allocationDao;
    @Autowired
    AssetDao assetDao;
    @Autowired
    CostDao costDao;
    @Autowired
    CsaleDao csaleDao;
    @Autowired
    CustomerDao customerDao;
    @Autowired
    HouseInOutDao houseInOutDao;
    @Autowired
    IndentDao indentDao;
    @Autowired
    InventoryDao inventoryDao;
    @Autowired
    LeadgerDao leadgerDao;
    @Autowired
    SalaryDao salaryDao;
    @Autowired
    SimpleProduceDao simpleProduceDao;
    @Autowired
    SupplierPriceDao supplierPriceDao;
    @Autowired
    UserDao userDao;




    @Override
    public RespBean top() {
        RespBean respBean=new RespBean();
        int staffnumer=userDao.selectCount(null);
        int produceline=3;
        BigDecimal salenumber= RandomUtil.randomBigDecimal(new BigDecimal("500000") ).setScale(2, RoundingMode.HALF_EVEN);
        int producecount= RandomUtil.randomInt(1000,2000);
        JSONObject jsonObject=JSONUtil.createObj();
        jsonObject.put("staffnumer",staffnumer);
        jsonObject.put("produceline",produceline);
        jsonObject.put("salenumber",salenumber);
        jsonObject.put("producecount",producecount);
        respBean.setStatus(200);
        respBean.setData(jsonObject);
        return respBean;
    }

    @Override
    public RespBean monthly() {
        return null;
    }

    @Override
    public RespBean dept() {
        return null;
    }

    @Override
    public RespBean dutyrecord() {
        return null;
    }

    @Override
    public HashMap selectDataList(String type) {
        HashMap<String,Object> resultt=new HashMap<>();
        List<?> re=new ArrayList<>();
        String tittle="";
        QueryWrapper queryWrapper=new QueryWrapper();
        switch (type) {
            case "indent":
                queryWrapper.orderByDesc("purchase_request_date");
                re=indentDao.selectList(queryWrapper);
                tittle="采购详情";
                break;
            case "supplierprice":
                queryWrapper.orderByDesc("supplier_name");
                re=supplierPriceDao.selectList(queryWrapper);
                tittle="供应商价格详情";
                break;
            case "allocation":
                queryWrapper.orderByDesc("allocate_date");
                re=allocationDao.selectList(queryWrapper);
                tittle="物资调拨详情";
                break;
            case "customerinfo":
                queryWrapper.orderByDesc("customer_name");
                re=customerDao.selectList(queryWrapper);
                tittle="客户信息详情";
                break;
            case "houseinout":
                queryWrapper.orderByDesc("in_out_date");
                re=houseInOutDao.selectList(queryWrapper);
                tittle="出入库详情";
                break;
            case "csale":
                queryWrapper.orderByDesc("estimate_date");
                re=csaleDao.selectList(queryWrapper);
                tittle="销售详情";
                break;
            case "accountpayable":
                queryWrapper.orderByDesc("supplier_name");
                re=accountPayableDao.selectList(queryWrapper);
                tittle="应付款详情";
                break;
            case "accountreceivable":
                queryWrapper.orderByDesc("project_name");
                re=accountReceivableDao.selectList(queryWrapper);
                tittle="应收款详情";
                break;
            case "asset":
                queryWrapper.orderByDesc("asset_name");
                re=assetDao.selectList(queryWrapper);
                tittle="固定资产详情";
                break;
            case "cost":
                queryWrapper.orderByDesc("project_name");
                re=costDao.selectList(queryWrapper);
                tittle="成本详情";
                break;
            case "ledger":
                queryWrapper.orderByDesc("project_name");
                re=leadgerDao.selectList(queryWrapper);
                tittle="总账详情";
                break;
            case "salary":
                queryWrapper.orderByDesc("staff_name");
                re=salaryDao.selectList(queryWrapper);
                tittle="工资详情";
                break;
            case "simpleproduce":
                queryWrapper.orderByDesc("estimate_date");
                re=simpleProduceDao.selectList(queryWrapper);
                tittle="生产详情";
                break;
            case "user":
                queryWrapper.orderByDesc("name");
                re=userDao.selectList(queryWrapper);
                tittle="员工详情";
                break;
            default:
                break;
        }
        JSONArray ja=JSONUtil.parseArray(re);
        resultt.put("data",ja);
        resultt.put("tittle",tittle);
        return resultt;
    }
}
