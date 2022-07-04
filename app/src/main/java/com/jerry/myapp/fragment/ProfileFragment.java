package com.jerry.myapp.fragment;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.icu.text.CaseMap;
import android.os.Build;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.jerry.myapp.R;

import com.jerry.myapp.activity.HomeActivity;
import com.jerry.myapp.activity.SettingActivity;

import com.jerry.myapp.api.Api;
import com.jerry.myapp.api.ApiConfig;
import com.jerry.myapp.api.TtitCallback;
import com.jerry.myapp.entity.LoginResponse;
import com.jerry.myapp.entity.TokenResponse;


import com.jerry.myapp.entity.UserResponse;

import com.jerry.myapp.util.ParseTokenUtils;
import com.jerry.myapp.util.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.raphets.roundimageview.RoundImageView;

import java.util.HashMap;
import java.util.Timer;

public class ProfileFragment extends BaseFragment {
// private SwipeRefreshLayout layout;
    private TextView username;
    private Button  state;
    private ImageView right_arrow;
    private RoundImageView usr_icon;
    private ImageView ad_background;
    private TextView ad_text1,ad_text2;
    private Button ad_state;
    private RelativeLayout stete_card;


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
        username = mRootView.findViewById(R.id.user_name);
        state = mRootView.findViewById(R.id.state);
        right_arrow = mRootView.findViewById(R.id.right_arrow);
        usr_icon = mRootView.findViewById(R.id.person_icon);
        ad_background = mRootView.findViewById(R.id.ad_background);
        ad_text1  = mRootView.findViewById(R.id.advert_text1);
        ad_text2 = mRootView.findViewById(R.id.advert_text2);
        ad_state = mRootView.findViewById(R.id.btn_endrose);
        stete_card = mRootView.findViewById(R.id.state_card);
    }

//    @Override
//    protected void initView() {
//        layout = mRootView.findViewById(R.id.swipeRefreshLayout);
//    }

    @Override
    protected void initData() {

        String token = getStringFromSp("token");
        ParseTokenUtils parsetoken = new ParseTokenUtils();
//        parsetoken.parseToken(token,"sub");

        getNickname();
        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateTo(SettingActivity.class);
            }
        });
        right_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateTo(SettingActivity.class);
            }
        });

//        checktoken(token);
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

    private void getNickname(){
        String token = getStringFromSp("token");
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("token", token);
        ParseTokenUtils parseTokenUtils = new ParseTokenUtils();
        String username_ = parseTokenUtils.parseToken(token,"sub");
        Integer status = Integer.parseInt(parseTokenUtils.parseToken(token,"state"));
        params.put("username",username_);
        Api.config(ApiConfig.NICKNAME,params).postRequest(getActivity(),new TtitCallback() {
            @Override
            public void onSuccess(final String res) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("onSuccess", res);
                        Gson gson = new Gson();
                        UserResponse userResponse = gson.fromJson(res, UserResponse.class);
                        if (userResponse.getCode() == 200) {
                            username.setText(userResponse.getData().getNickname());
                            if(status == 1){
                                //改变样式
                                state.setText("高级会员");
                                state.setTextColor(getResources().getColor(R.color.vip_color));
                                Drawable drawable = getResources().getDrawable(R.mipmap.state_icon2);
                                drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
                                state.setCompoundDrawables(drawable,null,null,null);
                                state.setBackground(getResources().getDrawable(R.drawable.state_vip_background));
                                stete_card.setBackground(getResources().getDrawable(R.drawable.state_vip_card));
                                //#564126
                                ad_text1.setTextColor(getResources().getColor(R.color.vip_color));
                                ad_text1.setText("超级会员SVIP");
                                ad_text2.setTextColor(getResources().getColor(R.color.vip_color));
                                ad_text2.setText("挑选爱宠吧");
                                ad_state.setBackground(getResources().getDrawable(R.drawable.state_vip_endrose));
                                ad_state.setTextColor(getResources().getColor(R.color.vip_color));
                                ad_state.setTypeface(Typeface.DEFAULT_BOLD,Typeface.BOLD);
                                ad_state.setText("立即续费");

                            }
                        } else if(status == 0){

                        }else{
                            username.setText("未登录/注册");
                        }
                    }
                });
            }
            @Override
            public void onFailure(Exception e) {

            }
        });
    }



    private void checktoken(String token) {
        HashMap<String,Object> params = new HashMap<String, Object>();
        params.put("token",token);
        Api.config(ApiConfig.CHECK_TOKEN, params ).postRequest(getActivity(),new TtitCallback() {
            @Override
            public void onSuccess(final String res) {
                Log.e("onSuccess", res);
                Gson gson = new Gson();
                TokenResponse tokenResponse = gson.fromJson(res, TokenResponse.class);
                if (tokenResponse.getCode() == 200) {
                    showToastSync("token有效！");
                } else {
//                    showToastSync(tokenResponse.getMsg());
                }
            }
            @Override
            public void onFailure(Exception e) {

            }
        });
    }
}
