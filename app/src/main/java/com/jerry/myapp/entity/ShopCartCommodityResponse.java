package com.jerry.myapp.entity;

import java.util.List;


public class ShopCartCommodityResponse {
    private Integer code;
    private String msg;
    private List<ShopBean> data;

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

    public List<ShopBean> getData() {
        return data;
    }

    public void setData(List<ShopBean> data) {
        this.data = data;
    }


    public static class ShopBean {
        private Integer id;
        private String name;
        private String shopAddress;
        private String description;
        private boolean isChecked;
        private List<GoodsEntity> commodityList;

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getShopAddress() {
            return shopAddress;
        }

        public void setShopAddress(String shopAddress) {
            this.shopAddress = shopAddress;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<GoodsEntity> getCommodityList() {
            return commodityList;
        }

        public void setCommodityList(List<GoodsEntity> commodityList) {
            this.commodityList = commodityList;
        }


    }
}
