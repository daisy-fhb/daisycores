package com.daisy.bangsen.controller.auth;


import cn.hutool.json.JSONObject;
import com.daisy.bangsen.service.DeptService;
import com.daisy.bangsen.util.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("dept")
@CrossOrigin
public class DeptController {
    @Autowired
    DeptService deptService;

    @RequestMapping(value = "/add" , method = RequestMethod.POST)
    public RespBean regist(@RequestBody String jsondata){
        return deptService.save(jsondata);
    }

    @RequestMapping(value = "/del" , method = RequestMethod.DELETE)
    public RespBean del(@RequestBody String jsondata){
        return deptService.delete(jsondata);
    }

    @RequestMapping(value = "/update" , method = RequestMethod.PUT)
    public RespBean update(@RequestBody String jsondata){
        return deptService.update(jsondata);
    }

    @RequestMapping(value = "/query" , method = RequestMethod.GET)
    public RespBean query(@RequestParam String name, @RequestParam String currentpage, @RequestParam String pagesize, @RequestParam String parentname, HttpServletRequest request, HttpServletResponse response){
        RespBean re;
        JSONObject jsondata=new JSONObject();
        jsondata.put("name",name);
        jsondata.put("parentname",parentname);
        jsondata.put("pagesize",pagesize);
        jsondata.put("currentpage",currentpage);
        re=deptService.query(jsondata.toString());
        return re;
    }

    @RequestMapping(value = "/querypdept" , method = RequestMethod.GET)
    public RespBean querypdept(HttpServletRequest request, HttpServletResponse response){
        RespBean re;
        JSONObject jsondata=new JSONObject();
        jsondata.put("parentif",true);
        re=deptService.query(jsondata.toString());
        return re;
    }
}
