package com.dm.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dm.task.TaskManager;
import com.dm.task.UIController;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.rest.RequestQueue;

/**
 * Created by zhangyue on 2016/6/20.
 */
public abstract class BaseFragment extends Fragment implements UIController {
    public RequestQueue requestQueue;
    public TelephonyManager tm;
    protected TaskManager taskManager = null;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        tm = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        taskManager = TaskManager.getInstance();
        taskManager.registerUIController(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        requestQueue = NoHttp.newRequestQueue();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    protected abstract void initData();

    @Override
    public void refreshUI(int id, Object params) {

    }

    @Override
    public String getIdentification() {
        return getClass().toString() + this;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        taskManager.unRegisterUIController(this);
    }
}
