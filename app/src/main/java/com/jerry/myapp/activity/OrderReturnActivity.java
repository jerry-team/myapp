package com.jerry.myapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jerry.myapp.R;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

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
                submitOrderReturn();
            }
        });
    }
    protected void submitOrderReturn(){

    }
}