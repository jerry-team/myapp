package com.jerry.myapp.activity;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.CommonTabLayout;
import com.jerry.myapp.R;

import java.util.ArrayList;

public class HomeActivity extends BaseActivity {

    private String[] mTitles = {"首页", "周边商店", "购物车", "个人中心"};
    private int[] mIconUnselectIds = {
            R.mipmap.icon1, R.mipmap.icon2,
            R.mipmap.icon3, R.mipmap.icon4};
    private int[] mIconSelectIds = {
            R.mipmap.icon1_1, R.mipmap.icon2_1,
            R.mipmap.icon3_1, R.mipmap.icon4_1};

    private ArrayList<Fragment> mFragments = new ArrayList<>();

    private ViewPager viewPager;
    private CommonTabLayout commonTabLayout;

    @Override
    protected int initLayout() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        viewPager = findViewById(R.id.viewpager);
        commonTabLayout = findViewById(R.id.commonTabLayout);
    }

    @Override
    protected void initData() {

    }
}
