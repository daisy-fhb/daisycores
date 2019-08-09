package com.daisy.bangsen.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;

public class QRUtils {

    public static HttpServletResponse getQR(String str, HttpServletResponse response){
        try {
            QrConfig config = new QrConfig(300, 300);
            // 设置边距，既二维码和背景之间的边距
            config.setMargin(3);
            // 设置前景色，既二维码颜色
            config.setForeColor(Color.BLACK.getRGB());
            // 设置背景色（灰色）
            config.setBackColor(Color.YELLOW.getRGB());
            // 生成二维码到文件，也可以到流
//            QrCodeUtil.generate(str,config, FileUtil.file("E:\\迅雷下载\\a.png"));
           response.getOutputStream().write(QrCodeUtil.generatePng(str,config));
           response.getOutputStream().close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }

    public static String decoeQR(){
        String decode = QrCodeUtil.decode(FileUtil.file("E:\\迅雷下载\\a.png"));
        return decode;
    }
}
