package com.daisy.temple.service;

import cn.hutool.json.JSONObject;

import javax.servlet.http.HttpServletRequest;

public interface TestService {
    JSONObject upload(HttpServletRequest request);

    JSONObject save(String postData);

    JSONObject delete(String id);

    JSONObject update(String postData);

    JSONObject query(String postData);

    JSONObject querybymutitable(String postData);
}
