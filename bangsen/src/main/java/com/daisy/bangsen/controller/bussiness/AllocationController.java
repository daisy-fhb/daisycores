package com.daisy.bangsen.controller.bussiness;


import cn.hutool.json.JSONObject;
import com.daisy.bangsen.service.AllocationService;
import com.daisy.bangsen.util.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/allocation")
@CrossOrigin
public class AllocationController {
    @Autowired
    AllocationService allocationService;

    @RequestMapping(value = "/add" , method = RequestMethod.POST)
    public RespBean regist(@RequestBody String jsondata){
        return allocationService.save(jsondata);
    }

    @RequestMapping(value = "/del" , method = RequestMethod.DELETE)
    public RespBean del(@RequestBody String jsondata){
        return allocationService.delete(jsondata);
    }

    @RequestMapping(value = "/update" , method = RequestMethod.PUT)
    public RespBean update(@RequestBody String jsondata){
        return allocationService.update(jsondata);
    }

    @RequestMapping(value = "/query" , method = RequestMethod.GET)
    public RespBean query(String allocateDate,  String currentpage, String pagesize,  String serialName){
        RespBean re;
        JSONObject jsondata=new JSONObject();
        jsondata.put("allocateDate",allocateDate);
        jsondata.put("serialName",serialName);
        jsondata.put("pagesize",pagesize);
        jsondata.put("currentpage",currentpage);
        re=allocationService.query(jsondata.toString());
        return re;
    }
}
