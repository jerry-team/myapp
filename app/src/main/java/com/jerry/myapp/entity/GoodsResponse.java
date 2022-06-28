package com.jerry.myapp.entity;

import java.io.Serializable;
import java.util.List;

public class GoodsResponse implements Serializable {
    /**
     * code : 200
     * msg : 操作成功
     * data : [{"id":4,"createTime":"2022-06-27T09:52:23.000+00:00","updateTime":"2022-06-27T11:18:34.000+00:00","name":"布偶猫","price":1000,"desription":"这是一条贼牛逼的猫，买了能咬人，再也不用怕打架打不过","category":1,"number":1,"imgurl":"https://s1.xoimg.com/i/2022/06/27/i7ja5g.png","videourl":null}]
     */

    private int code;
    private String msg;
    private List<GoodsEntity> data;

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

    public List<GoodsEntity> getData() {
        return data;
    }

    public void setData(List<GoodsEntity> data) {
        this.data = data;
    }

}
