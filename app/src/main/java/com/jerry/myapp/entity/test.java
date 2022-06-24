package com.jerry.myapp.entity;

public class test {
    /**
     * code : 200
     * msg : 操作成功
     * data : {"username":"719622388","password":"123456a","telephone":"17750432514","nickname":"Jerry","icon":null,"token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI3MTk2MjIzODgiLCJpYXQiOjE2NTU5NTUwNjMsImV4cCI6MTY1NjU1OTg2M30._JIz9PmAGc9psHHgQgtW_QeLWKq0iyjXRDfobHpkGChutUZrAXWQV0jq6eozbTOh3edw1XedMVNqgV_xklO-1Q"}
     */

    private int code;
    private String msg;
    private user data;

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

    public user getData() {
        return data;
    }

    public void setData(user data) {
        this.data = data;
    }

    public static class user {
        /**
         * username : 719622388
         * password : 123456a
         * telephone : 17750432514
         * nickname : Jerry
         * icon : null
         * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI3MTk2MjIzODgiLCJpYXQiOjE2NTU5NTUwNjMsImV4cCI6MTY1NjU1OTg2M30._JIz9PmAGc9psHHgQgtW_QeLWKq0iyjXRDfobHpkGChutUZrAXWQV0jq6eozbTOh3edw1XedMVNqgV_xklO-1Q
         */

        private String username;
        private String password;
        private String telephone;
        private String nickname;
        private Object icon;
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
    }
}
