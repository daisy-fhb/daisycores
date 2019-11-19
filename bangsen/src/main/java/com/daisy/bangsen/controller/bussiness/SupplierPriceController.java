package com.daisy.bangsen.controller.bussiness;


import cn.hutool.json.JSONObject;
import com.daisy.bangsen.service.SupplierPriceService;
import com.daisy.bangsen.util.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/supplierprice")
@CrossOrigin
public class SupplierPriceController {
    @Autowired
    SupplierPriceService supplierPriceService;

    @RequestMapping(value = "/add" , method = RequestMethod.POST)
    public RespBean regist(@RequestBody String jsondata){
        return supplierPriceService.save(jsondata);
    }

    @RequestMapping(value = "/del" , method = RequestMethod.DELETE)
    public RespBean del(@RequestBody String jsondata){
        return supplierPriceService.delete(jsondata);
    }

    @RequestMapping(value = "/update" , method = RequestMethod.PUT)
    public RespBean update(@RequestBody String jsondata){
        return supplierPriceService.update(jsondata);
    }

    @RequestMapping(value = "/query" , method = RequestMethod.GET)
    public RespBean query(String supplierName,  String currentpage, String pagesize,  String itemName){
        RespBean re;
        JSONObject jsondata=new JSONObject();
        jsondata.put("supplierName",supplierName);
        jsondata.put("itemName",itemName);
        jsondata.put("pagesize",pagesize);
        jsondata.put("currentpage",currentpage);
        re=supplierPriceService.query(jsondata.toString());
        return re;
    }
}
