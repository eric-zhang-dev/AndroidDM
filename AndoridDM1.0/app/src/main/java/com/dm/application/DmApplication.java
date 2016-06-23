package com.dm.application;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;

import com.dm.utils.AppConfig;
import com.dm.utils.Constant;
import com.yolanda.nohttp.Logger;
import com.yolanda.nohttp.NoHttp;

/**
 * Created by zhangyue on 2016/6/16.
 */
public class DmApplication extends Application {
    private static DmApplication application;
    private String mobile;
    private String token;
    private String key;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

        NoHttp.initialize(this);

        Logger.setTag("DmApplication");
        Logger.setDebug(true);// 开始NoHttp的调试模式, 这样就能看到请求过程和日志

        AppConfig.getInstance();
    }

    public static DmApplication getInstance() {
        return application;
    }

    public String getMobile() {
        if (mobile == null || mobile == "") {
            SharedPreferences preferences = getSharedPreferences(
                    Constant.SETTING_XML, Activity.MODE_PRIVATE);
            mobile = preferences.getString("mobile", "");
        }
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
        SharedPreferences preferences = getSharedPreferences(Constant.SETTING_XML, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("mobile", mobile);
        editor.commit();
    }

    public String getToken() {
        if (token == null || token == "") {
            SharedPreferences preferences = getSharedPreferences(Constant.SETTING_XML, Activity.MODE_PRIVATE);
            token = preferences.getString("token", "");
        }
        return token;
    }

    public void setToken(String token) {
        this.token = token;
        SharedPreferences preferences = getSharedPreferences(Constant.SETTING_XML, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("token", token);
        editor.commit();
    }
    public String getKey() {
        if (key == null || key == "") {
            SharedPreferences preferences = getSharedPreferences(Constant.SETTING_XML, Activity.MODE_PRIVATE);
            key = preferences.getString("key", "");
        }
        return token;
    }

    public void setKey(String key) {
        this.key = key;
        SharedPreferences preferences = getSharedPreferences(Constant.SETTING_XML, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("key", key);
        editor.commit();
    }
}
