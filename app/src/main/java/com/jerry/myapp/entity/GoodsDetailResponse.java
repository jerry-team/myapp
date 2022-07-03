package com.jerry.myapp.entity;

public class GoodsDetailResponse {
    /**
     * code : 200
     * msg : 操作成功
     * data : {"id":2,"createTime":"2022-06-27T09:50:31.000+00:00","updateTime":"2022-07-04T00:57:48.000+00:00","name":"哈巴狗","price":23244.4,"description":"这是一条贼牛逼的狗，买了能咬人，再也不用怕打架打不过","category":2,"number":1,"imgurl":"dog2.jpg","videourl":null,"vaccin":1,"isPedigree":1,"isPest":1,"breed":"美国哈巴狗"}
     */

    private int code;
    private String msg;
    private GoodsEntity data;

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

    public GoodsEntity getData() {
        return data;
    }

    public void setData(GoodsEntity data) {
        this.data = data;
    }

}
