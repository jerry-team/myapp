package com.jerry.myapp;

import android.app.Application;

import com.huawei.hms.framework.common.Logger;
import com.huawei.hms.videokit.player.InitFactoryCallback;
import com.huawei.hms.videokit.player.LogConfigInfo;
import com.huawei.hms.videokit.player.WisePlayerFactory;
import com.huawei.hms.videokit.player.WisePlayerFactoryOptionsExt;

public class VideoKitApplication extends Application {

    private static final String TAG = "VideoKitApplication";
    private static WisePlayerFactory factory;

    @Override
    public void onCreate() {
        super.onCreate();
        // setDeviceId方法需要传入手机的DeviceId
        // setServeCountry方法需要传入国家/地区码
        WisePlayerFactoryOptionsExt.Builder factoryOptions =
                new WisePlayerFactoryOptionsExt.Builder().setDeviceId("xxx");
        // 设置日志配置信息
        LogConfigInfo logCfgInfo = new LogConfigInfo(1, "", 20, 1024);
        factoryOptions.setLogConfigInfo(logCfgInfo);
        // Application onCreate在多进程的场景下会被调用多次
        // App需要在App进程（App包名）和播放器进程（App包名:player）的Application onCreate中调用WisePlayerFactory.initFactory()接口
        WisePlayerFactory.initFactory(this, factoryOptions.build(), new InitFactoryCallback() {
            @Override
            public void onSuccess(WisePlayerFactory wisePlayerFactory) {
                Logger.d(TAG, "onSuccess wisePlayerFactory:" + wisePlayerFactory);
                factory = wisePlayerFactory;
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                Logger.e(TAG, "onFailure errorcode:" + errorCode + " reason:" + msg);
            }
        });
    }

//    private void initPlayer() {
//        // Pass the ID of a device to the setDeviceId method.
//        // Pass a country/region code to the setServeCountry method.
//        WisePlayerFactoryOptionsExt.Builder factoryOptions =
//                new WisePlayerFactoryOptionsExt.Builder().setDeviceId("xxx").setServeCountry("xx");
//        // Set the log configurations.
//        LogConfigInfo logCfgInfo = new LogConfigInfo(1, "", 20, 1024);
//        factoryOptions.setLogConfigInfo(logCfgInfo);
//        // Initialize the WisePlayer factory class.
//        WisePlayerFactory.initFactory(this, factoryOptions.build(), new InitFactoryCallback() {
//            @Override
//            public void onSuccess(WisePlayerFactory wisePlayerFactory) {
//                com.huawei.hms.common.util.Logger.d(TAG, "onSuccess wisePlayerFactory:" + wisePlayerFactory);
//                VideoKitApplication.setWisePlayerFactory(wisePlayerFactory);
//            }
//
//            @Override
//            public void onFailure(int errorCode, String msg) {
//                com.huawei.hms.common.util.Logger.e(TAG, "onFailure errorcode:" + errorCode + " reason:" + msg);
//            }
//        });
//    }

    public static WisePlayerFactory getWisePlayerFactory() {
        return factory;
    }

}

