package com.jerry.myapp.entity;


import java.util.Date;

public class DefaultAddressResponse {
    private Integer code;
    private String msg;
    private DefaultAddress data;

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

    public DefaultAddress getData() {
        return data;
    }

    public void setData(DefaultAddress data) {
        this.data = data;
    }

    public static class DefaultAddress {
        private Integer id;
        private String createTime;
        private String updateTime;
        private String name;
        private Long telephone;
        private String address;
        private Integer defaultAddress;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
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

        public Long getTelephone() {
            return telephone;
        }

        public void setTelephone(Long telephone) {
            this.telephone = telephone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Integer getDefaultAddress() {
            return defaultAddress;
        }

        public void setDefaultAddress(Integer defaultAddress) {
            this.defaultAddress = defaultAddress;
        }
    }
}
