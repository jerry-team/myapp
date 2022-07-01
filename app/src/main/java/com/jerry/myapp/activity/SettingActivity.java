package com.jerry.myapp.activity;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hb.dialog.myDialog.MyAlertDialog;
import com.hb.dialog.myDialog.MyAlertInputDialog;
import com.jerry.myapp.R;
import com.jerry.myapp.api.Api;
import com.jerry.myapp.api.ApiConfig;
import com.jerry.myapp.api.TtitCallback;
import com.jerry.myapp.entity.LoginResponse;
import com.jerry.myapp.entity.UserResponse;
import com.jerry.myapp.util.ParseTokenUtils;

import java.util.HashMap;

public class SettingActivity extends BaseActivity {
    private TextView username;
    private TextView nickname;
    private TextView status_;
    private ImageButton btn_return;
    private Button exit;
    private TextView phone;
    private TextView email;

    @Override
    protected int initLayout() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        username = findViewById(R.id.setting_username);
        nickname = findViewById(R.id.setting_nickname);
        status_ = findViewById(R.id.setting_status);
        btn_return = findViewById(R.id.left_return);
        exit = findViewById(R.id.exit_button);
        phone = findViewById(R.id.setting_phone);
        email = findViewById((R.id.setting_email));
    }

    @Override
    protected void initData() {
        getUserInfo();
        Context _this = this;
        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyAlertDialog myAlertDialog = new MyAlertDialog(_this).builder()
                        .setTitle("您当前用户名为" +username.getText() )
                        .setMsg("无法修改！用户名为唯一")
                        .setPositiveButton("确认", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }).setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        });
                myAlertDialog.show();
            }
        });

        nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MyAlertInputDialog myAlertInputDialog = new MyAlertInputDialog(_this).builder()
                        .setTitle("请输入要修改的用户名")
                        .setEditText("");
                myAlertInputDialog.setPositiveButton("确认", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyAlertDialog myAlertDialog = new MyAlertDialog(_this).builder()
                                .setTitle("确定将昵称修改为"+myAlertInputDialog.getResult().trim()+"吗？")
                                .setMsg("确定修改")
                                .setPositiveButton("确认", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        editNickname(myAlertInputDialog.getResult().trim());
                                        myAlertInputDialog.dismiss();
                                    }
                                }).setNegativeButton("取消", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                });
                        myAlertDialog.show();
//                        editNickname(myAlertInputDialog.getResult());
//                        myAlertInputDialog.dismiss();
                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),"取消修改",Toast.LENGTH_SHORT).show();
                        myAlertInputDialog.dismiss();
                    }
                });
                myAlertInputDialog.show();
            }
        });

        status_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = getStringFromSp("token");
                ParseTokenUtils parseTokenUtils = new ParseTokenUtils();
                String state_judge = parseTokenUtils.parseToken(token,"role");
                    MyAlertDialog myAlertDialog = new MyAlertDialog(_this).builder()
                            .setTitle("您当前是" + state_judge)
                            .setMsg("身份信息")
                            .setPositiveButton("确认", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            }).setNegativeButton("取消", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                    myAlertDialog.show();
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MyAlertInputDialog myAlertInputDialog = new MyAlertInputDialog(_this).builder()
                        .setTitle("请输入要修改的邮箱")
                        .setEditText("");
                myAlertInputDialog.setPositiveButton("确认", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyAlertDialog myAlertDialog = new MyAlertDialog(_this).builder()
                                .setTitle("确定将邮箱修改为" + myAlertInputDialog.getResult().trim() + "吗？")
                                .setMsg("确定修改")
                                .setPositiveButton("确认", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        editEmail(myAlertInputDialog.getResult().trim());
                                        myAlertInputDialog.dismiss();
                                    }
                                }).setNegativeButton("取消", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                });
                        myAlertDialog.show();
//                        editNickname(myAlertInputDialog.getResult());
//                        myAlertInputDialog.dismiss();
                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "取消修改", Toast.LENGTH_SHORT).show();
                        myAlertInputDialog.dismiss();
                    }
                });
                myAlertInputDialog.show();
            }
        });


        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MyAlertInputDialog myAlertInputDialog = new MyAlertInputDialog(_this).builder()
                        .setTitle("请输入要修改的电话")
                        .setEditText("");
                myAlertInputDialog.setPositiveButton("确认", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyAlertDialog myAlertDialog = new MyAlertDialog(_this).builder()
                                .setTitle("确定将电话修改为" + myAlertInputDialog.getResult().trim() + "吗？")
                                .setMsg("确定修改")
                                .setPositiveButton("确认", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        editPhone(myAlertInputDialog.getResult().trim());
                                        myAlertInputDialog.dismiss();
                                    }
                                }).setNegativeButton("取消", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                });
                        myAlertDialog.show();
//                        editNickname(myAlertInputDialog.getResult());
//                        myAlertInputDialog.dismiss();
                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "取消修改", Toast.LENGTH_SHORT).show();
                        myAlertInputDialog.dismiss();
                    }
                });
                myAlertInputDialog.show();
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyAlertDialog myAlertDialog = new MyAlertDialog(_this).builder()
                        .setTitle("您确定要退出吗？" )
                        .setMsg("退出登录")
                        .setPositiveButton("确认", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                exit_login();
                            }
                        }).setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        });
                myAlertDialog.show();
            }
        });

    }

//        phone.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

    private void getUserInfo(){
        String token = getStringFromSp("token");
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("token", token);
        ParseTokenUtils parseTokenUtils = new ParseTokenUtils();
        String username_ = parseTokenUtils.parseToken(token,"sub");
        String status = parseTokenUtils.parseToken(token,"role");
        params.put("username",username_);
        Api.config(ApiConfig.NICKNAME,params).postRequest(new TtitCallback() {
            @Override
            public void onSuccess(final String res) {
                runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //                Log.e("onSuccess", res);
                Gson gson = new Gson();
                UserResponse userResponse = gson.fromJson(res, UserResponse.class);
                if (userResponse.getCode() == 200) {
                    username.setText(userResponse.getData().getUsername());
                    nickname.setText(userResponse.getData().getNickname());
                    status_.setText(status);
                    email.setText(userResponse.getData().getEmail());
                    phone.setText(userResponse.getData().getTelephone());
                } else {
                    showToastSync(userResponse.getMsg());
                }
                    }
                });
            }
            @Override
            public void onFailure(Exception e) {
            }
        });
    }

    private void editNickname(String edit_nickname) {
        String token = getStringFromSp("token");
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("token", token);
        ParseTokenUtils parseTokenUtils = new ParseTokenUtils();
        String username_ = parseTokenUtils.parseToken(token,"sub");
        params.put("username",username_);
        params.put("edit_nickname",edit_nickname);

        Api.config(ApiConfig.EDITNICKNAME,params).postRequest(new TtitCallback() {
            @Override
            public void onSuccess(final String res) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson = new Gson();
                        UserResponse userResponse = gson.fromJson(res, UserResponse.class);
                        if (userResponse.getCode() == 200) {
                            nickname.setText(userResponse.getData().getNickname());
                            Toast.makeText(getApplicationContext(),"修改成功",Toast.LENGTH_SHORT).show();
                        } else {
//                            Toast.makeText(getApplicationContext(),userResponse.getMsg(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            @Override
            public void onFailure(Exception e) {
            }
        });


    }
    private void editEmail(String edit_email) {
        String token = getStringFromSp("token");
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("token", token);
        ParseTokenUtils parseTokenUtils = new ParseTokenUtils();
        String username_ = parseTokenUtils.parseToken(token,"sub");
        params.put("username",username_);
        params.put("edit_email",edit_email);

        Api.config(ApiConfig.EDITEMAIL,params).postRequest(new TtitCallback() {
            @Override
            public void onSuccess(final String res) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson = new Gson();
                        UserResponse userResponse = gson.fromJson(res, UserResponse.class);
                        if (userResponse.getCode() == 200) {
                            email.setText(userResponse.getData().getEmail());
                            Toast.makeText(getApplicationContext(),"修改成功",Toast.LENGTH_SHORT).show();
                        } else {
//                            Toast.makeText(getApplicationContext(),userResponse.getMsg(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            @Override
            public void onFailure(Exception e) {
            }
        });

    }
    private void editPhone(String edit_phone) {
        String token = getStringFromSp("token");
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("token", token);
        ParseTokenUtils parseTokenUtils = new ParseTokenUtils();
        String username_ = parseTokenUtils.parseToken(token,"sub");
        params.put("username",username_);
        params.put("edit_phone",edit_phone);
        Api.config(ApiConfig.EDITPHONE,params).postRequest(new TtitCallback() {
            @Override
            public void onSuccess(final String res) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson = new Gson();
                        UserResponse userResponse = gson.fromJson(res, UserResponse.class);
                        if (userResponse.getCode() == 200) {
                            phone.setText(userResponse.getData().getTelephone());
                            Toast.makeText(getApplicationContext(),"修改成功",Toast.LENGTH_SHORT).show();
                        } else {
//                            Toast.makeText(getApplicationContext(),userResponse.getMsg(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            @Override
            public void onFailure(Exception e) {
            }
        });
    }

    public void exit_login(){
        removeByKey("token");
        navigateTo(LoginActivity.class);
    }
}
