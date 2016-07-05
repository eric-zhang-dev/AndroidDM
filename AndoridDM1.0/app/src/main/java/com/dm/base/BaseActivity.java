package com.dm.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;

import com.dm.application.AppManager;
import com.dm.task.TaskManager;
import com.dm.task.UIController;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.rest.RequestQueue;

/**
 * Created by zhangyue on 2016/6/16.
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseView,UIController {
    public RequestQueue requestQueue;
    public TelephonyManager tm;
    protected TaskManager taskManager = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        requestQueue = NoHttp.newRequestQueue();
        taskManager = TaskManager.getInstance();
        taskManager.registerUIController(this);
        tm = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
        initView();
        initData();
    }

    @Override
    public void refreshUI(int id, Object params) {

    }

    @Override
    public String getIdentification() {
        return getClass().toString()+this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        taskManager.unRegisterUIController(this);
    }
}
