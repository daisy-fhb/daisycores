package com.daisy.bangsen.controller.bussiness;


import cn.hutool.json.JSONObject;
import com.daisy.bangsen.service.InventoryService;
import com.daisy.bangsen.util.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
@CrossOrigin
public class InventoryController {
    @Autowired
    InventoryService inventoryService;

    @RequestMapping(value = "/add" , method = RequestMethod.POST)
    public RespBean regist(@RequestBody String jsondata){
        return inventoryService.save(jsondata);
    }

    @RequestMapping(value = "/del" , method = RequestMethod.DELETE)
    public RespBean del(@RequestBody String jsondata){
        return inventoryService.delete(jsondata);
    }

    @RequestMapping(value = "/update" , method = RequestMethod.PUT)
    public RespBean update(@RequestBody String jsondata){
        return inventoryService.update(jsondata);
    }

    @RequestMapping(value = "/query" , method = RequestMethod.GET)
    public RespBean query(String serialName,  String currentpage, String pagesize,  String warehouseName){
        RespBean re;
        JSONObject jsondata=new JSONObject();
        jsondata.put("serialName",serialName);
        jsondata.put("warehouseName",warehouseName);
        jsondata.put("pagesize",pagesize);
        jsondata.put("currentpage",currentpage);
        re=inventoryService.query(jsondata.toString());
        return re;
    }
}
