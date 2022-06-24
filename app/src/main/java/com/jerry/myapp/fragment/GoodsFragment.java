package com.jerry.myapp.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jerry.myapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GoodsFragment extends BaseFragment {
    private int categoryId;

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

    }

    @Override
    protected void initData() {

    }

}
