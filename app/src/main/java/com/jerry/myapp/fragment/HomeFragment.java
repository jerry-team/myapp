package com.jerry.myapp.fragment;

import android.content.Context;
import android.net.Uri;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.jerry.myapp.R;
import com.jerry.myapp.loader.GlideImageLoader;
import com.jerry.myapp.adapter.HomeAdapter;
import com.youth.banner.Banner;

import java.util.ArrayList;
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
    private String[] mTitles = {"首页","推荐","猫","狗","猪","鸭","鼠"};

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        viewPager = mRootView.findViewById(R.id.fixedViewPager);
        slidingTabLayout = mRootView.findViewById(R.id.slidingTabLayout);
    }

    @Override
    protected void initData() {
        getCategoryList();
    }

    private void getCategoryList(){
        for(int i = 0;i < mTitles.length;i++)
        {
            mFragments.add(GoodsFragment.newInstance(i));
        }

        viewPager.setOffscreenPageLimit(mFragments.size());
        viewPager.setAdapter(new HomeAdapter(getFragmentManager(), mTitles, mFragments));
        slidingTabLayout.setViewPager(viewPager);
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
