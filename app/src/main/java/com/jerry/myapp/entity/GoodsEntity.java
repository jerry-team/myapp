package com.jerry.myapp.entity;

import java.io.Serializable;

public class GoodsEntity implements Serializable {
    /**
     * id : 4
     * createTime : 2022-06-27T09:52:23.000+00:00
     * updateTime : 2022-06-27T11:18:34.000+00:00
     * name : 布偶猫
     * price : 1000.0
     * desription : 这是一条贼牛逼的猫，买了能咬人，再也不用怕打架打不过
     * category : 1
     * number : 1
     * imgurl : https://s1.xoimg.com/i/2022/06/27/i7ja5g.png
     * videourl : null
     */

    private int id;
    private String createTime;
    private String updateTime;
    private String name;
    private double price;
    private String desription;
    private int category;
    private int number;
    private String imgurl;
    private Object videourl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDesription() {
        return desription;
    }

    public void setDesription(String desription) {
        this.desription = desription;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public Object getVideourl() {
        return videourl;
    }

    public void setVideourl(Object videourl) {
        this.videourl = videourl;
    }
}