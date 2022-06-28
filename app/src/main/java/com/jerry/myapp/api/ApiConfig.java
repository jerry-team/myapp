package com.jerry.myapp.api;

public class ApiConfig {
    public static final int PAGE_SIZE = 2;

    public static final String BASE_URl = "http://10.0.2.2:8001";


    public static final String LOGIN = "/user/login"; //登录
    public static final String REGISTER = "/user/register"; //注册
    public static final String CATEGORY_LIST = "/category/list";//类别查询
    public static final String Goods_LIST_PAGE = "/commodity/pageQuery";//商品分页查询
}
