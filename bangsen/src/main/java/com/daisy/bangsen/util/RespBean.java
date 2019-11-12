package com.daisy.bangsen.util;

import lombok.Data;


@Data
public class RespBean {
    private int status;
    private String msg;
    private Object data;

    public RespBean() {
    }

    public RespBean(int status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data=data;
    }
}
