package com.daisy.bangsen.controller;


import com.daisy.bangsen.service.IndexService;
import com.daisy.bangsen.util.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("index")
@CrossOrigin
public class IndexController {

    @Autowired
    IndexService indexService;


    @RequestMapping("top")
   public RespBean top() {
        return indexService.top();
    }

    @RequestMapping("monthly")
   public RespBean monthly() {
        return indexService.monthly();
    }

    @RequestMapping("dept")
    public RespBean dept() {
        return indexService.dept();
    }

    @RequestMapping("dutyrecord")
   public RespBean dutyrecord() {
        return indexService.dutyrecord();
    }

}
