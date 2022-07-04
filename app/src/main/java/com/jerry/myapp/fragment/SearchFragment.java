package com.jerry.myapp.fragment;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.jerry.myapp.R;
import com.jerry.myapp.entity.CategoryResponse;

import java.util.ArrayList;

public class SearchFragment extends BaseFragment{
    private ViewPager viewPager;
    private SlidingTabLayout slidingTabLayout;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles;

    @Override
    protected int initLayout() {
        return R.layout.fragment_search;
    }

    @Override
    protected void initView() {
        viewPager = mRootView.findViewById(R.id.fixedViewPager_search);
        slidingTabLayout = mRootView.findViewById(R.id.slidingTabLayout_search);

    }

    @Override
    protected void initData() {
        getSearchView();
    }

    private void getSearchView(){

    }
}
