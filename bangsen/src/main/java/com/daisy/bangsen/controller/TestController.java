package com.daisy.bangsen.controller;

import cn.hutool.extra.mail.MailUtil;
import cn.hutool.json.JSONObject;
import com.daisy.bangsen.controller.im.ImController;
import com.daisy.bangsen.service.TestService;
import com.daisy.bangsen.util.CaptchaUtils;
import com.daisy.bangsen.util.IPUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("testbs")
@CrossOrigin
public class TestController {

    @Autowired
    TestService testService;
    @Autowired
    ImController imController;


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
        MailUtil.send("daisyfhb@gmail.com", "Re:请查收验证码", "验证码为："+ CaptchaUtils.getCaptCha(response), true);
//        QRUtils.getQR("http://daisyfhb.cn/",response);
    }

    @RequestMapping("push/{cid}")
    public void sendMsg(@RequestBody String msg,@PathVariable String cid) {
        try {
            imController.sendInfo(msg,cid);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
