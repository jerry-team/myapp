package com.jerry.myapp.fragment;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.icu.text.CaseMap;
import android.os.Build;
import android.os.Bundle;
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

import com.jerry.myapp.activity.AddressActivity;
import com.jerry.myapp.activity.HomeActivity;
import com.jerry.myapp.activity.OrderActivity;
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
    private ImageView address_icon;
    private Button btn_endorse;
    private ImageView icon_1;
    private ImageView icon_2;
    private ImageView icon_3;
    private ImageView icon_4;
    private ImageView icon_5;


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
        //地址管理id=icon_6
        address_icon =mRootView.findViewById(R.id.icon_6);
        btn_endorse = mRootView.findViewById(R.id.btn_endrose);
        //订单
        icon_1 = mRootView.findViewById(R.id.icon_1);
        icon_2 = mRootView.findViewById(R.id.icon_2);
    }


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
        address_icon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){ navigateTo(AddressActivity.class); }
        });
        btn_endorse.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View v){
                toApplyMember();
            }
        }));


        icon_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putInt("param",0);
                navigateToWithBundle(OrderActivity.class,args);
            }
        });
        icon_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putInt("param",1);
                navigateToWithBundle(OrderActivity.class,args);
            }
        });

    }

    private void toApplyMember(){
        HashMap<String,Object> params = new HashMap<String, Object>();
        //设置state为申请会员状态2
        params.put("state", 2);
        Api.config(ApiConfig.APPLYMEMBER,params).postRequest(getActivity(), new TtitCallback() {
            @Override
            public void onSuccess(String res) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson = new Gson();
                        UserResponse userResponse = gson.fromJson(res, UserResponse.class);
                            Integer status=userResponse.getData().getState();
                            if(status == 2){
                                state.setText("会员申请中");
                                btn_endorse.setText("会员申请中");
                            }else {
                            }
                }});
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }
    private void getNickname(){
        HashMap<String, Object> params = new HashMap<String, Object>();
        //更新版，数据都在requstBody里了，不需要解析token，这边随便传一个无用数据
        params.put("test","test");
        Api.config(ApiConfig.NICKNAME,params).postRequest(getActivity(),new TtitCallback() {
            @Override
            public void onSuccess(final String res) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("onSuccess", res);
                        Gson gson = new Gson();
                        UserResponse userResponse = gson.fromJson(res, UserResponse.class);
                        Integer status = userResponse.getData().getState();
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
                            }else if(status == 0){

                            }else if(status == 2){
                                state.setText("会员申请中");
                                btn_endorse.setText("会员申请中");
                            }
                            else{
                                username.setText("未登录/注册");
                            }
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
