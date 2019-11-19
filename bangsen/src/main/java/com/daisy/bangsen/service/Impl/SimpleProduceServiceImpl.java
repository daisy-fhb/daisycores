package com.daisy.bangsen.service.Impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.daisy.bangsen.dao.SimpleProduceDao;
import com.daisy.bangsen.entity.producement.SimpleProduce;
import com.daisy.bangsen.service.SimpleProduceService;
import com.daisy.bangsen.util.RespBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class SimpleProduceServiceImpl implements SimpleProduceService {

    @Autowired
    SimpleProduceDao simpleProduceDao;

    @Override
    public RespBean save(String postData) {
        RespBean respBean = new RespBean();
        try {
            SimpleProduce simpleProduce = JSONUtil.toBean(postData, SimpleProduce.class);
            int re = simpleProduceDao.insert(simpleProduce);
            if (re > 0) {
                respBean.setStatus(200);
                respBean.setMsg("新增成功");
            } else {
                respBean.setStatus(2000);
                respBean.setMsg("新增失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            respBean.setStatus(2000);
            respBean.setMsg("新增失败," + e.getMessage());
        }
        return respBean;
    }

    @Override
    public RespBean delete(String id) {
        RespBean respBean=new RespBean();
        try {
            int re=simpleProduceDao.deleteById(JSONUtil.parseObj(id).getStr("id"));
            if (re>0){
                respBean.setStatus(200);
                respBean.setMsg("删除成功");
            }else{
                respBean.setStatus(2000);
                respBean.setMsg("删除失败，没有该条数据");
            }
        }catch (Exception e){
            e.printStackTrace();
            respBean.setStatus(2000);
            respBean.setMsg("删除失败,"+e.getMessage());
        }
        return respBean;
    }

    @Override
    public RespBean update(String postData) {
        RespBean respBean=new RespBean();
        try {
            SimpleProduce simpleProduce= JSONUtil.toBean(postData,SimpleProduce.class);
            int re=simpleProduceDao.updateById(simpleProduce);
            if (re>0){
                respBean.setStatus(200);
                respBean.setMsg("编辑成功");
            }else{
                respBean.setStatus(2000);
                respBean.setMsg("编辑失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            respBean.setStatus(2000);
            respBean.setMsg("更新失败，"+e.getMessage());
        }
        return respBean;
    }

    @Override
    public RespBean query(String postData) {
        RespBean respBean = new RespBean();
        try {
            JSONObject jsondata = JSONUtil.parseObj(postData);
            HashMap paraMap = new HashMap<>();
            Page pageBean;

            if (jsondata.containsKey("productName") && StringUtils.isNotBlank(jsondata.getStr("productName"))) {
                paraMap.put("product_name", jsondata.get("productName"));
            }
            if (jsondata.containsKey("receiptNumber") && StringUtils.isNotBlank(jsondata.getStr("receiptNumber"))) {
                paraMap.put("receipt_number", jsondata.get("receiptNumber"));
            }
            if (jsondata.containsKey("productionStatus") && StringUtils.isNotBlank(jsondata.getStr("productionStatus"))) {
                paraMap.put("production_status", jsondata.get("productionStatus"));
            }

            JSONObject reall = new JSONObject();
            if (StringUtils.isBlank(jsondata.get("currentpage").toString()) && StringUtils.isBlank(jsondata.get("pagesize").toString())) {
                pageBean = new Page<SimpleProduce>(1, 10);
            } else {
                pageBean = new Page<SimpleProduce>(Long.parseLong(jsondata.getStr("currentpage")), Long.parseLong(jsondata.getStr("pagesize")));
            }
            paraMap.put("page", pageBean);
            List re = simpleProduceDao.selectByParam(paraMap);
            int allsize = simpleProduceDao.selectCount(null);
            JSONArray ja=JSONUtil.parseArray(re);
            for (int i =0;i<ja.size();i++){
                ja.getJSONObject(i).put("key",ja.getJSONObject(i).getStr("id"));
            }
            reall.put("total", allsize);
            reall.put("list", ja);
            if (re != null && re.size() >= 0) {
                respBean.setData(reall);
                respBean.setStatus(200);
                respBean.setMsg("查询成功");
            } else {
                respBean.setStatus(200);
                respBean.setMsg("查询成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            respBean.setStatus(2000);
            respBean.setMsg("查询失败，" + e.getMessage());
        }
        return respBean;
    }
}
