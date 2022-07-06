package com.jerry.myapp.entity;

public class ShopResponse {
    /**
     * code : 200
     * msg : 操作成功
     * data : {"id":1,"createTime":1656581829000,"updateTime":1656581835000,"name":"宠物市场","shopAddress":"厦门"}
     */

    private int code;
    private String msg;
    private ShopEntity data;

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

    public ShopEntity getData() {
        return data;
    }

    public void setData(ShopEntity data) {
        this.data = data;
    }

}
