package com.daisy.bangsen.service.Impl;

import com.daisy.bangsen.service.IndexService;
import com.daisy.bangsen.util.RespBean;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@Transactional
public class IndexServiceImpl implements IndexService {
    @Override
    public RespBean top() {
        return null;
    }

    @Override
    public RespBean monthly() {
        return null;
    }

    @Override
    public RespBean dept() {
        return null;
    }

    @Override
    public RespBean dutyrecord() {
        return null;
    }
}
