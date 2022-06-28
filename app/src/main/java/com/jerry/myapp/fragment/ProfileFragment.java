package com.jerry.myapp.fragment;

import android.widget.Toast;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.jerry.myapp.R;

public class ProfileFragment extends BaseFragment {
// private SwipeRefreshLayout layout;

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_profile;
    }

    @Override
    protected void initView() {

    }

//    @Override
//    protected void initView() {
//        layout = mRootView.findViewById(R.id.swipeRefreshLayout);
//    }

    @Override
    protected void initData() {
//        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                //判断是否在刷新
//                showToast(layout.isRefreshing() ? "正在刷新" : "刷新完成");
////                Toast.makeText(ProfileFragment.this, layout.isRefreshing() ? "正在刷新" : "刷新完成", Toast.LENGTH_SHORT).show();
//
//                layout.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        layout.setRefreshing(false);
//                    }
//                }, 1000);
//            }
//        });

    }
}
