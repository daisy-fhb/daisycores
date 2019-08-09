package com.daisy.cbsx.controller;

import com.daisy.cbsx.service.SanxingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("sx")
@CrossOrigin
public class SanxingController {

    @Autowired
    SanxingService sanxingService;


    @RequestMapping(value = "query",produces = "application/json;charset=utf-8")
    String query(@RequestBody String postData, HttpServletRequest request) {
        return sanxingService.query(postData, request);
    }

    @RequestMapping("upload")
    String upload(HttpServletRequest request) {
        return sanxingService.upload(request);
    }

    @RequestMapping("delete")
    String delete(@RequestBody String postData, HttpServletRequest request) {
        return sanxingService.delete(postData, request);
    }
}
