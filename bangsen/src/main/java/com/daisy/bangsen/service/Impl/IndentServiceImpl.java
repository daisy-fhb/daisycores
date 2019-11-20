package com.daisy.bangsen.service.Impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.daisy.bangsen.dao.DeptDao;
import com.daisy.bangsen.dao.IndentDao;
import com.daisy.bangsen.entity.bussiness.Indent;
import com.daisy.bangsen.service.IndentService;
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
public class IndentServiceImpl implements IndentService {

    @Autowired
    IndentDao indentDao;
    @Autowired
    DeptDao deptDao;

    @Override
    public RespBean save(String postData) {
        RespBean respBean = new RespBean();
        try {
            Indent indent = JSONUtil.toBean(postData, Indent.class);
            indent.setReceiptNumber(NetImgUtil.getTimeFlag());
            indent.setPurchaseStatus(0);
            int re = indentDao.insert(indent);
            if (re > 0) {
                respBean.setStatus(200);
                respBean.setMsg("新增成功");
            } else {
                respBean.setStatus(2000);
                respBean.setMsg("新增失败");
            }
        } catch (Exception e) {
            respBean.setStatus(2000);
            if ( e.getMessage().contains(" decimal digit number")){
                respBean.setMsg("请检查数字字段是否填为汉字或英文字母了" );
            }else{
                respBean.setMsg( e.getMessage());
                e.printStackTrace();
            }
        }
        return respBean;
    }

    @Override
    public RespBean delete(String id) {
        RespBean respBean=new RespBean();
        try {
            int re=indentDao.deleteById(JSONUtil.parseObj(id).getStr("id"));
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
            Indent indent= JSONUtil.toBean(postData,Indent.class);
            Indent tmp=indentDao.selectById(indent.getId());
            if (!postData.contains("purchaseStatus")){
                indent.setPurchaseStatus(tmp.getPurchaseStatus());
            }
            int re=indentDao.updateById(indent);
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

            if (jsondata.containsKey("name") && StringUtils.isNotBlank(jsondata.getStr("name"))) {
                paraMap.put("purchaseName", jsondata.get("name"));
            }
            if (jsondata.containsKey("recordid") && StringUtils.isNotBlank(jsondata.getStr("recordid"))) {
                paraMap.put("receiptNumber", jsondata.get("recordid"));
            }
            if (jsondata.containsKey("s_name") && StringUtils.isNotBlank(jsondata.getStr("s_name"))) {
                paraMap.put("supplierName", jsondata.get("s_name"));
            }
           if (jsondata.containsKey("status") && StringUtils.isNotBlank(jsondata.getStr("status"))) {
                paraMap.put("purchaseStatus", jsondata.get("status"));
            }

            JSONObject reall = new JSONObject();
            if (StringUtils.isBlank(jsondata.get("currentpage").toString()) && StringUtils.isBlank(jsondata.get("pagesize").toString())) {
                pageBean = new Page<Indent>(1, 10);
            } else {
                pageBean = new Page<Indent>(Long.parseLong(jsondata.getStr("currentpage")), Long.parseLong(jsondata.getStr("pagesize")));
            }
            paraMap.put("page", pageBean);
            List re = indentDao.selectByParam(paraMap);
            JSONArray ja=JSONUtil.parseArray(re);
            for (int i=0;i<ja.size();i++){
                ja.getJSONObject(i).put("id",ja.getJSONObject(i).getStr("id"));
                ja.getJSONObject(i).put("key",ja.getJSONObject(i).getStr("id"));
            }
            int allsize = indentDao.selectCount(null);
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
