package com.jerry.myapp.api;

public class ApiConfig {
    public static final int PAGE_SIZE = 5;

    public static final String BASE_URl = "http://10.0.2.2:8001";
    public static final String LOGIN = "/user/login"; //登录
    public static final String REGISTER = "/user/register"; //注册
    public static final String CATEGORY_LIST = "/category/list";//类别查询
    public static final String CHECK_TOKEN = "/user/checktoken"; //判断token是否过期
}
