package com.daisy.pirelli.controller;

import cn.hutool.json.JSONObject;
import com.daisy.pirelli.service.TestService;
import com.daisy.pirelli.util.CaptchaUtils;
import com.daisy.pirelli.util.IPUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("testbs")
@CrossOrigin
public class TestController {

    @Autowired
    TestService testService;

    @RequestMapping("add")
    JSONObject save(@RequestBody String postData) {
        return testService.save(postData);
    }

    @RequestMapping("delete")
    JSONObject delete(@RequestBody String id) {
        return testService.delete(id);
    }

    @RequestMapping("update")
    JSONObject update(@RequestBody String postData) {
        return testService.update(postData);
    }


    @RequestMapping("query")
    JSONObject query(@RequestBody String postData) {
        return testService.query(postData);
    }

    @RequestMapping("query2")
    JSONObject query2(@RequestBody String postData) {
        return testService.querybymutitable(postData);
    }

    @RequestMapping("upload")
    JSONObject upload(HttpServletRequest request) {
        return testService.upload(request);
    }

    @RequestMapping("test")
    JSONObject test(HttpServletRequest request) {
        return IPUtils.getRequestIPInfo(request);
    }

    @RequestMapping("test2")
    public void test2(HttpServletResponse response) {
//        MailUtil.send("daisyfhb@gmail.com", "测试", "邮件来自daisyfhb's 测试", false);
//        QRUtils.getQR("http://daisyfhb.cn/",response);
        CaptchaUtils.getCaptCha(response);
    }

}
