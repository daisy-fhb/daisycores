package com.daisy.cbsx.service;

import javax.servlet.http.HttpServletRequest;

public interface SanxingService {
    String upload(HttpServletRequest request);

    String query(String postData, HttpServletRequest request);

    String delete(String postData, HttpServletRequest request);
}
