package com.daisy.bangsen.service.Impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.daisy.bangsen.dao.AccountReceivableDao;
import com.daisy.bangsen.entity.financial.AccountReceivable;
import com.daisy.bangsen.service.AccountReceivableService;
import com.daisy.bangsen.util.RespBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class AccountReceivableServiceImpl implements AccountReceivableService {

    @Autowired
    AccountReceivableDao accountReceivableDao;

    @Override
    public RespBean save(String postData) {
        RespBean respBean = new RespBean();
        try {
            AccountReceivable accountReceivable = JSONUtil.toBean(postData, AccountReceivable.class);
            int re = accountReceivableDao.insert(accountReceivable);
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
            int re=accountReceivableDao.deleteById(JSONUtil.parseObj(id).getStr("id"));
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
            AccountReceivable accountReceivable= JSONUtil.toBean(postData,AccountReceivable.class);
            int re=accountReceivableDao.updateById(accountReceivable);
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
                paraMap.put("project_name", jsondata.get("name"));
            }

            JSONObject reall = new JSONObject();
            if (StringUtils.isBlank(jsondata.get("currentpage").toString()) && StringUtils.isBlank(jsondata.get("pagesize").toString())) {
                pageBean = new Page<AccountReceivable>(1, 10);
            } else {
                pageBean = new Page<AccountReceivable>(Long.parseLong(jsondata.getStr("currentpage")), Long.parseLong(jsondata.getStr("pagesize")));
            }
            paraMap.put("page", pageBean);
            List re = accountReceivableDao.selectByParam(paraMap);
            int allsize = accountReceivableDao.selectCount(null);
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
