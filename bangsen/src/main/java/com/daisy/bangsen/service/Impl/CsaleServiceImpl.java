package com.daisy.bangsen.service.Impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.daisy.bangsen.dao.CsaleDao;
import com.daisy.bangsen.entity.bussiness.Csale;
import com.daisy.bangsen.service.CsaleService;
import com.daisy.bangsen.util.RespBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class CsaleServiceImpl implements CsaleService {

    @Autowired
    CsaleDao csaleDao;

    @Override
    public RespBean save(String postData) {
        RespBean respBean = new RespBean();
        try {
            Csale csale = JSONUtil.toBean(postData, Csale.class);
            int re = csaleDao.insert(csale);
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
            int re=csaleDao.deleteById(JSONUtil.parseObj(id).getStr("id"));
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
            Csale csale= JSONUtil.toBean(postData,Csale.class);
            int re=csaleDao.updateById(csale);
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

            if (jsondata.containsKey("receiptNumber") && StringUtils.isNotBlank(jsondata.getStr("receiptNumber"))) {
                paraMap.put("receipt_number", jsondata.get("receiptNumber"));
            }

            if (jsondata.containsKey("salesName") && StringUtils.isNotBlank(jsondata.getStr("salesName"))) {
                paraMap.put("sales_name", jsondata.get("salesName"));
            }


            if (jsondata.containsKey("customerName") && StringUtils.isNotBlank(jsondata.getStr("customerName"))) {
                paraMap.put("customer_name", jsondata.get("customerName"));
            }


            if (jsondata.containsKey("status") && StringUtils.isNotBlank(jsondata.getStr("status"))) {
                paraMap.put("sales_status", jsondata.get("status"));
            }


            JSONObject reall = new JSONObject();
            if (StringUtils.isBlank(jsondata.get("currentpage").toString()) && StringUtils.isBlank(jsondata.get("pagesize").toString())) {
                pageBean = new Page<Csale>(1, 10);
            } else {
                pageBean = new Page<Csale>(Long.parseLong(jsondata.getStr("currentpage")), Long.parseLong(jsondata.getStr("pagesize")));
            }
            paraMap.put("page", pageBean);
            List re = csaleDao.selectByParam(paraMap);
            int allsize = csaleDao.selectCount(null);
            JSONArray ja=JSONUtil.parseArray(re);
            for (int i=0;i<ja.size();i++){
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
