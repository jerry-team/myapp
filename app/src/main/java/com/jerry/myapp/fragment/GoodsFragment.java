package com.jerry.myapp.fragment;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.jerry.myapp.R;
import com.jerry.myapp.adapter.GoodsAdapter;
import com.jerry.myapp.entity.GoodsEntity;
import com.jerry.myapp.loader.GlideImageLoader;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GoodsFragment extends BaseFragment {
    private int categoryId;
    private RecyclerView mRecyclerView;
    private GoodsAdapter mAdapter;
    private Banner banner;
    private List<Integer> images = new ArrayList<>();
    private List<GoodsEntity> goodsEntityList = new ArrayList<>();

    public static GoodsFragment newInstance(int categoryId) {
        GoodsFragment fragment = new GoodsFragment();
        fragment.categoryId = categoryId;
        return fragment;
    }

    @Override
    protected int initLayout() {
        if(categoryId == 0)
            return R.layout.fragment_goods_one;
        else if(categoryId == 1)
            return R.layout.fragment_goods_two;
        else
            return R.layout.fragment_goods_three;
    }

    @Override
    protected void initView() {
        if(categoryId == 0)
        {
            banner = mRootView.findViewById(R.id.banner);
            useBanner();
        }

        mRecyclerView = mRootView.findViewById(R.id.recyclerView);
    }

    @Override
    protected void initData() {
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        for(int i =0;i < 5;i++){
            goodsEntityList.add(new GoodsEntity(R.mipmap.dog1,"狮子狗","1020"));
            goodsEntityList.add(new GoodsEntity(R.mipmap.dog1,"哈巴狗","2000.20"));
        }
        mAdapter = new GoodsAdapter(goodsEntityList);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void useBanner(){
        images.add(R.mipmap.cat1);
        images.add(R.mipmap.cat3);
        images.add(R.mipmap.dog1);
        banner.setImages(images)
                .setImageLoader(new GlideImageLoader())
                .start();
    }

}
