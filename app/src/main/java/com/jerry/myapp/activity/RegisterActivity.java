package com.jerry.myapp.activity;

import android.graphics.drawable.Drawable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.gson.Gson;
import com.jerry.myapp.R;
import com.jerry.myapp.api.Api;
import com.jerry.myapp.api.ApiConfig;
import com.jerry.myapp.api.TtitCallback;
import com.jerry.myapp.entity.Register;
import com.jerry.myapp.entity.RegisterResponse;
import com.jerry.myapp.util.StringUtils;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends BaseActivity {
    private EditText rUsername;
    private EditText rPassword;
    private EditText rePassword;
    private EditText rEmail;
    private EditText rPhone;
    private Button btn_register;
    private TextView return_login;
    private ImageButton clear_username;
    private ToggleButton show_hide;
    private Drawable on,off;

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
        btn_register = findViewById(R.id.btn_register);
        return_login = findViewById(R.id.return_login);
        clear_username = findViewById(R.id.clear_username);
        show_hide = findViewById(R.id.pwd_show_hide);
        on = this.getResources().getDrawable((R.mipmap.on_pwd));
        off = this.getResources().getDrawable((R.mipmap.off_pwd));
    }

    @Override
    protected void initData() {

        //注册按钮
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register rInfo =new Register();
                String rusername = rUsername.getText().toString().trim();
                String rpwd = rPassword.getText().toString().trim();
                String repwd = rePassword.getText().toString().trim();
                String remail = rEmail.getText().toString().trim();
                String rphone = rPhone.getText().toString().trim();
                rInfo.setRusername(rusername);
                rInfo.setRpwd(rpwd);
                rInfo.setRepwd(repwd);
                rInfo.setRemail(remail);
                rInfo.setRphone(rphone);
                register(rInfo);

            }
        });

        //返回注册界面的按钮
        return_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateTo(LoginActivity.class);
            }
        });


        //清空文本框的按钮
        clear_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rUsername.setText("");
            }
        });

        //密码显示与隐藏按钮
        show_hide.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(isChecked){
                    //如果选中，显示密码
                    rPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    show_hide.setBackgroundDrawable(on);

                }else{
                    //否则隐藏密码
                    rPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    show_hide.setBackgroundDrawable(off);
                }
            }
        });
    }

    private void register(Register register) {
        navigateTo(RegisterActivity.class);


        String regEmail = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        String regPwd = "^(?![0-9])(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
        Pattern email_p,pwd_p;
        Matcher email_m,pwd_m;
        email_p = Pattern.compile(regEmail);
        pwd_p = Pattern.compile(regPwd);

        pwd_m = pwd_p.matcher(register.getRpwd());
        email_m = email_p.matcher(register.getRemail());

        //账号验证
        if (StringUtils.isEmpty(register.getRusername())) {
            Toast.makeText(getApplicationContext(),"账号不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if (register.getRusername().length()>16 ) {
            Toast.makeText(getApplicationContext(),"账号必须小于16位",Toast.LENGTH_SHORT).show();
            return;
        }
        if (register.getRusername().length()<8 ) {
            Toast.makeText(getApplicationContext(),"账号必须大于8位",Toast.LENGTH_SHORT).show();
            return;
        }

        //密码验证
        if (StringUtils.isEmpty(register.getRpwd())) {
            Toast.makeText(getApplicationContext(),"密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if (!pwd_m.matches()) {
            Toast.makeText(getApplicationContext(),"密码格式不正确，至少要由6-16位数字字母组合成",Toast.LENGTH_SHORT).show();
            return;
        }
        if (!register.getRpwd().equals(register.getRepwd())) {
            Toast.makeText(getApplicationContext(),"两次密码输入不一致!",Toast.LENGTH_SHORT).show();
            return;
        }
//        if (register.getRpassword().length()>16 ) {
//            Toast.makeText(getApplicationContext(),"密码必须小于16位",Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (register.getRpassword().length()<8 ) {
//            Toast.makeText(getApplicationContext(),"密码必须大于8位",Toast.LENGTH_SHORT).show();
//            return;
//        }
        //邮箱验证
        if (StringUtils.isEmpty(register.getRemail()) ) {
            Toast.makeText(getApplicationContext(),"邮箱不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if (!email_m.matches()) {
            Toast.makeText(getApplicationContext(),"邮箱格式不正确",Toast.LENGTH_SHORT).show();
            return;
        }


        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("rusername", register.getRusername());
        params.put("rpassword", register.getRpwd());
        params.put("remail", register.getRemail());
        params.put("rphone", register.getRphone());
        Api.config(ApiConfig.REGISTER, params).postRequest(new TtitCallback() {
            @Override
            public void onSuccess(final String res) {
                Log.e("onSuccess", res);
                Gson gson = new Gson();
                RegisterResponse registerResponse = gson.fromJson(res, RegisterResponse.class);
                if (registerResponse.getCode() == 200) {
                    navigateTo(LoginActivity.class);//放在showToastSync之前
                    showToastSync("注册成功");
                } else {
                    showToastSync(registerResponse.getMsg());
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }
}
