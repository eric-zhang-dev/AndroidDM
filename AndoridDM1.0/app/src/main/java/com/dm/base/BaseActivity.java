package com.dm.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;

import com.dm.application.AppManager;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.rest.RequestQueue;

/**
 * Created by zhangyue on 2016/6/16.
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseView {
    public RequestQueue requestQueue;
    public TelephonyManager tm;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        requestQueue = NoHttp.newRequestQueue();
        tm = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
        initView();
        initData();
    }
}
