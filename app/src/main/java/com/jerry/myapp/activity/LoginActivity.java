package com.jerry.myapp.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.support.account.AccountAuthManager;
import com.huawei.hms.support.account.request.AccountAuthParams;
import com.huawei.hms.support.account.request.AccountAuthParamsHelper;
import com.huawei.hms.support.account.result.AuthAccount;
import com.huawei.hms.support.account.service.AccountAuthService;
import com.huawei.hms.support.api.entity.common.CommonConstant;
import com.huawei.hms.support.api.safetydetect.SafetyDetect;
import com.huawei.hms.support.api.safetydetect.SafetyDetectClient;
import com.huawei.hms.support.hwid.ui.HuaweiIdAuthButton;
import com.jerry.myapp.R;
import com.jerry.myapp.api.Api;
import com.jerry.myapp.api.ApiConfig;
import com.jerry.myapp.api.TtitCallback;
import com.jerry.myapp.entity.LoginResponse;
import com.jerry.myapp.util.StringUtils;

import java.util.HashMap;

public class LoginActivity extends BaseActivity {

    private EditText etAccount;
    private EditText etPwd;
    private Button btnLogin;
    private Button btnRegister;
    private HuaweiIdAuthButton huaweiIdAuthButton;

    // 华为帐号登录授权服务，提供静默登录接口silentSignIn，获取前台登录视图getSignInIntent，登出signOut等接口
    private AccountAuthService mAuthService;

    // 华为帐号登录授权参数
    private AccountAuthParams mAuthParam;

    // 用户自定义signInIntent请求码
    private static final int REQUEST_CODE_SIGN_IN = 1000;

    // 用户自定义日志标记
    private static final String TAG = "Account";

    @Override
    protected int initLayout() {

        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        etAccount = findViewById(R.id.et_account);
        etPwd = findViewById(R.id.et_pwd);
        btnLogin = findViewById(R.id.btn_login);
        huaweiIdAuthButton = findViewById(R.id.HuaweiIdAuthButton);
        btnRegister = findViewById(R.id.btn_register);

    }

    @Override
    protected void initData() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = etAccount.getText().toString().trim();
                String pwd = etPwd.getText().toString().trim();
                login(account, pwd);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateTo(RegisterActivity.class);
            }
        });

        huaweiIdAuthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onHuaweiAccountLogin();
//                silentSignInByHwId();
            }
        });
    }

    private void login(String account, String pwd) {
        if (StringUtils.isEmpty(account)) {
            showToast("请输入账号");
            return;
        }
        if (StringUtils.isEmpty(pwd)) {
            showToast("请输入密码");
            return;
        }
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("username", account);
        params.put("password", pwd);
        Api.config(ApiConfig.LOGIN, params).postRequest(new TtitCallback() {
            @Override
            public void onSuccess(final String res) {
                Log.e("onSuccess", res);
                Gson gson = new Gson();
                LoginResponse loginResponse = gson.fromJson(res, LoginResponse.class);
                if (loginResponse.getCode() == 200) {
                    String token = loginResponse.getData().getToken();
                    saveStringToSp("token",token);
                    navigateTo(HomeActivity.class);//放在showToastSync之前
                    showToastSync("登录成功");
                } else {
                    showToastSync(loginResponse.getMsg());
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    /**
     * HUAWEI Account Login in
     */
    private void onHuaweiAccountLogin() {
        AccountAuthParams authParams = new AccountAuthParamsHelper(AccountAuthParams.DEFAULT_AUTH_REQUEST_PARAM)
                .setIdToken().createParams();
        AccountAuthService mAuthService = AccountAuthManager.getService(this, authParams);
        startActivityForResult(mAuthService.getSignInIntent(), 200);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200) {
            Task<AuthAccount> authAccountTask = AccountAuthManager.parseAuthResultFromIntent(data);
            if (authAccountTask.isSuccessful()) {
                //login success
                AuthAccount authAccount = authAccountTask.getResult();
//                onHuaweiIdLoginSuccess(authAccount);
            } else {
                Log.e(TAG, "sign in failed : " + ((ApiException) authAccountTask.getException()).getStatusCode());
            }
        }
    }

//    private void onHuaweiIdLoginSuccess(AuthAccount authAccount) {
//        String openId = authAccount.getOpenId();
//        Log.i(TAG, "OpenId : " + openId);
//        SPUtil.put(this, SPConstants.KEY_OPENID, openId);
//        // save user info
//        LoginResponse.UserBean userBean = UserUtil.getLocalUser(this, openId);
//        if (userBean == null) {
//            userBean = new LoginResponse.UserBean();
//            userBean.setAvatar(authAccount.getAvatarUriString());
//            userBean.setDisplayName(authAccount.getDisplayName());
//            String localStr = new Gson().toJson(userBean);
//            SPUtil.put(this, openId, localStr);
//        }
//        startActivity(new Intent(this, HomeAct.class));
//        finish();
//    }

    /**
     * 静默登录，如果设备上的华为帐号系统已经登录，并且用户已经授权过，无需再拉起登录页面和授权页面，
     * 将直接静默登录成功，在成功监听器中，返回帐号信息;
     * 如果华为帐号系统帐号未登录或者用户没有授权，静默登录会失败，需要显式拉起前台登录授权视图。
     */
//    private void silentSignInByHwId() {
//        // 1、配置登录请求参数AccountAuthParams，包括请求用户id(openid、unionid)、email、profile（昵称、头像）等。
//        // 2、DEFAULT_AUTH_REQUEST_PARAM默认包含了id和profile（昵称、头像）的请求。
//        // 3、如需要请求获取用户邮箱，需要setEmail();
//        mAuthParam = new AccountAuthParamsHelper(AccountAuthParams.DEFAULT_AUTH_REQUEST_PARAM)
//                .setEmail()
//                .createParams();
//
//        // 使用请求参数构造华为帐号登录授权服务AccountAuthService
//        mAuthService = AccountAuthManager.getService(this, mAuthParam);
//
//        // 使用静默登录进行华为帐号登录
//        Task<AuthAccount> task = mAuthService.silentSignIn();
//        task.addOnSuccessListener(new OnSuccessListener<AuthAccount>() {
//            @Override
//            public void onSuccess(AuthAccount authAccount) {
//                // 静默登录成功，处理返回的帐号对象AuthAccount，获取帐号信息
//                dealWithResultOfSignIn(authAccount);
//            }
//        });
//        task.addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(Exception e) {
//                // 静默登录失败，使用getSignInIntent()方法进行前台显式登录
//                if (e instanceof ApiException) {
//                    ApiException apiException = (ApiException) e;
//                    Intent signInIntent = mAuthService.getSignInIntent();
//                    // 如果应用是全屏显示，即顶部无状态栏的应用，需要在Intent中添加如下参数：
//                    // intent.putExtra(CommonConstant.RequestParams.IS_FULL_SCREEN, true);
//                    // 具体详情可以参见应用调用登录接口的时候是全屏页面，为什么在拉起登录页面的过程中顶部的状态栏会闪一下？应该如何解决？
//                    signInIntent.putExtra(CommonConstant.RequestParams.IS_FULL_SCREEN, true);
//                    startActivityForResult(signInIntent, REQUEST_CODE_SIGN_IN);
//                }
//            }
//        });
//    }
}
