package com.daisy.bangsen.controller.bussiness;


import cn.hutool.json.JSONObject;
import com.daisy.bangsen.service.CsaleService;
import com.daisy.bangsen.util.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/csale")
@CrossOrigin
public class CsaleController {
    @Autowired
    CsaleService csaleService;

    @RequestMapping(value = "/add" , method = RequestMethod.POST)
    public RespBean regist(@RequestBody String jsondata){
        return csaleService.save(jsondata);
    }

    @RequestMapping(value = "/del" , method = RequestMethod.DELETE)
    public RespBean del(@RequestBody String jsondata){
        return csaleService.delete(jsondata);
    }

    @RequestMapping(value = "/update" , method = RequestMethod.PUT)
    public RespBean update(@RequestBody String jsondata){
        return csaleService.update(jsondata);
    }

    @RequestMapping(value = "/query" , method = RequestMethod.GET)
    public RespBean query(String receiptNumber,  String currentpage, String pagesize,  String salesName,  String customerName,String status){
        RespBean re;
        JSONObject jsondata=new JSONObject();
        jsondata.put("receiptNumber",receiptNumber);
        jsondata.put("salesName",salesName);
        jsondata.put("customerName",customerName);
        jsondata.put("status",status);
        jsondata.put("pagesize",pagesize);
        jsondata.put("currentpage",currentpage);
        re=csaleService.query(jsondata.toString());
        return re;
    }
}
