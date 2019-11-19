package com.daisy.bangsen.controller.financial;


import cn.hutool.json.JSONObject;
import com.daisy.bangsen.service.SalaryService;
import com.daisy.bangsen.util.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/salary")
@CrossOrigin
public class SalaryController {
    @Autowired
    SalaryService salaryService;

    @RequestMapping(value = "/add" , method = RequestMethod.POST)
    public RespBean regist(@RequestBody String jsondata){
        return salaryService.save(jsondata);
    }

    @RequestMapping(value = "/del" , method = RequestMethod.DELETE)
    public RespBean del(@RequestBody String jsondata){
        return salaryService.delete(jsondata);
    }

    @RequestMapping(value = "/update" , method = RequestMethod.PUT)
    public RespBean update(@RequestBody String jsondata){
        return salaryService.update(jsondata);
    }

    @RequestMapping(value = "/query" , method = RequestMethod.GET)
    public RespBean query(String name,  String currentpage, String pagesize){
        RespBean re;
        JSONObject jsondata=new JSONObject();
        jsondata.put("staffName",name);
        jsondata.put("pagesize",pagesize);
        jsondata.put("currentpage",currentpage);
        re=salaryService.query(jsondata.toString());
        return re;
    }
}
