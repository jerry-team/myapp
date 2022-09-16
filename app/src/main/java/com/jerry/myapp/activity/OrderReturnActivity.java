package com.jerry.myapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jerry.myapp.R;
import com.jerry.myapp.api.Api;
import com.jerry.myapp.api.ApiConfig;
import com.jerry.myapp.api.TtitCallback;
import com.jerry.myapp.entity.OrderEntity;
import com.jerry.myapp.entity.Result;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import java.util.HashMap;

public class OrderReturnActivity extends BaseActivity {

    private Integer orderId;
    private CommonTitleBar titleBar;
    private EditText reason;
    private Button submit;

    @Override
    protected int initLayout() {
        return R.layout.activity_order_return;
    }

    @Override
    protected void initView() {
        titleBar = findViewById(R.id.titlebar);
        reason = findViewById(R.id.et_reason);
        submit = findViewById(R.id.bt_submit);
    }

    @Override
    protected void initData() {
        Intent in = getIntent();
        Bundle bd = in.getExtras();
        orderId = bd.getInt("orderId");
        Toast.makeText(mContext,""+orderId,Toast.LENGTH_SHORT).show();

        titleBar.setListener(new CommonTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == CommonTitleBar.ACTION_LEFT_TEXT) {
                    finish();
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backOrder();
            }
        });
    }

    //申请退单
    public void backOrder(){
        Gson gson = new Gson();
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("orderId",this.orderId);
        Api.config(ApiConfig.BACKORDER, params).postRequest(mContext,new TtitCallback() {
            @Override
            public void onSuccess(final String res) {
                ((Activity)mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Result rs = gson.fromJson(res, Result.class);
                        if(rs.getCode() == 200){
                            Toast.makeText(mContext,rs.getMsg(),Toast.LENGTH_SHORT).show();
                            finish();
                        }

                    }
                });
            }

            @Override
            public void onFailure(Exception e) {}
        });
        ;
    }
}