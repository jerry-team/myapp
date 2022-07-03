package com.jerry.myapp.entity;

public class ShopCartResponse {
    /**
     * code : 200
     * msg : 操作成功
     * data : {"id":5,"createTime":null,"updateTime":null,"userId":1,"commodityId":5,"num":1}
     */

    private int code;
    private String msg;
    private ShopCartEntity data;

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

    public ShopCartEntity getData() {
        return data;
    }

    public void setData(ShopCartEntity data) {
        this.data = data;
    }

}
