package com.jerry.myapp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {
    public Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(initLayout());
        initView();
        initData();
    }

    protected abstract int initLayout();

    protected abstract void initView();

    protected abstract void initData();

    public void showToast(String msg){
        Toast.makeText(mContext,msg, Toast.LENGTH_SHORT).show();
    }

    public void showToastSync(String msg){
        Looper.prepare();//消息队列机制，解决Toast不能在子线程运行的问题
        Toast.makeText(mContext,msg, Toast.LENGTH_SHORT).show();
        Looper.loop();
    }

    public void navigateTo(Class cls){
        Intent in = new Intent(mContext, cls);
        startActivity(in);
    }

    public void navigateToWithBundle(Class cls, Bundle bundle){//带参数跳转
        Intent in = new Intent(mContext, cls);
        in.putExtras(bundle);
        startActivity(in);
    }

    protected void saveStringToSp(String key, String val) {
        SharedPreferences sp = getSharedPreferences("sp_ttit", MODE_PRIVATE);//保存在sp_ttit.xml文件中
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, val);
        editor.commit();

    }

    protected void removeByKey(String key) {
        SharedPreferences sp = getSharedPreferences("sp_ttit", MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.remove(key);
        edit.commit();
    }
}
