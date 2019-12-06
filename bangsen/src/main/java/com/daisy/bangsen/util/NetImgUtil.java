package com.daisy.bangsen.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.img.Img;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.RandomUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetImgUtil {

    //获取网络图片并压缩
    public static void ImageRequestAndScale(String GifUrl, HttpServletResponse response) {
        String path = "/home/bangsen/tmpcode/authcode/";
        HttpURLConnection conn = null;
        InputStream inputStream = null;
        try {
            //new一个URL对象
            URL url = new URL(GifUrl);
            //打开链接
            conn = (HttpURLConnection) url.openConnection();
            //设置请求方式为"GET"
            conn.setRequestMethod("GET");
            //超时响应时间为5秒
            conn.setConnectTimeout(5 * 1000);
            //通过输入流获取图片数据
            inputStream = conn.getInputStream();
            if (inputStream != null) {
                Img img = Img.from(inputStream);
                img.scale(0.1F);
                if (!FileUtil.exist(path)) {
                    FileUtil.mkdir(path);
                }
                path += RandomUtil.randomString(5) + ".jpg";
                img.write(new File(path));
                byte[] data = NetImgUtil.readInputStream(new FileInputStream(new File(path)));
                //写入数据
                response.getOutputStream().write(data);
                response.getOutputStream().flush();
            }
        } catch (Exception e) {  } finally {
            try {
                //关闭输入输出流
                response.getOutputStream().close();
                inputStream.close();
                conn.disconnect();
                FileUtil.del(path);
            } catch (Exception e) { }
        }
    }

        //获取本地图片并压缩
    public static  void ImageLocalAndScale(HttpServletResponse response) {
        InputStream inputStream = null;
        try {
            //通过输入流获取图片数据
            String FileName="authcode ("+RandomUtil.randomInt(1,15)+").jpg";
            inputStream = ResourceUtil.getStreamSafe("classpath://pic/"+FileName);
            if (inputStream != null) {
                byte[] data = NetImgUtil.readInputStream(inputStream);
                //写入数据
                response.getOutputStream().write(data);
                response.getOutputStream().flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭输入输出流
                response.getOutputStream().close();
                inputStream.close();
            } catch (Exception e) { }
        }
    }



    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        //创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        //每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        //使用一个输入流从buffer里把数据读取出来
        while ((len = inStream.read(buffer)) != -1) {
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        //关闭输入流
        inStream.close();
        //把outStream里的数据写入内存
        return outStream.toByteArray();
    }

    public static String getTimeFlag() {
        String now = DateUtil.now().replaceAll(":", "").replaceAll("-", "").replaceAll(" ", "").trim();
        return now;
    }
}
