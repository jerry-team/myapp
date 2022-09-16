package com.jerry.myapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.sip.SipAudioCall;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.jerry.myapp.R;
import com.jerry.myapp.adapter.HomeAdapter;
import com.jerry.myapp.fragment.GoodsFragment;
import com.jerry.myapp.fragment.OrderFragment;
import com.jerry.myapp.view.FixedViewPager;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends BaseActivity {

    private String[] mTitles = {"全部","待付款","待发货","待收货","待评价","待退款","已完成"};
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private Integer tid;
    private CommonTitleBar titleBar;
    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;

    @Override
    protected int initLayout() {
        return R.layout.activity_order;
    }

    @Override
    protected void initView() {
        titleBar = findViewById(R.id.titlebar);
        slidingTabLayout = findViewById(R.id.slidingTabLayout_order);
        viewPager = findViewById(R.id.fixedViewPager_order);
    }

    @Override
    protected void initData() {

        Intent in = getIntent();
        Bundle bd = in.getExtras();
        this.tid = bd.getInt("param");
        mFragments.add(OrderFragment.newInstance(10));
        for(int i = 0;i < 4;i++){
            mFragments.add(OrderFragment.newInstance(i));
        }
        mFragments.add(OrderFragment.newInstance(-2));
        mFragments.add(OrderFragment.newInstance(4));
        viewPager.setOffscreenPageLimit(mFragments.size());
        viewPager.setAdapter(new HomeAdapter(getSupportFragmentManager(), mTitles, mFragments));
        slidingTabLayout.setViewPager(viewPager);
        slidingTabLayout.setCurrentTab(this.tid);

        titleBar.setListener(new CommonTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == CommonTitleBar.ACTION_LEFT_TEXT) {
                    finish();
                }
            }
        });

    }

}