package com.daisy.bangsen.service.Impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.daisy.bangsen.dao.AllocationDao;
import com.daisy.bangsen.entity.bussiness.Allocation;
import com.daisy.bangsen.service.AllocationService;
import com.daisy.bangsen.util.RespBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class AllocationServiceImpl implements AllocationService {

    @Autowired
    AllocationDao allocationDao;
    @Autowired
    RedisTemplate redisTemplate;

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

            if (jsondata.containsKey("name") && StringUtils.isNotBlank(jsondata.getStr("name"))) {
                paraMap.put("name", jsondata.get("name"));
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
            reall.put("total", allsize);
            reall.put("list", re);
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
