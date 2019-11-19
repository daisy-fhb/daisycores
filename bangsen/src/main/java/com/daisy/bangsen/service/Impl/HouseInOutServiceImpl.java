package com.daisy.bangsen.service.Impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.daisy.bangsen.dao.HouseInOutDao;
import com.daisy.bangsen.entity.bussiness.HouseInOut;
import com.daisy.bangsen.service.HouseInOutService;
import com.daisy.bangsen.util.RespBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class HouseInOutServiceImpl implements HouseInOutService {

    @Autowired
    HouseInOutDao houseInOutDao;

    @Override
    public RespBean save(String postData) {
        RespBean respBean = new RespBean();
        try {
            HouseInOut houseInOut = JSONUtil.toBean(postData, HouseInOut.class);
            int re = houseInOutDao.insert(houseInOut);
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
            int re=houseInOutDao.deleteById(JSONUtil.parseObj(id).getStr("id"));
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
            HouseInOut houseInOut= JSONUtil.toBean(postData,HouseInOut.class);
            int re=houseInOutDao.updateById(houseInOut);
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
                paraMap.put("receiptNumber", jsondata.get("receiptNumber"));
            }

            if (jsondata.containsKey("inOutName") && StringUtils.isNotBlank(jsondata.getStr("inOutName"))) {
                paraMap.put("inOutName", jsondata.get("inOutName"));
            }

            if (jsondata.containsKey("inOutType") && StringUtils.isNotBlank(jsondata.getStr("inOutType"))) {
                paraMap.put("inOutType", jsondata.get("inOutType"));
            }

            if (jsondata.containsKey("warehouseName") && StringUtils.isNotBlank(jsondata.getStr("warehouseName"))) {
                paraMap.put("warehouseName", jsondata.get("warehouseName"));
            }


            JSONObject reall = new JSONObject();
            if (StringUtils.isBlank(jsondata.get("currentpage").toString()) && StringUtils.isBlank(jsondata.get("pagesize").toString())) {
                pageBean = new Page<HouseInOut>(1, 10);
            } else {
                pageBean = new Page<HouseInOut>(Long.parseLong(jsondata.getStr("currentpage")), Long.parseLong(jsondata.getStr("pagesize")));
            }
            paraMap.put("page", pageBean);
            List re = houseInOutDao.selectByParam(paraMap);
            int allsize = houseInOutDao.selectCount(null);

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
