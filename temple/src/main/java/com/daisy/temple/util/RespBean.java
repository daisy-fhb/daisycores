package com.daisy.temple.util;

import lombok.Data;

@Data
public class RespBean {
    private boolean flag;
    private String status;
    private String msg;

    public RespBean() {
    }

    public RespBean(boolean flag, String status, String msg) {
        this.flag=flag;
        this.status = status;
        this.msg = msg;
    }
}
