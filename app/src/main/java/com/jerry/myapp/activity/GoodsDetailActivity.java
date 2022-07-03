package com.jerry.myapp.activity;

import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jerry.myapp.R;
import com.jerry.myapp.api.Api;
import com.jerry.myapp.api.ApiConfig;
import com.jerry.myapp.api.TtitCallback;
import com.jerry.myapp.entity.GoodsDetailResponse;
import com.jerry.myapp.entity.GoodsEntity;
import com.jerry.myapp.entity.GoodsResponse;
import com.jerry.myapp.entity.ShopCartResponse;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import java.util.HashMap;

public class GoodsDetailActivity extends BaseActivity {


    private int commodityId;
    private CommonTitleBar titleBar;
    private ImageView rlShop;
    private TextView tv_shopcart;

    private ImageView imageView;
    private TextView price;
    private TextView description;
    private TextView vaccin;
    private TextView isPedigree;
    private TextView isPest;
    private TextView breed;
    private TextView address;

    @Override
    protected int initLayout() {
        return R.layout.activity_goods_detail;
    }

    @Override
    protected void initView() {
        titleBar = findViewById(R.id.titlebar);
        rlShop = findViewById(R.id.rl_shop);
        tv_shopcart = findViewById(R.id.tv_shopcart);

        imageView = findViewById(R.id.imageView);
        price = findViewById(R.id.detail_price);
        description = findViewById(R.id.detail_description);
        vaccin = findViewById(R.id.vaccin);
        isPedigree = findViewById(R.id.isPedigree);
        isPest = findViewById(R.id.isPest);
        breed = findViewById(R.id.breed);
        address = findViewById(R.id.address);

    }

    @Override
    protected void initData() {
        Intent in = getIntent();
        Bundle bd = in.getExtras();
        this.commodityId = bd.getInt("commodityId");
//        Toast.makeText(GoodsDetailActivity.this, "commodityId:" +this.commodityId , Toast.LENGTH_SHORT).show();
        titleBar.setListener(new CommonTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == CommonTitleBar.ACTION_LEFT_TEXT) {
                    finish();
//                    Toast.makeText(GoodsDetailActivity.this, "Titlebar double clicked!", Toast.LENGTH_SHORT).show();
                }
                // CommonTitleBar.ACTION_LEFT_TEXT;        // 左边TextView被点击
                // CommonTitleBar.ACTION_LEFT_BUTTON;      // 左边ImageBtn被点击
                // CommonTitleBar.ACTION_RIGHT_TEXT;       // 右边TextView被点击
                // CommonTitleBar.ACTION_RIGHT_BUTTON;     // 右边ImageBtn被点击
                // CommonTitleBar.ACTION_SEARCH;           // 搜索框被点击,搜索框不可输入的状态下会被触发
                // CommonTitleBar.ACTION_SEARCH_SUBMIT;    // 搜索框输入状态下,键盘提交触发，此时，extra为输入内容
                // CommonTitleBar.ACTION_SEARCH_VOICE;     // 语音按钮被点击
                // CommonTitleBar.ACTION_SEARCH_DELETE;    // 搜索删除按钮被点击
                // CommonTitleBar.ACTION_CENTER_TEXT;      // 中间文字点击
            }
        });

        rlShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bd = new Bundle();
                bd.putInt("commodityId",2);
                navigateToWithBundle(ShopDetailActivity.class,bd);
            }
        });
        tv_shopcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertShopCart();
            }
        });
        getCommodity();

    }

    public void insertShopCart(){
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("commodityId", this.commodityId);
        Api.config(ApiConfig.INSERT_SHOP_CART,params).postRequest(this,new TtitCallback() {
            @Override
            public void onSuccess(final String res) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("insertShopCart", res);
                        Gson gson = new Gson();
                        ShopCartResponse rs = gson.fromJson(res, ShopCartResponse.class);
                        if(rs.getCode() == 200) {
                            Toast.makeText(GoodsDetailActivity.this, "已加入购物车！", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(GoodsDetailActivity.this, "购物车已有该商品！", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    public void getCommodity(){
        String token = getStringFromSp("token");
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("commodityId", this.commodityId);
        Api.config(ApiConfig.GETGOODS_BYID,params).postRequest(this,new TtitCallback() {
            @Override
            public void onSuccess(final String res) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("commodityone", res);
                        Gson gson = new Gson();
                        GoodsDetailResponse goodsResponse = gson.fromJson(res, GoodsDetailResponse.class);
                        if(goodsResponse.getCode() == 200) {
                            GoodsEntity goodsEntity = goodsResponse.getData();
                            Glide.with(mContext).load("http://10.0.2.2:8001/commodityImages/"+goodsEntity.getImgurl()).into(imageView);
                            price.setText("￥"+goodsEntity.getPrice());
                            description.setText(goodsEntity.getDescription());
                            vaccin.setText(goodsEntity.getVaccin()+"针");
                            if(goodsEntity.getIsPedigree() == 1)
                                isPedigree.setText("有血统证书");
                            if(goodsEntity.getIsPedigree() == 0)
                                isPedigree.setText("无血统证书");

                            if(goodsEntity.getIsPest() == 1)
                                isPedigree.setText("已驱虫");
                            if(goodsEntity.getIsPest() == 0)
                                isPedigree.setText("未驱虫");

                            breed.setText(goodsEntity.getBreed());
                        } else {
                            showToastSync(goodsResponse.getMsg());
                        }
                    }
                });

            }
            @Override
            public void onFailure(Exception e) {

            }
        });
    }

}
