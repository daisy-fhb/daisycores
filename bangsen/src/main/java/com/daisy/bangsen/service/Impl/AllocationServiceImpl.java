package com.daisy.bangsen.service.Impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.daisy.bangsen.dao.AllocationDao;
import com.daisy.bangsen.dao.InventoryDao;
import com.daisy.bangsen.entity.bussiness.Allocation;
import com.daisy.bangsen.entity.bussiness.Inventory;
import com.daisy.bangsen.service.AllocationService;
import com.daisy.bangsen.util.RespBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AllocationServiceImpl implements AllocationService {

    @Autowired
    AllocationDao allocationDao;
    @Autowired
    InventoryDao inventoryDao;

    @Override
    public RespBean save(String postData) {
        RespBean respBean = new RespBean();
        try {
            Allocation allocation = JSONUtil.toBean(postData, Allocation.class);
            int re = allocationDao.insert(allocation);
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
            int re=allocationDao.deleteById(JSONUtil.parseObj(id).getStr("id"));
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
            Allocation allocation= JSONUtil.toBean(postData,Allocation.class);
            int re=allocationDao.updateById(allocation);
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

            if (jsondata.containsKey("serialName") && StringUtils.isNotBlank(jsondata.getStr("serialName"))) {
                paraMap.put("serial_name", jsondata.get("serialName"));
            }
            if (jsondata.containsKey("allocateDate") && StringUtils.isNotBlank(jsondata.getStr("allocateDate"))) {
                String timeflag=jsondata.get("allocateDate").toString();
                String realTime_sta= timeflag+" 00:00:00";
                String realTime_end= timeflag+" 23:59:59";
                paraMap.put("realTime_sta", realTime_sta);
                paraMap.put("realTime_end", realTime_end);
            }

            JSONObject reall = new JSONObject();
            if (StringUtils.isBlank(jsondata.get("currentpage").toString()) && StringUtils.isBlank(jsondata.get("pagesize").toString())) {
                pageBean = new Page<Allocation>(1, 10);
            } else {
                pageBean = new Page<Allocation>(Long.parseLong(jsondata.getStr("currentpage")), Long.parseLong(jsondata.getStr("pagesize")));
            }
            paraMap.put("page", pageBean);
            List re = allocationDao.selectByParam(paraMap);
            int allsize = allocationDao.selectCount(null);
            JSONArray ja=JSONUtil.parseArray(re);
            for (int i =0;i<ja.size();i++){
                ja.getJSONObject(i).put("key",ja.getJSONObject(i).getStr("id"));
                ja.getJSONObject(i).put("id",ja.getJSONObject(i).getStr("id"));
                ja.getJSONObject(i).put("allocateDate",ja.getJSONObject(i).getStr("allocateDate").split(" ")[0]);
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

    @Override
    public RespBean querywaredata() {
        List<String> warehouses=allocationDao.querywaredata();
        List<Map<String, Object>> res=new ArrayList<>();
        for (String warehouse_name:warehouses){
            HashMap param=new HashMap();
            param.put("warehouse_name",warehouse_name);
            List<Inventory> allocations= inventoryDao.selectByParam(param);
            JSONArray incentory=JSONUtil.parseArray(allocations);
            for (int i=0;i<incentory.size();i++){
                incentory.getJSONObject(i).put("id",    incentory.getJSONObject(i).getStr("id"));
            }
            Map<String, Object> re=new HashMap<>();
            re.put("name",warehouse_name);
            re.put("list",incentory);
            res.add(re);
        }
       RespBean respBean=new RespBean();
        respBean.setData(res);
        respBean.setStatus(200);
        return respBean;
    }
}
