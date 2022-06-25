package com.jerry.myapp.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jerry.myapp.R;
import com.jerry.myapp.adapter.GoodsAdapter;
import com.jerry.myapp.entity.GoodsEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GoodsFragment extends BaseFragment {
    private int categoryId;
    private RecyclerView mRecyclerView;
    private GoodsAdapter mAdapter;
    private List<GoodsEntity> goodsEntityList = new ArrayList<>();

    public static GoodsFragment newInstance(int categoryId) {
        GoodsFragment fragment = new GoodsFragment();
        fragment.categoryId = categoryId;
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_goods;
    }

    @Override
    protected void initView() {
        mRecyclerView = mRootView.findViewById(R.id.recyclerView);
    }

    @Override
    protected void initData() {
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        for(int i =0;i < 11;i++){
            goodsEntityList.add(new GoodsEntity(R.mipmap.image1,"狮子狗","1020"));
        }
        mAdapter = new GoodsAdapter(goodsEntityList);
        mRecyclerView.setAdapter(mAdapter);
    }

}
