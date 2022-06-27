package com.jerry.myapp.fragment;

import android.app.Application;

import com.huawei.hms.framework.common.Logger;

public class VideoKitApplication extends Application {
//    private static final String TAG = "VideoKitApplication";
//    private static WisePlayerFactory factory;
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        // setDeviceId方法需要传入手机的DeviceId
//        // setServeCountry方法需要传入国家/地区码
//        WisePlayerFactoryOptionsExt.Builder factoryOptions =
//                new WisePlayerFactoryOptionsExt.Builder().setDeviceId("xxx");
//        // 设置日志配置信息
//        LogConfigInfo logCfgInfo = new LogConfigInfo(1, "", 20, 1024);
//        factoryOptions.setLogConfigInfo(logCfgInfo);
//        // Application onCreate在多进程的场景下会被调用多次
//        // App需要在App进程（App包名）和播放器进程（App包名:player）的Application onCreate中调用WisePlayerFactory.initFactory()接口
//        WisePlayerFactory.initFactory(this, factoryOptions.build(), new InitFactoryCallback() {
//            @Override
//            public void onSuccess(WisePlayerFactory wisePlayerFactory) {
//                Logger.d(TAG, "onSuccess wisePlayerFactory:" + wisePlayerFactory);
//                factory = wisePlayerFactory;
//            }
//
//            @Override
//            public void onFailure(int errorCode, String msg) {
//                Logger.e(TAG, "onFailure errorcode:" + errorCode + " reason:" + msg);
//            }
//        });
//    }
//
//    public static WisePlayerFactory getWisePlayerFactory() {
//        return factory;
//    }

}
