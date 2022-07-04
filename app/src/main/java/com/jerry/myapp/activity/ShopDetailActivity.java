package com.jerry.myapp.activity;



import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.flyco.tablayout.SlidingTabLayout;
import com.google.gson.Gson;
import com.jerry.myapp.R;
import com.jerry.myapp.adapter.HomeAdapter;
import com.jerry.myapp.adapter.ShopDetailAdapter;
import com.jerry.myapp.adapter.ShopGoodsAdapter;
import com.jerry.myapp.api.Api;
import com.jerry.myapp.api.TtitCallback;
import com.jerry.myapp.entity.GoodsEntity;
import com.jerry.myapp.entity.GoodsResponse;
import com.jerry.myapp.fragment.ShopGoodsFragment;
import com.jerry.myapp.util.StringUtils;
import com.jerry.myapp.view.FixedViewPager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShopDetailActivity extends BaseActivity {

    private int commodityId;

    private RecyclerView mRecyclerView;
    private ShopGoodsAdapter mAdapter;
    private List<Integer> images = new ArrayList<>();
    private SmartRefreshLayout refreshLayout;
    private List<GoodsEntity> goodsEntityList = new ArrayList<>();


    private SlidingTabLayout slidingTabLayout;
    private CommonTitleBar titlebar;
    private String[] mTitles;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private FixedViewPager viewPager;

    @Override
    protected int initLayout() {
        return R.layout.activity_shop_detail;
    }

    @Override
    protected void initView() {
        titlebar = findViewById(R.id.titlebar);
//        mRecyclerView = findViewById(R.id.recyclerView_d);
        slidingTabLayout = findViewById(R.id.slidingTabLayout);
        viewPager = findViewById(R.id.fixedViewPager);
    }

    @Override
    protected void initData() {
        Intent in = getIntent();
        Bundle bd = in.getExtras();
        this.commodityId = bd.getInt("commodityId");
        titlebar.setListener(new CommonTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == CommonTitleBar.ACTION_LEFT_TEXT) {
                    finish();
//                    Toast.makeText(GoodsDetailActivity.this, "Titlebar double clicked!", Toast.LENGTH_SHORT).show();
                }

            }
        });

//        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//        mRecyclerView.setLayoutManager(gridLayoutManager);
////        System.out.println(getActivity());
//        mAdapter = new ShopGoodsAdapter(this);
////        mAdapter.notifyDataSetChanged();//通知view数据已更新，刷新视图
//        getGoods();
//        mRecyclerView.setAdapter(mAdapter);

        mTitles = new String[2];
        mTitles[0] = "宠物";
        mTitles[1] = "宠物周边";
        mFragments.add(ShopGoodsFragment.newInstance(0,1));
        mFragments.add(ShopGoodsFragment.newInstance(1,2));
//        viewPager.setOffscreenPageLimit(mFragments.size());
        viewPager.setAdapter(new ShopDetailAdapter(getSupportFragmentManager(), mTitles, mFragments));
        slidingTabLayout.setViewPager(viewPager);
    }

//    public void getGoods() {
//        String token = getStringFromSp("token");
//        if (!StringUtils.isEmpty(token)) {
//            HashMap<String, Object> params = new HashMap<>();
//            params.put("token", token);
//            Api.config("/commodity/list", params).postRequest(new TtitCallback() {
//                @Override
//                public void onSuccess(final String res) {
//
//
//                            Log.e("onSuccess", res);
//                            GoodsResponse response = new Gson().fromJson(res, GoodsResponse.class);
//                            List<GoodsEntity> list = response.getData();
//                            goodsEntityList = list;
//                            mAdapter.setDatas(goodsEntityList);
//                            mAdapter.notifyDataSetChanged();//通知view数据已更新，刷新视图
//
//
//                }
//
//                @Override
//                public void onFailure(Exception e) {
//                }
//            });
//        } else {
//            navigateTo(LoginActivity.class);
//        }
//    }
}
