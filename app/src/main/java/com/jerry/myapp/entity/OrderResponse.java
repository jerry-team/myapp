package com.jerry.myapp.entity;

import java.io.Serializable;
import java.util.List;


public class OrderResponse implements Serializable {
    private Integer code;
    private String msg;
    private List<OrderEntity> data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<OrderEntity> getData() {
        return data;
    }

    public void setData(List<OrderEntity> data) {
        this.data = data;
    }
}
