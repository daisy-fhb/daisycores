package com.daisy.bangsen.service.Impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.daisy.bangsen.dao.LeadgerDao;
import com.daisy.bangsen.entity.financial.Ledger;
import com.daisy.bangsen.service.LeadgerService;
import com.daisy.bangsen.util.RespBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class LeadgerServiceImpl implements LeadgerService {

    @Autowired
    LeadgerDao leadgerDao;

    @Override
    public RespBean save(String postData) {
        RespBean respBean = new RespBean();
        try {
            Ledger ledger = JSONUtil.toBean(postData, Ledger.class);
            int re = leadgerDao.insert(ledger);
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
            int re=leadgerDao.deleteById(JSONUtil.parseObj(id).getStr("id"));
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
            Ledger ledger= JSONUtil.toBean(postData,Ledger.class);
            int re=leadgerDao.updateById(ledger);
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

            if (jsondata.containsKey("projectName") && StringUtils.isNotBlank(jsondata.getStr("projectName"))) {
                paraMap.put("project_name", jsondata.get("projectName"));
            }

            JSONObject reall = new JSONObject();
            if (StringUtils.isBlank(jsondata.get("currentpage").toString()) && StringUtils.isBlank(jsondata.get("pagesize").toString())) {
                pageBean = new Page<Ledger>(1, 10);
            } else {
                pageBean = new Page<Ledger>(Long.parseLong(jsondata.getStr("currentpage")), Long.parseLong(jsondata.getStr("pagesize")));
            }
            paraMap.put("page", pageBean);
            List re = leadgerDao.selectByParam(paraMap);
            int allsize = leadgerDao.selectCount(null);

            JSONArray ja=JSONUtil.parseArray(re);
            for (int i =0;i<ja.size();i++){
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
