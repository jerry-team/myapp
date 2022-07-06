package com.jerry.myapp.entity;

import java.io.Serializable;

public class LoginResponse implements Serializable {
    /**
     * code : 200
     * msg : 操作成功
     * data : {"username":"719622388","password":"123456a","telephone":"17750432514","nickname":"Jerry","icon":null,"token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI3MTk2MjIzODgiLCJpYXQiOjE2NTU4NjMzNzUsImV4cCI6MTY1NjQ2ODE3NX0.rxEiZXXPby1fo8Dnmev1AGjEu69f6KBmd9QglAj5qIB_f6_-MG1ErrfWyFxBD_VKr4-4vZt81Ph5zDmIu10f2w"}
     */

    private int code;
    private String msg;
    private UserBean data;

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

    public UserBean getData() {
        return data;
    }

    public void setData(UserBean data) {
        this.data = data;
    }

    public static class UserBean {
        /**
         * username : 719622388
         * password : 123456a
         * telephone : 17750432514
         * nickname : Jerry
         * icon : null
         * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI3MTk2MjIzODgiLCJpYXQiOjE2NTU4NjMzNzUsImV4cCI6MTY1NjQ2ODE3NX0.rxEiZXXPby1fo8Dnmev1AGjEu69f6KBmd9QglAj5qIB_f6_-MG1ErrfWyFxBD_VKr4-4vZt81Ph5zDmIu10f2w
         */

        private String username;
        private String password;
        private String telephone;
        private String nickname;
        private Object icon;
        private String email;
        private String token;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public Object getIcon() {
            return icon;
        }

        public void setIcon(Object icon) {
            this.icon = icon;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
