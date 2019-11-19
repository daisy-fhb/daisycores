package com.daisy.bangsen.controller.bussiness;


import cn.hutool.json.JSONObject;
import com.daisy.bangsen.service.HouseInOutService;
import com.daisy.bangsen.util.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/houseinout")
@CrossOrigin
public class HouseInOutController {
    @Autowired
    HouseInOutService houseInOutService;

    @RequestMapping(value = "/add" , method = RequestMethod.POST)
    public RespBean regist(@RequestBody String jsondata){
        return houseInOutService.save(jsondata);
    }

    @RequestMapping(value = "/del" , method = RequestMethod.DELETE)
    public RespBean del(@RequestBody String jsondata){
        return houseInOutService.delete(jsondata);
    }

    @RequestMapping(value = "/update" , method = RequestMethod.PUT)
    public RespBean update(@RequestBody String jsondata){
        return houseInOutService.update(jsondata);
    }

    @RequestMapping(value = "/query" , method = RequestMethod.GET)
    public RespBean query(String  receiptNumber,  String currentpage, String pagesize,  String inOutName,  String warehouseName,  String  inOutType){
        RespBean re;
        JSONObject jsondata=new JSONObject();
        jsondata.put("receiptNumber",receiptNumber);
        jsondata.put("inOutName",inOutName);
        jsondata.put("warehouseName",warehouseName);
        jsondata.put("inOutType", inOutType);
        jsondata.put("pagesize",pagesize);
        jsondata.put("currentpage",currentpage);
        re=houseInOutService.query(jsondata.toString());
        return re;
    }
}
