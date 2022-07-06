//package com.jerry.myapp.fragment;
//
//import androidx.recyclerview.widget.RecyclerView;
//import androidx.recyclerview.widget.StaggeredGridLayoutManager;
//
//import com.google.gson.Gson;
//import com.jerry.myapp.R;
//import com.jerry.myapp.activity.LoginActivity;
//import com.jerry.myapp.adapter.SearchGoodsAdapter;
//import com.jerry.myapp.api.Api;
//import com.jerry.myapp.api.ApiConfig;
//import com.jerry.myapp.api.TtitCallback;
//import com.jerry.myapp.entity.GoodsEntity;
//import com.jerry.myapp.entity.GoodsResponse;
//import com.jerry.myapp.util.StringUtils;
//import com.scwang.smartrefresh.layout.SmartRefreshLayout;
//import com.scwang.smartrefresh.layout.api.RefreshLayout;
//import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
//import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//public class SearchFragment extends BaseFragment{
//    private String search_val;
//    private RecyclerView mRecyclerView;
//    private SearchGoodsAdapter searchGoodsAdapter;
//    private SmartRefreshLayout smartRefreshLayout;
//    private List<GoodsEntity> goodsEntityList = new ArrayList<>();
//    private Integer pageNum = 1;
//
//    public static SearchFragment newInstance(String search_val) {
//        SearchFragment fragment = new SearchFragment();
//        fragment.search_val = search_val;
//        return fragment;
//    }
//
//    @Override
//    protected int initLayout() {
//        return R.layout.fragment_search;
//    }
//
//    @Override
//    protected void initView() {
//        smartRefreshLayout = mRootView.findViewById(R.id.refreshLayout);
//        mRecyclerView = mRootView.findViewById(R.id.recyclerView);
//    }
//
//    @Override
//    protected void initData() {
//        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
//        mRecyclerView.setLayoutManager(gridLayoutManager);
//        mRecyclerView.setAdapter(searchGoodsAdapter);
//        getSearchGoodsList(false);
//
//        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(RefreshLayout refreshlayout) {
//                pageNum = 1;
//                getSearchGoodsList(true);
//            }
//        });
//        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore(RefreshLayout refreshlayout) {
//                pageNum++;
//                getSearchGoodsList(false);
//            }
//        });
//    }
//
//    public void getSearchGoodsList(final boolean isRefresh){
//        String token = getStringFromSp("token");
//        if(!StringUtils.isEmpty(token)){
//            HashMap<String, Object> params = new HashMap<>();
//            params.put("token", token);
//            params.put("start", pageNum);
//            params.put("pageSize", ApiConfig.PAGE_SIZE);
//            params.put("val",search_val);
//            Api.config(ApiConfig.SEARCHGOODS,params).postRequest(getActivity(),new TtitCallback() {
//                @Override
//                public void onSuccess(final String res) {
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            if(isRefresh){
//                                smartRefreshLayout.finishRefresh(true);//关闭刷新动画
//                            }
//                            else{
//                                smartRefreshLayout.finishLoadMore(true);
//                            }
//                            GoodsResponse response = new Gson().fromJson(res, GoodsResponse.class);
//                            if(response != null && response.getCode() == 200){
//                                List<GoodsEntity> list = response.getData();
//                                if (list != null && list.size() > 0) {
//                                    if (isRefresh) {
//                                        goodsEntityList = list;
//                                    } else {
//                                        goodsEntityList.addAll(list);
//                                    }
//                                    searchGoodsAdapter.setDatas(goodsEntityList);
//                                    searchGoodsAdapter.notifyDataSetChanged();//通知view数据已更新，刷新视图
//                                }
//                                else{
//                                    if(isRefresh){
//                                        showToast("暂时加载无数据");
//                                    }
//                                    else{
//                                        showToast("没有更多数据");
//                                    }
//                                }
//                            }
//                        }
//                    });
//                }
//                @Override
//                public void onFailure(Exception e) {
//                    if(isRefresh){
//                        smartRefreshLayout.finishRefresh(true);//关闭刷新动画
//                    }
//                    else{
//                        smartRefreshLayout.finishLoadMore(true);
//                    }
//                }
//            });
//        }
//        else{
//            navigateTo(LoginActivity.class);
//        }
//
//    }
//}
