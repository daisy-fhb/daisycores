package com.daisy.bangsen.service;


import com.daisy.bangsen.util.RespBean;

public interface SupplierPriceService {
    RespBean save(String postData);

    RespBean delete(String id);

    RespBean update(String postData);

    RespBean query(String postData);
}
