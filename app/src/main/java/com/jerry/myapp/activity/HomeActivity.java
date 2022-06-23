package com.jerry.myapp.activity;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jerry.myapp.R;
import com.jerry.myapp.adapter.HomeAdapter;
import com.jerry.myapp.entity.TabEntity;
import com.jerry.myapp.fragment.HomeFragment;
import com.jerry.myapp.fragment.MyFragment;
import com.jerry.myapp.fragment.ShopCartFragment;
import com.jerry.myapp.fragment.StoreFragment;

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

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
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
        mFragments.add(HomeFragment.newInstance());
        mFragments.add(StoreFragment.newInstance());
        mFragments.add(ShopCartFragment.newInstance());
        mFragments.add(MyFragment.newInstance());
        for(int i = 0;i < mTitles.length;i++){
            mTabEntities.add(new TabEntity(mTitles[i],mIconSelectIds[i],mIconUnselectIds[i]));
        }
        commonTabLayout.setTabData(mTabEntities);
        viewPager.setAdapter(new HomeAdapter(getSupportFragmentManager(), mTitles, mFragments));

        //预加载fragment，防止异常
        viewPager.setOffscreenPageLimit(mFragments.size());

        commonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                commonTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
