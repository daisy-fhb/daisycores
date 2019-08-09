package com.daisy.bangsen.util;

import cn.hutool.http.HttpUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import cn.hutool.json.JSONObject;

import javax.servlet.http.HttpServletRequest;

public class IPUtils {

    /**
     * 获取发起请求的IP地址
     * @param request
     * @return
     */
    private static String getIp(HttpServletRequest request) {
        //代理进来，则透过防火墙获取真实IP地址
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

            ip = request.getHeader("Proxy-Client-IP");

        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

            ip = request.getHeader("WL-Proxy-Client-IP");

        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

            ip = request.getHeader("HTTP_CLIENT_IP");


            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

                ip = request.getHeader("HTTP_X_FORWARDED_FOR");

            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

                ip = request.getHeader("X-Real-IP");

            }
            //如果没有代理，则获取真实ip
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

                ip = request.getRemoteAddr();

            }
        }
            return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
        }


    /**
     * 将IP地址转换为地区
     * @param ip
     * @return
     */
    private static String ip2region(String ip) {
            String result ="";
            try {
                String requststr = "http://freeapi.ipip.net/"+ ip;
                String re = HttpUtil.get(requststr, 3000);
                for (String str:re.split(",")){
                    result+=str.replaceAll("\"","").replaceAll("\\[","").
                            replaceAll("\\]","").replaceAll("linode.com","");
                }
            } catch (Exception e) {
                result= "无法解析IP地址";
            }
            return result;
        }

    /**
     * 根据请求获取IP信息
     * @param request
     * @return
     */
    public static JSONObject getRequestIPInfo(HttpServletRequest request){
            UserAgent userAgent= UserAgentUtil.parse(request.getHeader("User-Agent"));
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("ip",getIp(request));
            jsonObject.put("Area",ip2region(jsonObject.getStr("ip")));
            jsonObject.put("Browser", userAgent.getBrowser().getName());
            jsonObject.put("Os",userAgent.getOs().getName().contains("or")?"Windows 10":userAgent.getOs().getName());
            return jsonObject;
        }
    }
