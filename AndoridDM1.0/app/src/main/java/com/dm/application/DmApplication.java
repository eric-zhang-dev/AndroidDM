package com.dm.application;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.dm.net.HttpHelper;
import com.dm.task.TaskManager;
import com.dm.task.UIController;
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
    private String accountId;
    private String merchantId;
    private String realName;
    private String merchantName;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            if (bundle == null)
                return;
            int id = bundle.getInt("taskId");
            String identification = bundle.getString("identification");
            Object result = bundle.get("result");
            UIController controller = TaskManager.getInstance() .getUIController(identification);
            try {
                controller.refreshUI(id, result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        HttpHelper.getInstance(mHandler);
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
    public String getAccountId() {
        if (accountId == null || accountId == "") {
            SharedPreferences preferences = getSharedPreferences(Constant.SETTING_XML, Activity.MODE_PRIVATE);
            accountId = preferences.getString("accountId", "");
        }
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
        SharedPreferences preferences = getSharedPreferences(Constant.SETTING_XML, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("accountId", accountId);
        editor.commit();
    }
    public String getMerchantId() {
        if (merchantId == null || merchantId == "") {
            SharedPreferences preferences = getSharedPreferences(Constant.SETTING_XML, Activity.MODE_PRIVATE);
            merchantId = preferences.getString("merchantId", "");
        }
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
        SharedPreferences preferences = getSharedPreferences(Constant.SETTING_XML, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("merchantId", merchantId);
        editor.commit();
    }
    public String getRealName() {
        if (realName == null || realName == "") {
            SharedPreferences preferences = getSharedPreferences(Constant.SETTING_XML, Activity.MODE_PRIVATE);
            realName = preferences.getString("realName", "");
        }
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
        SharedPreferences preferences = getSharedPreferences(Constant.SETTING_XML, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("realName", realName);
        editor.commit();
    }
    public String getMerchantName() {
        if (merchantName == null || merchantName == "") {
            SharedPreferences preferences = getSharedPreferences(Constant.SETTING_XML, Activity.MODE_PRIVATE);
            merchantName = preferences.getString("merchantName", "");
        }
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
        SharedPreferences preferences = getSharedPreferences(Constant.SETTING_XML, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("merchantName", merchantName);
        editor.commit();
    }
}
