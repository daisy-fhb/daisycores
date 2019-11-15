package com.daisy.bangsen.controller.financial;


import cn.hutool.json.JSONObject;
import com.daisy.bangsen.service.LeadgerService;
import com.daisy.bangsen.util.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/ledger")
@CrossOrigin
public class LeadgerController {
    @Autowired
    LeadgerService leadgerService;

    @RequestMapping(value = "/add" , method = RequestMethod.POST)
    public RespBean regist(@RequestBody String jsondata){
        return leadgerService.save(jsondata);
    }

    @RequestMapping(value = "/del" , method = RequestMethod.DELETE)
    public RespBean del(@RequestBody String jsondata){
        return leadgerService.delete(jsondata);
    }

    @RequestMapping(value = "/update" , method = RequestMethod.PUT)
    public RespBean update(@RequestBody String jsondata){
        return leadgerService.update(jsondata);
    }

    @RequestMapping(value = "/query" , method = RequestMethod.GET)
    public RespBean query(String name,  String currentpage, String pagesize,  String parentname, HttpServletRequest request, HttpServletResponse response){
        RespBean re;
        JSONObject jsondata=new JSONObject();
        jsondata.put("name",name);
        jsondata.put("parentname",parentname);
        jsondata.put("pagesize",pagesize);
        jsondata.put("currentpage",currentpage);
        re=leadgerService.query(jsondata.toString());
        return re;
    }
}
