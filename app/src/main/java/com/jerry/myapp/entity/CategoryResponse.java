package com.jerry.myapp.entity;

import java.io.Serializable;
import java.util.List;

public class CategoryResponse implements Serializable {
    /**
     * code : 200
     * msg : 操作成功
     * data : [{"id":1,"createTime":"2022-06-27T09:42:45.000+00:00","updateTime":"2022-06-27T09:44:17.000+00:00","name":"猫咪","type":0},{"id":2,"createTime":"2022-06-27T09:43:07.000+00:00","updateTime":"2022-06-27T09:44:23.000+00:00","name":"狗狗","type":0},{"id":3,"createTime":"2022-06-27T09:44:39.000+00:00","updateTime":null,"name":"金鱼","type":0},{"id":4,"createTime":"2022-06-27T09:44:45.000+00:00","updateTime":null,"name":"仓鼠","type":0},{"id":5,"createTime":"2022-06-27T14:32:35.000+00:00","updateTime":null,"name":"宠物周边","type":1},{"id":6,"createTime":"2022-06-27T14:33:04.000+00:00","updateTime":null,"name":"宠物粮食","type":1},{"id":7,"createTime":"2022-06-27T14:33:12.000+00:00","updateTime":null,"name":"宠物电器","type":1},{"id":8,"createTime":"2022-06-27T14:33:56.000+00:00","updateTime":null,"name":"宠物美容","type":1}]
     */

    private int code;
    private String msg;
    private List<CategoryEntity> data;

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

    public List<CategoryEntity> getData() {
        return data;
    }

    public void setData(List<CategoryEntity> data) {
        this.data = data;
    }

}
