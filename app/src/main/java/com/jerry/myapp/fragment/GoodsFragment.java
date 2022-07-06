package com.jerry.myapp.fragment;


import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.gson.Gson;
import com.jerry.myapp.R;
import com.jerry.myapp.activity.GoodsDetailActivity;
import com.jerry.myapp.activity.LoginActivity;
import com.jerry.myapp.activity.PeripheryActivity;
import com.jerry.myapp.adapter.CategoryAdapter;
import com.jerry.myapp.adapter.GoodsAdapter;
import com.jerry.myapp.api.Api;
import com.jerry.myapp.api.ApiConfig;
import com.jerry.myapp.api.TtitCallback;
import com.jerry.myapp.entity.CategoryEntity;
import com.jerry.myapp.entity.CategoryResponse;
import com.jerry.myapp.entity.GoodsEntity;
import com.jerry.myapp.entity.GoodsResponse;
import com.jerry.myapp.loader.GlideImageLoader;
import com.jerry.myapp.util.StringUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;

import java.io.Serializable;
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
    private SmartRefreshLayout refreshLayout;
    private CategoryResponse categoryResponse;
    private List<CategoryEntity> categoryEntityList = new ArrayList<>();
    private List<GoodsEntity> goodsEntityList = new ArrayList<>();
    private int pageNum = 1;

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
        if(categoryId == 0) {
            banner = mRootView.findViewById(R.id.banner);
            refreshLayout = mRootView.findViewById(R.id.refreshLayout);
            useBanner();
        }
        if(categoryId <= 1){
            mRecyclerViewCategory = mRootView.findViewById(R.id.recyclerView_category);
        }
        refreshLayout = mRootView.findViewById(R.id.refreshLayout);
        mRecyclerView = mRootView.findViewById(R.id.recyclerView);

    }

    @Override
    protected void initData() {
        StaggeredGridLayoutManager gridLayoutManager1 = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(gridLayoutManager1);
        if(categoryId <= 1) {
            StaggeredGridLayoutManager gridLayoutManager2 = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
            mRecyclerViewCategory.setLayoutManager(gridLayoutManager2);
            mAdapter_category = new CategoryAdapter(getActivity());
            getCategoryList();
            mAdapter_category.setDatas(categoryEntityList);
            mRecyclerViewCategory.setAdapter(mAdapter_category);
            mAdapter_category.setOnItemClickListener(new CategoryAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Serializable obj) {
                    navigateTo(PeripheryActivity.class);
                }
            });
        }

        mAdapter = new GoodsAdapter(getActivity());
//        getGoods();
        mRecyclerView.setAdapter(mAdapter);

//        mAdapter.setOnItemClickListener(new GoodsAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(Serializable obj) {
//                navigateTo(GoodsDetailActivity.class);
//            }
//        });


        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageNum = 1;
                getGoodsList(true);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                pageNum++;
                getGoodsList(false);
            }
        });
        getGoodsList(false);
    }



    private void getGoodsList(final boolean isRefresh){
        String token = getStringFromSp("token");
        if(!StringUtils.isEmpty(token)){
            HashMap<String, Object> params = new HashMap<>();
            params.put("token", token);
            params.put("start", pageNum);
            params.put("pageSize", ApiConfig.PAGE_SIZE);
            params.put("categoryId", categoryId);
            Api.config(ApiConfig.Goods_LIST_PAGE,params).postRequest(getActivity(),new TtitCallback() {
                @Override
                public void onSuccess(final String res) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(isRefresh){
                                refreshLayout.finishRefresh(true);//关闭刷新动画
                            }
                            else{
                                refreshLayout.finishLoadMore(true);
                            }
                            GoodsResponse response = new Gson().fromJson(res, GoodsResponse.class);
                            if(response != null && response.getCode() == 200){
                                List<GoodsEntity> list = response.getData();
                                if (list != null && list.size() > 0) {
                                    if (isRefresh) {
                                        goodsEntityList = list;
                                    } else {
                                        goodsEntityList.addAll(list);
                                    }
                                    mAdapter.setDatas(goodsEntityList);
                                    mAdapter.notifyDataSetChanged();//通知view数据已更新，刷新视图
                                }
                                else{
                                    if(isRefresh){
                                        showToast("暂时加载无数据");
                                    }
                                    else{
                                        showToast("没有更多数据");
                                    }
                                }
                            }
                        }
                    });
                }
                @Override
                public void onFailure(Exception e) {
                    if(isRefresh){
                        refreshLayout.finishRefresh(true);//关闭刷新动画
                    }
                    else{
                        refreshLayout.finishLoadMore(true);
                    }
                }
            });
        }
        else{
            navigateTo(LoginActivity.class);
        }

    }

    public void getCategoryList(){
        String token = getStringFromSp("token");
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("token", token);
        Api.config(ApiConfig.CATEGORY_LIST,params).postRequest(getActivity(),new TtitCallback() {
            @Override
            public void onSuccess(final String res) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("onSuccess", res);
                        Gson gson = new Gson();
                        CategoryResponse categoryResponse = gson.fromJson(res, CategoryResponse.class);
                        if (categoryResponse.getCode() == 200) {
                            for(CategoryEntity category:categoryResponse.getData()){
                                if(category.getType() == 1)
                                    categoryEntityList.add(category);
                            }
                            mAdapter_category.notifyDataSetChanged();
                        } else {
                            showToastSync(categoryResponse.getMsg());
                        }
                    }
                });

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

//    public void getGoods() {
//        String token = getStringFromSp("token");
//        if (!StringUtils.isEmpty(token)) {
//            HashMap<String, Object> params = new HashMap<>();
//            params.put("token", token);
//            Api.config("/commodity/list", params).postRequest(new TtitCallback() {
//                @Override
//                public void onSuccess(final String res) {
//                   getActivity().runOnUiThread(new Runnable() {
//                       @Override
//                       public void run() {
//                           GoodsResponse response = new Gson().fromJson(res, GoodsResponse.class);
//                           List<GoodsEntity> list = response.getData();
//                           goodsEntityList = list;
////                    System.out.println(response.getData());
////                    System.out.println(list);
////                    System.out.println(goodsEntityList);
//                           mAdapter.setDatas(goodsEntityList);
//                    mAdapter.notifyDataSetChanged();//通知view数据已更新，刷新视图
//                       }
//                   });
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
