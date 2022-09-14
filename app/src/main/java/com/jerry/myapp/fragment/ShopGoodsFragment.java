package com.jerry.myapp.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.jerry.myapp.R;
import com.jerry.myapp.activity.GoodsDetailActivity;
import com.jerry.myapp.activity.LoginActivity;
import com.jerry.myapp.adapter.CategoryAdapter;
import com.jerry.myapp.adapter.GoodsAdapter;
import com.jerry.myapp.adapter.ShopGoodsAdapter;
import com.jerry.myapp.api.Api;
import com.jerry.myapp.api.TtitCallback;
import com.jerry.myapp.entity.CategoryEntity;
import com.jerry.myapp.entity.CategoryResponse;
import com.jerry.myapp.entity.GoodsEntity;
import com.jerry.myapp.entity.GoodsResponse;
import com.jerry.myapp.util.StringUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.youth.banner.Banner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopGoodsFragment extends BaseFragment {

    private int type;
    private int shopId;
    private RecyclerView mRecyclerView;
    private ShopGoodsAdapter mAdapter;
    private List<Integer> images = new ArrayList<>();
    private SmartRefreshLayout refreshLayout;
    private List<GoodsEntity> goodsEntityList = new ArrayList<>();
    private int pageNum = 1;


    public static ShopGoodsFragment newInstance(int type, int shopId) {
        ShopGoodsFragment fragment = new ShopGoodsFragment();
        fragment.type = type;
        fragment.shopId = shopId;
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_goods_three;
    }

    @Override
    protected void initView() {

        mRecyclerView = mRootView.findViewById(R.id.recyclerView);
    }

    @Override
    protected void initData() {
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(gridLayoutManager);
//        System.out.println(getActivity());
        mAdapter = new ShopGoodsAdapter(getActivity(),goodsEntityList);
        mRecyclerView.setAdapter(mAdapter);
        getGoods();
    }


    private void getGoods() {
        String token = getStringFromSp("token");
        if (!StringUtils.isEmpty(token)) {
            HashMap<String, Object> params = new HashMap<>();
            params.put("token", token);
            Api.config("/commodity/list", params).postRequest(getActivity(),new TtitCallback() {
                @Override
                public void onSuccess(final String res) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.e("onSuccess123123", res);
                            GoodsResponse response = new Gson().fromJson(res, GoodsResponse.class);
                            List<GoodsEntity> list = new ArrayList<>();
                            list = response.getData();
                            goodsEntityList.clear();
                            goodsEntityList.addAll(list);
                            mAdapter.notifyDataSetChanged();
                        }
                    });

               }

                @Override
                public void onFailure(Exception e) {
                }
            });
        } else {
            navigateTo(LoginActivity.class);
        }
    }

}
