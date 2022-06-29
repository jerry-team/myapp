package com.jerry.myapp.entity;

import java.io.Serializable;

public class CategoryEntity implements Serializable {
    /**
     * id : 1
     * createTime : 2022-06-27T09:42:45.000+00:00
     * updateTime : 2022-06-27T09:44:17.000+00:00
     * name : 猫咪
     * type : 0
     */

    private int id;
    private String createTime;
    private String updateTime;
    private String name;
    private int type;

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}