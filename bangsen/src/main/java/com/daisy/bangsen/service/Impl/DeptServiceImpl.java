package com.daisy.bangsen.service.Impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.daisy.bangsen.dao.DeptDao;
import com.daisy.bangsen.entity.auth.Department;
import com.daisy.bangsen.service.DeptService;
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
public class DeptServiceImpl implements DeptService {

    @Autowired
    DeptDao deptDao;

    @Override
    public RespBean save(String postData) {
        RespBean respBean=new RespBean();
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        try {
            Department department= JSONUtil.toBean(postData, Department.class);
            department.setId(snowflake.nextIdStr());
            HashMap param=new HashMap();
            param.put("name",department.getName());
            List<Department> tmp=deptDao.selectByMap(param);
            if (tmp!=null&&!tmp.isEmpty()){
                respBean.setStatus(2000);
                respBean.setMsg("新增失败,部门名称重复");
            }else{
                int re=deptDao.insert(department);
                if (re>0){
                    respBean.setStatus(200);
                    respBean.setMsg("新增成功");
                }else{
                    respBean.setStatus(2000);
                    respBean.setMsg("新增失败");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            respBean.setStatus(2000);
            respBean.setMsg("新增失败,"+e.getMessage());
        }
        return respBean;
    }

    @Override
    public RespBean delete(String id) {
        RespBean respBean=new RespBean();
        try {
            int re=deptDao.deleteById(JSONUtil.parseObj(id).getStr("deptid"));
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
            Department department= JSONUtil.toBean(postData,Department.class);
            int re=deptDao.updateById(department);
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
        RespBean respBean=new RespBean();
        try {
            JSONObject jsondata= JSONUtil.parseObj(postData);
            HashMap paraMap=new HashMap<>();
            Page pageBean;

            if (jsondata.containsKey("name")&& StringUtils.isNotBlank(jsondata.getStr("name"))){
                paraMap.put("name",jsondata.get("name"));
            }

            if (jsondata.containsKey("parentname")&& StringUtils.isNotBlank(jsondata.getStr("parentname"))){
                HashMap params=new HashMap();
                params.put("name",jsondata.get("parentname"));
                Department parent=deptDao.selectByParam(params)!=null?deptDao.selectByParam(params).get(0):null;
                if (parent!=null){
                    paraMap.put("parentid",parent.getId());
                }
            }


            List<Department> re;
            JSONObject reall=new JSONObject();

            if (jsondata.containsKey("parentif")&& StringUtils.isNotBlank(jsondata.getStr("parentif"))){
                //下拉框查询
                re=deptDao.selectByParam(paraMap);
                List<Map<String, String>> finallist=new ArrayList<>();
                for (Department d:re){
                    Map map=new HashMap();
                    map.put("id",d.getId());
                    map.put("name",d.getName());
                    finallist.add(map);
                }
                int allsize=deptDao.selectCount(null);
                reall.put("total",allsize);
                reall.put("list",finallist);
            }else{
                if(StringUtils.isBlank(jsondata.get("currentpage").toString())&&StringUtils.isBlank(jsondata.get("pagesize").toString())){
                    pageBean= new Page<Department>(1, 10);
                }else{
                    pageBean= new Page<Department>(Long.parseLong(jsondata.getStr("currentpage")), Long.parseLong(jsondata.getStr("pagesize")));
                }
                paraMap.put("page",pageBean);
                re=deptDao.selectByParam(paraMap);
                JSONArray ja=new JSONArray();
                List<Department> alldept=deptDao.selectList(null);
                Map alldepymaps=new HashMap();
                for (Department d:alldept){
                    alldepymaps.put(d.getId(),d.getName());
                }
                for(Department d:re){
                    JSONObject jsonObject= JSONUtil.parseObj(d);
                    if (alldepymaps.containsKey(d.getParentid())){
                        jsonObject.put("parentname",alldepymaps.get(d.getParentid()));
                    }else{
                        jsonObject.put("parentname","—");
                    }
                    ja.add(jsonObject);
                }
                int allsize=deptDao.selectCount(null);
                reall.put("total",allsize);
                reall.put("list",ja);
            }

            if (re!=null&&re.size()>=0){
                respBean.setData(reall);
                respBean.setStatus(200);
                respBean.setMsg("查询成功");
            }else{
                respBean.setStatus(200);
                respBean.setMsg("查询成功");
            }
        }catch (Exception e){
            e.printStackTrace();
            respBean.setStatus(2000);
            respBean.setMsg("查询失败，"+e.getMessage());
        }
        return respBean;
    }
}
