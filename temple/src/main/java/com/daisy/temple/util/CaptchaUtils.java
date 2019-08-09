package com.daisy.temple.util;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;

import javax.servlet.http.HttpServletResponse;

public class CaptchaUtils {

    public static String getCaptCha(HttpServletResponse response){
        //定义图形验证码的长和宽
//        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(200, 100);
         CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(200, 100, 6, 30);
        try {
//            lineCaptcha.createCode();//重新生成验证码
//            lineCaptcha.write("E:\\迅雷下载//line.png");
            captcha.write(response.getOutputStream());
            response.getOutputStream().close();
        }catch (Exception e){
            e.printStackTrace();
        }
       return captcha.getCode();
    }

}
