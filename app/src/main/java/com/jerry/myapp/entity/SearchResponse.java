package com.jerry.myapp.entity;

import java.util.List;

public class SearchResponse {
    private int code;
    private String msg;
    private List<SearchEntity> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<SearchEntity> getData() {
        return data;
    }

    public void setData(List<SearchEntity> data) {
        this.data = data;
    }
}
