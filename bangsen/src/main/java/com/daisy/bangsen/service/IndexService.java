package com.daisy.bangsen.service;


import com.daisy.bangsen.util.RespBean;

import java.util.HashMap;

public interface IndexService {
    RespBean top();

    RespBean monthly();

    RespBean dept();

    RespBean dutyrecord();

    HashMap selectDataList(String type);
}
