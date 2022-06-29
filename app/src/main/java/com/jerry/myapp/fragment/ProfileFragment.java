package com.jerry.myapp.fragment;

import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.jerry.myapp.R;
import com.jerry.myapp.activity.HomeActivity;
import com.jerry.myapp.api.Api;
import com.jerry.myapp.api.ApiConfig;
import com.jerry.myapp.api.TtitCallback;
import com.jerry.myapp.entity.LoginResponse;
import com.jerry.myapp.entity.TokenResponse;
import com.jerry.myapp.util.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.raphets.roundimageview.RoundImageView;

import java.util.HashMap;

public class ProfileFragment extends BaseFragment {
// private SwipeRefreshLayout layout;
    private TextView username;
    private Button  state;
    private ImageView right_arrow;
    private RoundImageView usr_icon;
    private ImageView ad_background;
    private TextView ad_text1,ad_text2;
    private Button ad_state;


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
        ad_state = mRootView.findViewById(R.id.state);
    }

//    @Override
//    protected void initView() {
//        layout = mRootView.findViewById(R.id.swipeRefreshLayout);
//    }

    @Override
    protected void initData() {
        String token = getStringFromSp("token");

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
//    private void gettokentime(String token){//线将token转换为String类型然和将String转换成json然和去解析就好了
//        String strtoken=new String(Base64.decode(token.split("\\.")[1],0));
//        try {
//            JSONObject object = new JSONObject(strtoken);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//    }



    private void checktoken(String token) {
        HashMap<String,Object> params = new HashMap<String, Object>();
        params.put("token",token);
        Api.config(ApiConfig.CHECK_TOKEN, params ).postRequest(new TtitCallback() {
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
