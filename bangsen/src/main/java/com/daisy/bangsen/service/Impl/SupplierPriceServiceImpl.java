package com.daisy.bangsen.service.Impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.daisy.bangsen.dao.SupplierPriceDao;
import com.daisy.bangsen.entity.bussiness.SupplierPrice;
import com.daisy.bangsen.service.SupplierPriceService;
import com.daisy.bangsen.util.NetImgUtil;
import com.daisy.bangsen.util.RespBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class SupplierPriceServiceImpl implements SupplierPriceService {

    @Autowired
    SupplierPriceDao supplierPriceDao;

    @Override
    public RespBean save(String postData) {
        RespBean respBean = new RespBean();
        try {
            SupplierPrice supplierPrice = JSONUtil.toBean(postData, SupplierPrice.class);
            supplierPrice.setReceiptNumber(NetImgUtil.getTimeFlag());
            int re = supplierPriceDao.insert(supplierPrice);
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
            int re=supplierPriceDao.deleteById(JSONUtil.parseObj(id).getStr("id"));
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
            SupplierPrice supplierPrice= JSONUtil.toBean(postData,SupplierPrice.class);
            int re=supplierPriceDao.updateById(supplierPrice);
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

            if (jsondata.containsKey("supplierName") && StringUtils.isNotBlank(jsondata.getStr("supplierName"))) {
                paraMap.put("supplier_name", jsondata.get("supplierName"));
            }

            if (jsondata.containsKey("itemName") && StringUtils.isNotBlank(jsondata.getStr("itemName"))) {
                paraMap.put("item_name", jsondata.get("itemName"));
            }

            JSONObject reall = new JSONObject();
            if (StringUtils.isBlank(jsondata.get("currentpage").toString()) && StringUtils.isBlank(jsondata.get("pagesize").toString())) {
                pageBean = new Page<SupplierPrice>(1, 10);
            } else {
                pageBean = new Page<SupplierPrice>(Long.parseLong(jsondata.getStr("currentpage")), Long.parseLong(jsondata.getStr("pagesize")));
            }
            paraMap.put("page", pageBean);
            List re = supplierPriceDao.selectByParam(paraMap);
            int allsize = supplierPriceDao.selectCount(null);
            JSONArray ja=JSONUtil.parseArray(re);
            for(int i=0;i<ja.size();i++){
                ja.getJSONObject(i).put("key",ja.getJSONObject(i).getStr("id"));
                ja.getJSONObject(i).put("id",ja.getJSONObject(i).getStr("id"));
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
