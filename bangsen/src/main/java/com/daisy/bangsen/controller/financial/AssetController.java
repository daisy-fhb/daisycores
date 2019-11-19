package com.daisy.bangsen.controller.financial;


import cn.hutool.json.JSONObject;
import com.daisy.bangsen.service.AssetService;
import com.daisy.bangsen.util.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/asset")
@CrossOrigin
public class AssetController {
    @Autowired
    AssetService assetService;

    @RequestMapping(value = "/add" , method = RequestMethod.POST)
    public RespBean regist(@RequestBody String jsondata){
        return assetService.save(jsondata);
    }

    @RequestMapping(value = "/del" , method = RequestMethod.DELETE)
    public RespBean del(@RequestBody String jsondata){
        return assetService.delete(jsondata);
    }

    @RequestMapping(value = "/update" , method = RequestMethod.PUT)
    public RespBean update(@RequestBody String jsondata){
        return assetService.update(jsondata);
    }

    @RequestMapping(value = "/query" , method = RequestMethod.GET)
    public RespBean query(String assetName,  String currentpage, String pagesize,  String  type,  String status){
        RespBean re;
        JSONObject jsondata=new JSONObject();
        jsondata.put("assetName",assetName);
        jsondata.put("type", type);
        jsondata.put("status", status);
        jsondata.put("pagesize",pagesize);
        jsondata.put("currentpage",currentpage);
        re=assetService.query(jsondata.toString());
        return re;
    }
}
