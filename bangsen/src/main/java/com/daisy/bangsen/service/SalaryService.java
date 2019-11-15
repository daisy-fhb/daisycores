package com.daisy.bangsen.service;


import com.daisy.bangsen.util.RespBean;

public interface SalaryService {
    RespBean save(String postData);

    RespBean delete(String id);

    RespBean update(String postData);

    RespBean query(String postData);
}
