package com.jerry.myapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jerry.myapp.R;
import com.jerry.myapp.adapter.OrderAdapter;
import com.jerry.myapp.entity.OrderEntity;
import com.jerry.myapp.entity.OrderItemEntity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderFragment extends BaseFragment {

    private Integer type;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout smartRefreshLayout;
    private OrderAdapter mAdapter;
    private List<OrderEntity> orderEntityList = new ArrayList<>();
    public static OrderFragment newInstance(Integer type) {
        OrderFragment fragment = new OrderFragment();
        fragment.type = type;
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_order;
    }

    @Override
    protected void initView() {
        mRecyclerView = mRootView.findViewById(R.id.recyclerView);
        smartRefreshLayout = mRootView.findViewById(R.id.refreshLayout);
    }

    @Override
    protected void initData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        getOrderList();

        mAdapter = new OrderAdapter(getContext(),orderEntityList);
        mRecyclerView.setAdapter(mAdapter);

    }

    protected void getOrderList(){
        for(int i = 0;i < 6;i++){
            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setShopName("测试" + i);
            orderEntity.setTotalAmount(123124.2);
            orderEntity.setStatus(i);
            orderEntity.setId(i);
            if(i == 5)
                orderEntity.setStatus(-2);
            List<OrderItemEntity> orderItemEntityList = new ArrayList<>();
            for(int j = 0;j < 3;j++){
                OrderItemEntity orderItemEntity = new OrderItemEntity();
                orderItemEntity.setName("吃人的狗");
                orderItemEntity.setDescription("asdqowieuqowieuqwoiueqoiweuqwe");
                orderItemEntity.setPrice(2000.20);
                orderItemEntity.setNum(2-j);
                orderItemEntityList.add(orderItemEntity);
            }
            orderEntity.setOrderItemList(orderItemEntityList);
            orderEntityList.add(orderEntity);
        }

    }
}