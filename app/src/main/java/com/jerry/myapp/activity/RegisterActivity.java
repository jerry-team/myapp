package com.jerry.myapp.activity;

import android.widget.EditText;

import com.jerry.myapp.R;

public class RegisterActivity extends BaseActivity {
    private EditText rUsername;
    private EditText rPassword;
    private EditText rePassword;
    private EditText rEmail;
    private EditText rPhone;


    @Override
    protected int initLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        rUsername = findViewById(R.id.user_info);
        rPassword = findViewById(R.id.pwd_info);
        rePassword = findViewById(R.id.re_pwd_info);
        rEmail = findViewById(R.id.email_info);
        rPhone = findViewById(R.id.phone_info);
    }

    @Override
    protected void initData() {

    }
}
