package com.jerry.myapp.fragment;


import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.gson.Gson;
import com.jerry.myapp.R;
import com.jerry.myapp.adapter.CategoryAdapter;
import com.jerry.myapp.adapter.GoodsAdapter;
import com.jerry.myapp.api.Api;
import com.jerry.myapp.api.ApiConfig;
import com.jerry.myapp.api.TtitCallback;
import com.jerry.myapp.entity.CategoryEntity;
import com.jerry.myapp.entity.CategoryResponse;
import com.jerry.myapp.entity.GoodsEntity;
import com.jerry.myapp.loader.GlideImageLoader;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GoodsFragment extends BaseFragment {
    private int categoryId;
    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerViewCategory;
    private GoodsAdapter mAdapter;
    private CategoryAdapter mAdapter_category;
    private Banner banner;
    private List<Integer> images = new ArrayList<>();
    private CategoryResponse categoryResponse;
    private List<CategoryEntity> categoryEntityList = new ArrayList<>();
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
        mRecyclerViewCategory = mRootView.findViewById(R.id.recyclerView_category);
        mRecyclerView = mRootView.findViewById(R.id.recyclerView);
    }

    @Override
    protected void initData() {
        StaggeredGridLayoutManager gridLayoutManager1 = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        StaggeredGridLayoutManager gridLayoutManager2 = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(gridLayoutManager1);
        mRecyclerViewCategory.setLayoutManager(gridLayoutManager2);
        for(int i =0;i < 5;i++){
            goodsEntityList.add(new GoodsEntity(R.mipmap.dog1,"狮子狗","1020"));
            goodsEntityList.add(new GoodsEntity(R.mipmap.dog1,"哈巴狗","2000.20"));
        }
        mAdapter = new GoodsAdapter(getActivity());
        mAdapter.setDatas(goodsEntityList);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter_category = new CategoryAdapter(getActivity());
        getCategoryList();
        mAdapter_category.setDatas(categoryEntityList);
        mRecyclerViewCategory.setAdapter(mAdapter_category);
    }

    public void getGoodsList(final boolean isRefresh){

    }

    public void getCategoryList(){
        String token = getStringFromSp("token");
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("token", token);
        Api.config(ApiConfig.CATEGORY_LIST,params).postRequest(new TtitCallback() {
            @Override
            public void onSuccess(final String res) {
                Log.e("onSuccess", res);
                Gson gson = new Gson();
                CategoryResponse categoryResponse = gson.fromJson(res, CategoryResponse.class);
                if (categoryResponse.getCode() == 200) {
                    for(CategoryEntity category:categoryResponse.getData()){
                        if(category.getType() == 1)
                            categoryEntityList.add(category);
                    }
                } else {
                    showToastSync(categoryResponse.getMsg());
                }
            }
            @Override
            public void onFailure(Exception e) {

            }
        });
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
