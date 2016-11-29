package com.wjc.jokerwanshoppingmall.app;

import android.app.Application;
import android.content.Context;

import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.ShareSDK;

public class MyAppliction extends Application {
    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        this.mContext = this;

        //初始化ShareSDK
        ShareSDK.initSDK(this);

        //极光推送
        JPushInterface.init(mContext);
        JPushInterface.setDebugMode(true);
    }
    // 获取全局上下文
    public static Context getContext() {
        return mContext;
    }

}
