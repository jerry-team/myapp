package com.jerry.myapp.entity;

import java.util.List;

public class AddressResponse {
    private int code;
    private String msg;
    List<AddressInfo> data;

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

    public List<AddressInfo> getData() {
        return data;
    }

    public void setData(List<AddressInfo> data) {
        this.data = data;
    }

    public static class AddressInfo{
        private Integer id;
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
