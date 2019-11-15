package com.daisy.bangsen.controller.bussiness;


import cn.hutool.json.JSONObject;
import com.daisy.bangsen.service.IndentService;
import com.daisy.bangsen.util.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/indent")
@CrossOrigin
public class IndentController {
    @Autowired
    IndentService indentService;

    @RequestMapping(value = "/add" , method = RequestMethod.POST)
    public RespBean regist(@RequestBody String jsondata){
        return indentService.save(jsondata);
    }

    @RequestMapping(value = "/del" , method = RequestMethod.DELETE)
    public RespBean del(@RequestBody String jsondata){
        return indentService.delete(jsondata);
    }

    @RequestMapping(value = "/update" , method = RequestMethod.PUT)
    public RespBean update(@RequestBody String jsondata){
        return indentService.update(jsondata);
    }

    @RequestMapping(value = "/query" , method = RequestMethod.GET)
    public RespBean query(String name,  String currentpage, String pagesize,  String parentname, HttpServletRequest request, HttpServletResponse response){
        RespBean re;
        JSONObject jsondata=new JSONObject();
        jsondata.put("name",name);
        jsondata.put("parentname",parentname);
        jsondata.put("pagesize",pagesize);
        jsondata.put("currentpage",currentpage);
        re=indentService.query(jsondata.toString());
        return re;
    }
}
