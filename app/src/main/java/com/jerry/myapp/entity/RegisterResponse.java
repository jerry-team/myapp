package com.jerry.myapp.entity;

import java.io.Serializable;

public class RegisterResponse implements Serializable {
    private int code;
    private String msg;
    private RegisterBean data;

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

    public RegisterBean getData() {
        return data;
    }

    public void setData(RegisterBean data) {
        this.data = data;
    }


    public static class RegisterBean {
        private String rusername;
        private String rpassword;
        private String rpwd;
        private String repwd;
        private String remail;
        private String rphone;

        public String getRusername() {
            return rusername;
        }

        public void setRusername(String rusername) {
            this.rusername = rusername;
        }

        public String getRpassword() {
            return rpassword;
        }

        public void setRpassword(String rpassword) {
            this.rpassword = rpassword;
        }

        public String getRpwd() {
            return rpwd;
        }

        public void setRpwd(String rpwd) {
            this.rpwd = rpwd;
        }

        public String getRepwd() {
            return repwd;
        }

        public void setRepwd(String repwd) {
            this.repwd = repwd;
        }

        public String getRemail() {
            return remail;
        }

        public void setRemail(String remail) {
            this.remail = remail;
        }

        public String getRphone() {
            return rphone;
        }

        public void setRphone(String rphone) {
            this.rphone = rphone;
        }

    }
}
