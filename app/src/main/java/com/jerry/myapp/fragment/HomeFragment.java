package com.jerry.myapp.fragment;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.google.gson.Gson;
import com.jerry.myapp.R;
import com.jerry.myapp.activity.SearchHomeActivity;
import com.jerry.myapp.api.Api;
import com.jerry.myapp.api.ApiConfig;
import com.jerry.myapp.api.TtitCallback;
import com.jerry.myapp.entity.CategoryEntity;
import com.jerry.myapp.entity.CategoryResponse;
import com.jerry.myapp.adapter.HomeAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class HomeFragment extends BaseFragment {

    private OnFragmentInteractionListener mListener;
    private ViewPager viewPager;
    private SlidingTabLayout slidingTabLayout;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private EditText et_search;
    private CategoryResponse categoryResponse;
    private String[] mTitles;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }



    @Override
    protected int initLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        viewPager = mRootView.findViewById(R.id.fixedViewPager);
        slidingTabLayout = mRootView.findViewById(R.id.slidingTabLayout);
        et_search = mRootView.findViewById(R.id.et_search);
    }

    @Override
    protected void initData() {
        et_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateTo(SearchHomeActivity.class);
            }
        });
        getCategoryList();

    }

    private void getCategoryList(){
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
                            List<CategoryEntity> list = new ArrayList<>();
                            for(CategoryEntity category:categoryResponse.getData()){
                                if(category.getType() == 0){
                                    list.add(category);
                                }
                            }
                            if (list != null && list.size() > 0) {
                                mTitles = new String[list.size()+2];
                                mTitles[0] = "首页";
                                mFragments.add(GoodsFragment.newInstance(0));


                                mTitles[1] = "推荐";
                                mFragments.add(GoodsFragment.newInstance(1));

                                for (int i = 0; i < list.size(); i++) {
                                    mTitles[i+2] = list.get(i).getName();
                                    mFragments.add(GoodsFragment.newInstance(list.get(i).getId()+1));
                                }

                                viewPager.setOffscreenPageLimit(mFragments.size());
                                viewPager.setAdapter(new HomeAdapter(getFragmentManager(), mTitles, mFragments));
                                slidingTabLayout.setViewPager(viewPager);
                            }

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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
