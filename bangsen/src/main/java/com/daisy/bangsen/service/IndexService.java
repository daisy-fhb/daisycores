package com.daisy.bangsen.service;


import com.daisy.bangsen.util.RespBean;

public interface IndexService {
    RespBean top();

    RespBean monthly();

    RespBean dept();

    RespBean dutyrecord();
}
