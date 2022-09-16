package com.jerry.myapp.api;

public class ApiConfig {

    public static final String BASE_URl = "http://10.0.2.2:8001";
    public static final String LOGIN = "/user/login"; //登录
    public static final String REGISTER = "/user/register"; //注册
    public static final String CATEGORY_LIST = "/category/list";//类别查询
    public static final String CHECK_TOKEN = "/user/checktoken"; //判断token是否过期
    public static final int PAGE_SIZE = 2;
    public static final String Goods_LIST_PAGE = "/commodity/pageQuery";//商品分页查询
    public static final String GETGOODS_BYID = "/commodity/listById";//商品查询（id）
    public static final String NICKNAME ="/user/nickname";//获取昵称
    public static final String EDITNICKNAME="/user/editnickname";//修改昵称
    public static final String EDITEMAIL="/user/editemail";//修改邮箱
    public static final String EDITPHONE="/user/editphone";//修改电话

    public static final String INSERT_SHOP_CART= "/shop-cart/insertByCommodityId";//商品分页查询
    public static final String GETSHOPDETAIL= "/shop/getByCommodityId";//商店详情信息查询

    public static final String LISTSHOPCART="/shop-cart/listShop";//购物车获取商品
    public static final String DELSHOPCART="/shop-cart/delList";//购物车删除商品

    public static final String ADDADRESS = "/address/add";//添加收货地址
    public static final String ListAddress = "/address/list";//查询收货地址列表


    public static final String SEARCHGOODS= "/search/pageQuery"; //商品搜索，分页加载，瀑布刷新

    public static final String HISTORYSEARCH="/search/showSearchByTimeLimit"; //历史搜索
    public static final String OFTENSEARCH="/search/showSearchByNumLimit"; //常用搜索
    public static final String DELETESEARCH="/search/deleteAllSearchById"; //删除所有搜索记录
    public static final String DELETESEARCH_CHECK=""; //删除选中的搜索标签

    public static final String APPLYMEMBER = "/user/update2"; //申请成为会员

    public static final String GETCOMMENT="/comment/listByCommodityId"; //根据商品id查询评论
    public static final String ADDCOMMENT="/comment/add"; //添加评论
    public static final String TEST="/comment/test"; //添加订单
    public static final String ADDORDER="/orders/addOrder"; //测试
    public static final String PAYORDER="/orders/payOrder"; //支付订单
    public static final String CANCELORDER="/orders/cancelOrder"; //取消订单
    public static final String EORDER="/orders/evaluateOrder"; //评价订单
    public static final String RORDER="/orders/receiveOrder"; //确认收货
    public static final String LISTORDER="/orders/list"; //查询订单
    public static final String BACKORDER="/orders/backOrder"; //申请退单
    public static final String RMD="/commodity/recommend"; //推荐列表

}
