package com.daisy.bangsen.controller.bussiness;


import cn.hutool.json.JSONObject;
import com.daisy.bangsen.service.CustomerInfoService;
import com.daisy.bangsen.util.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/customerinfo")
@CrossOrigin
public class CustomerInfoController {
    @Autowired
    CustomerInfoService customerInfoService;

    @RequestMapping(value = "/add" , method = RequestMethod.POST)
    public RespBean regist(@RequestBody String jsondata){
        return customerInfoService.save(jsondata);
    }

    @RequestMapping(value = "/del" , method = RequestMethod.DELETE)
    public RespBean del(@RequestBody String jsondata){
        return customerInfoService.delete(jsondata);
    }

    @RequestMapping(value = "/update" , method = RequestMethod.PUT)
    public RespBean update(@RequestBody String jsondata){
        return customerInfoService.update(jsondata);
    }

    @RequestMapping(value = "/query" , method = RequestMethod.GET)
    public RespBean query(String name,  String currentpage, String pagesize,  String parentname, HttpServletRequest request, HttpServletResponse response){
        RespBean re;
        JSONObject jsondata=new JSONObject();
        jsondata.put("name",name);
        jsondata.put("parentname",parentname);
        jsondata.put("pagesize",pagesize);
        jsondata.put("currentpage",currentpage);
        re=customerInfoService.query(jsondata.toString());
        return re;
    }
}
