package com.jerry.myapp.activity;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.flyco.tablayout.SlidingTabLayout;
import com.jerry.myapp.R;
import com.jerry.myapp.adapter.HomeAdapter;
import com.jerry.myapp.adapter.ShopDetailAdapter;
import com.jerry.myapp.fragment.ShopGoodsFragment;
import com.jerry.myapp.view.FixedViewPager;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import java.util.ArrayList;

public class ShopDetailActivity extends BaseActivity {

    private int commodityId;

    private SlidingTabLayout slidingTabLayout;
    private CommonTitleBar titlebar;
    private String[] mTitles;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private FixedViewPager viewPager;

    @Override
    protected int initLayout() {
        return R.layout.activity_shop_detail;
    }

    @Override
    protected void initView() {
        titlebar = findViewById(R.id.titlebar);
        slidingTabLayout = findViewById(R.id.slidingTabLayout);
        viewPager = findViewById(R.id.fixedViewPager);
    }

    @Override
    protected void initData() {
        Intent in = getIntent();
        Bundle bd = in.getExtras();
        this.commodityId = bd.getInt("commodityId");
        titlebar.setListener(new CommonTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == CommonTitleBar.ACTION_LEFT_TEXT) {
                    finish();
//                    Toast.makeText(GoodsDetailActivity.this, "Titlebar double clicked!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        mTitles = new String[2];
        mTitles[0] = "宠物";
        mTitles[1] = "宠物周边";
        mFragments.add(ShopGoodsFragment.newInstance(0,1));
        mFragments.add(ShopGoodsFragment.newInstance(1,2));
        viewPager.setOffscreenPageLimit(mFragments.size());
        viewPager.setAdapter(new ShopDetailAdapter(getSupportFragmentManager(), mTitles, mFragments));
        slidingTabLayout.setViewPager(viewPager);
//        System.out.println("id:"+this.commodityId);
    }
}
