package com.jerry.myapp.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.jerry.myapp.activity.HomeActivity;
import com.jerry.myapp.api.Api;
import com.jerry.myapp.api.ApiConfig;
import com.jerry.myapp.api.TtitCallback;
import com.jerry.myapp.entity.CarResponse;
import com.jerry.myapp.R;
import com.jerry.myapp.adapter.CartShopAdapter;
import com.jerry.myapp.entity.GoodsEntity;
import com.jerry.myapp.entity.LoginResponse;
import com.jerry.myapp.entity.ShopCartCommodityResponse;
import com.jerry.myapp.util.Constant;
import com.jerry.myapp.util.GoodsCallback;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ShopCartFragment extends BaseFragment implements GoodsCallback{

    private HomeFragment.OnFragmentInteractionListener mListener;
    public static final String TAG = "ShopCartActivity";

    private Button cartButton;
    private RecyclerView rvStore;
    private CartShopAdapter storeAdapter;
//    private List<CarResponse.OrderDataBean> mList = new ArrayList<>();
    private TextView tvEdit;//编辑
    private ImageView ivCheckedAll;//全选
    private TextView tvTotal;//合计价格
    private TextView tvSettlement;//结算
    private LinearLayout layEdit;//编辑底部布局
    private TextView tvShareGoods;//分享商品
    private TextView tvCollectGoods;//收藏商品
    private TextView tvDeleteGoods;//删除商品

    private boolean isEdit = false;//是否编辑
    private boolean isAllChecked = false;//是否全选

    private List<Integer> shopIdList = new ArrayList<>();//店铺列表
    private List<ShopCartCommodityResponse.ShopBean> shopBeanList = new ArrayList<>();//商店列表

    private double totalPrice = 0.00;//商品总价
    private int totalCount = 0;//商品总数量

    private AlertDialog dialog;//弹窗

    private boolean isHaveGoods = false;//购物车是否有商品

    private SmartRefreshLayout refresh;//刷新布局
    private LinearLayout layEmpty;//空布局

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    storeAdapter.setData(shopBeanList);
                    storeAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    public static ShopCartFragment newInstance() {
        ShopCartFragment fragment = new ShopCartFragment();
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_shop_cart;
    }

    @Override
    protected void initView() {
        initItemView();
        refresh = mRootView.findViewById(R.id.refresh);
        initData();
    }

    protected void initItemView(){
        cartButton = mRootView.findViewById(R.id.cart_button);
        rvStore = mRootView.findViewById(R.id.rv_store);
        tvEdit = mRootView.findViewById(R.id.tv_edit);
        ivCheckedAll = mRootView.findViewById(R.id.iv_checked_all);
        tvTotal = mRootView.findViewById(R.id.tv_total);
        tvSettlement = mRootView.findViewById(R.id.tv_settlement);
        layEdit = mRootView.findViewById(R.id.lay_edit);
        tvShareGoods = mRootView.findViewById(R.id.tv_share_goods);
        tvCollectGoods = mRootView.findViewById(R.id.tv_collect_goods);
        tvDeleteGoods = mRootView.findViewById(R.id.tv_delete_goods);
        layEmpty = mRootView.findViewById(R.id.lay_empty);

    }

    @Override
    protected void initData() {
        //禁用下拉刷新和上拉加载更多
//        refresh.setEnableRefresh(false);
//        refresh.setEnableLoadMore(false);
        //下拉刷新监听
        refresh.setOnRefreshListener(refreshLayout -> initView());
        //监听器部分
        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        tvEdit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!isHaveGoods) {
                            showMsg("当前购物车空空如也~");
                            return;
                        }
                        if (isEdit) {
                            tvEdit.setText("编辑");
                            layEdit.setVisibility(View.GONE);
                            isEdit = false;
                        } else {
                            tvEdit.setText("完成");
                            layEdit.setVisibility(View.VISIBLE);
                            isEdit = true;
                        }
                    }
                });
        ivCheckedAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isHaveGoods) {
                    showMsg("当前购物车空空如也~");
                    return;
                }
                if (isAllChecked) {
                    //取消全选
                    isSelectAllStore(false);
                } else {
                    //全选
                    isSelectAllStore(true);
                }
            }
        });
        tvSettlement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isHaveGoods) {
                    showMsg("当前购物车空空如也~");
                    return;
                }
                if (totalCount == 0) {
                    showMsg("请选择要结算的商品");
                    return;
                }
                //弹窗
                dialog = new AlertDialog.Builder(getActivity())
                        .setMessage("总计:" + totalCount + "种商品，" + totalPrice + "元")
                        .setPositiveButton("确定", (dialog, which) -> deleteGoods())
                        .setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
                        .create();
                dialog.show();
            }
        });
        tvShareGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (totalCount == 0) {
                    showMsg("请选择要分享的商品");
                    return;
                }
                showMsg("分享成功!");
            }
        });
        tvCollectGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (totalCount == 0) {
                    showMsg("请选择要收藏的商品");
                    return;
                }
                showMsg("收藏成功!");
            }
        });
        tvDeleteGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (totalCount == 0) {
                    showMsg("请选择要删除的商品");
                    return;
                }
                //弹窗
                dialog = new AlertDialog.Builder(getActivity())
                        .setMessage("确定要删除所选商品吗?")
                        .setPositiveButton("确定", (dialog, which) -> deleteGoods())
                        .setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
                        .create();
                dialog.show();
            }
        });
        //Gson转换
        //        CarResponse carResponse = new Gson().fromJson(Constant.CAR_JSON, CarResponse.class);
        //Api.config(ApiConfig.REGISTER, params).postRequest(new TtitCallback() {
        //            @Override
        //            public void onSuccess(final String res) {
        //                Log.e("onSuccess", res);
        //                Gson gson = new Gson();
        //                RegisterResponse registerResponse = gson.fromJson(res, RegisterResponse.class);
        //                if (registerResponse.getCode() == 200) {
        //                    navigateTo(LoginActivity.class);//放在showToastSync之前
        //                    showToastSync("注册成功");
        //                } else {
        //                    showToastSync(registerResponse.getMsg());
        //                }
        //            }
        // CarResponse carResponse = new Gson();
        rvStore.setLayoutManager(new LinearLayoutManager(getActivity()));
        storeAdapter = new CartShopAdapter(R.layout.item_store, shopBeanList,this);
        listShop();
        rvStore.setAdapter(storeAdapter);

        //        mList.addAll(carResponse.getOrderData());
        //storeAdapter = new CartShopAdapter(R.layout.item_store, mList);

        //店铺点击
        storeAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {

            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ShopCartCommodityResponse.ShopBean storeBean = shopBeanList.get(position);

                if (view.getId() == R.id.iv_checked_store) {
                    storeBean.setChecked(!storeBean.isChecked());
//                    shopBeanList.get(position).setChecked(!shopBeanList.get(position).isChecked());
//                    storeAdapter.notifyDataSetChanged();
                    //传递选中店铺的id
                    if (storeBean.isChecked()) {
                        //全选商品
                        storeAdapter.controlGoods(storeBean.getId(), true);

                        //添加到列表中
                        if (!shopIdList.contains(storeBean.getId())) {
                            //如果列表中没有这个店铺Id且当前店铺为选中状态
                            shopIdList.add(storeBean.getId());
                        }
                    } else {
                        //清除全选商品
                        storeAdapter.controlGoods(storeBean.getId(), false);

                        //从列表中清除
                        if (shopIdList.contains(storeBean.getId())) {
                            shopIdList.remove((Integer) storeBean.getId());
                        }
                    }
                    //控制页面全选
                    controlAllChecked();
                }
//                mHandler.sendEmptyMessage(0);
            }
        });
        //有商品
        isHaveGoods = true;
        //下拉加载数据完成后，关闭下拉刷新动画
        refresh.finishRefresh();
        //隐藏布局
        layEmpty.setVisibility(View.GONE);
    }

    private void listShop(){
        HashMap<String, Object> params = new HashMap<String, Object>();
        Api.config(ApiConfig.LISTSHOPCART, params).postRequest(getActivity(),new TtitCallback() {
            @Override
            public void onSuccess(final String res) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("ShopCartSuccess", res);
                        Gson gson = new Gson();
                        ShopCartCommodityResponse rs = gson.fromJson(res, ShopCartCommodityResponse.class);
                        if (rs.getCode() == 200) {
                            List<ShopCartCommodityResponse.ShopBean> list = rs.getData();
                            shopBeanList.clear();
                            shopBeanList.addAll(list);
                            storeAdapter.notifyDataSetChanged();
//                            shopBeanList = rs.getData();

//                            storeAdapter.setData(shopBeanList);
//                            for(ShopCartCommodityResponse.ShopBean shopBean:shopBeanList)
//                            {
//                                System.out.println("test:"+shopBean.getName());
//                            }
//                            mHandler.sendEmptyMessage(0);

//                    storeAdapter = new CartShopAdapter(R.layout.item_store, mList,this);
                        } else {
                        }
                    }
                });

            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
    /**
     * 控制页面全选   与页面全选进行交互
     */
    private void controlAllChecked() {
        if (shopIdList.size() == shopBeanList.size() && shopBeanList.size() != 0) {
            //全选
            ivCheckedAll.setImageDrawable(getActivity().getDrawable(R.drawable.ic_checked));
            isAllChecked = true;
        } else {
            //不全选
            ivCheckedAll.setImageDrawable(getActivity().getDrawable(R.drawable.ic_check));
            isAllChecked = false;
        }
        //计算价格
        calculationPrice();
    }

    /**
     * 选中店铺
     *
     * @param shopId 店铺id
     */
    public void checkedStore(int shopId, boolean state) {
        for (ShopCartCommodityResponse.ShopBean bean : shopBeanList) {
            //遍历
            if (shopId == bean.getId()) {
                bean.setChecked(state);
                storeAdapter.notifyDataSetChanged();
                //记录选中店铺的shopid,添加到一个列表中。
                if (!shopIdList.contains(bean.getId()) && state) {
                    //如果列表中没有这个店铺Id且当前店铺为选中状态
                    shopIdList.add(bean.getId());
                } else {
                    if (shopIdList.contains(bean.getId())) {
                        //通过list.indexOf获取属性的在列表中的下标，不过强转Integer更简洁
                        shopIdList.remove((Integer) bean.getId());
                    }
                }
            }
        }
        //控制页面全选
        controlAllChecked();
    }


    /**
     * 商品价格
     */
    public void calculationPrice() {
        //每次计算之前先置零
        totalPrice = 0.00;
        totalCount = 0;
        //循环购物车中的店铺列表
        for (int i = 0; i < shopBeanList.size(); i++) {
            ShopCartCommodityResponse.ShopBean orderDataBean = shopBeanList.get(i);
            //循环店铺中的商品列表
            for (int j = 0; j < orderDataBean.getCommodityList().size(); j++) {
                GoodsEntity cartlistBean = orderDataBean.getCommodityList().get(j);
                //当有选中商品时计算数量和价格
                if (cartlistBean.isChecked()) {
                    totalCount++;
                    totalPrice += cartlistBean.getPrice() * cartlistBean.getNumber();
                }
            }
        }
        tvTotal.setText("￥" + totalPrice);
        tvSettlement.setText(totalCount == 0 ? "结算" : "结算(" + totalCount + ")");
    }

    /**
     * 删除商品
     */
    private void deleteGoods() {
        //店铺列表
        List<ShopCartCommodityResponse.ShopBean> storeList = new ArrayList<>();

        for (int i = 0; i < shopBeanList.size(); i++) {
            ShopCartCommodityResponse.ShopBean store = shopBeanList.get(i);
            if (store.isChecked()) {
                //店铺如果选择则添加到此列表中
                storeList.add(store);
            }
            //商品列表
            List<GoodsEntity> goodsList = new ArrayList<>();

            List<GoodsEntity> goods = store.getCommodityList();
            //循环店铺中的商品列表
            for (int j = 0; j < goods.size(); j++) {
                GoodsEntity cartlistBean = goods.get(j);
                //当有选中商品时添加到此列表中
                if (cartlistBean.isChecked()) {
                    goodsList.add(cartlistBean);
                    HashMap<String, Object> params = new HashMap<String, Object>();
                    params.put("commodity_id",cartlistBean.getId());
                    Api.config(ApiConfig.DELSHOPCART, params).postRequest(getActivity(),new TtitCallback() {
                        @Override
                        public void onSuccess(final String res) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.e("DelShopCart", res);
                                    Gson gson = new Gson();
                                    String rs = gson.fromJson(res, String.class);
//                                    System.out.println(rs);
                                }
                            });
                        }
                        @Override
                        public void onFailure(Exception e) {}
                    });
                }
            }
            //删除商品
            goods.removeAll(goodsList);


        }
        //删除店铺
        shopBeanList.removeAll(storeList);

        shopIdList.clear();//删除了选中商品，清空已选择的标识
        controlAllChecked();//控制去全选
        //改变界面UI
        tvEdit.setText("编辑");
        layEdit.setVisibility(View.GONE);
        isEdit = false;
        //刷新数据
        storeAdapter.notifyDataSetChanged();
        if (shopBeanList.size() <= 0) {
            //无商品
            isHaveGoods = false;
            //启用下拉刷新
            refresh.setEnableRefresh(true);
            //显示空布局
            layEmpty.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 是否全选
     *
     * @param state 状态
     */
    private void isSelectAllStore(boolean state) {
        //修改背景
        ivCheckedAll.setImageDrawable(getActivity().getDrawable(state ? R.drawable.ic_checked : R.drawable.ic_check));

        for (ShopCartCommodityResponse.ShopBean orderDataBean : shopBeanList) {
            //商品是否选中
            storeAdapter.controlGoods(orderDataBean.getId(), state);
            //店铺是否选中
            checkedStore(orderDataBean.getId(), state);
        }
        isAllChecked = state;
    }

    /**
     * Toast提示
     *
     * @param msg
     */
    private void showMsg(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}
