package com.daisy.bangsen.controller.producement;


import cn.hutool.json.JSONObject;
import com.daisy.bangsen.service.SimpleProduceService;
import com.daisy.bangsen.util.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/simpleproduce")
@CrossOrigin
public class SimpleProduceController {
    @Autowired
    SimpleProduceService simpleProduceService;

    @RequestMapping(value = "/add" , method = RequestMethod.POST)
    public RespBean regist(@RequestBody String jsondata){
        return simpleProduceService.save(jsondata);
    }

    @RequestMapping(value = "/del" , method = RequestMethod.DELETE)
    public RespBean del(@RequestBody String jsondata){
        return simpleProduceService.delete(jsondata);
    }

    @RequestMapping(value = "/update" , method = RequestMethod.PUT)
    public RespBean update(@RequestBody String jsondata){
        return simpleProduceService.update(jsondata);
    }

    @RequestMapping(value = "/query" , method = RequestMethod.GET)
    public RespBean query(String productName,  String currentpage, String  receiptNumber,  String productionStatus, String pagesize){
        RespBean re;
        JSONObject jsondata=new JSONObject();
        jsondata.put("productName",productName);
        jsondata.put("receiptNumber",receiptNumber);
        jsondata.put("productionStatus",productionStatus);
        jsondata.put("pagesize",pagesize);
        jsondata.put("currentpage",currentpage);
        re=simpleProduceService.query(jsondata.toString());
        return re;
    }
}
