package com.dm.net;

import android.app.Activity;
import android.content.Context;

import com.dm.base.BaseActivity;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.download.DownloadQueue;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;

/**
 * Created by zhangyue on 2016/6/24.
 */
public class CallServer {

    private static CallServer callServer;

    private RequestQueue requestQueue;

    private static DownloadQueue downloadQueue;

    private CallServer() {
        requestQueue = NoHttp.newRequestQueue(10);
    }

    public synchronized static CallServer getRequestInstance() {
        if (callServer == null)
            callServer = new CallServer();
        return callServer;
    }

    public static DownloadQueue getDownloadInstance() {
        if (downloadQueue == null)
            downloadQueue = NoHttp.newDownloadQueue(2);
        return downloadQueue;
    }

    public <T> void add(Activity context, int what, Request<T> request, HttpListener<T> callback, boolean canCancel, boolean isLoading) {
        requestQueue.add(what, request, new HttpResponseListener<T>(context, request, callback, canCancel, isLoading));
    }
    public void cancelBySign(Object sign) {
        requestQueue.cancelBySign(sign);
    }

    public void cancelAll() {
        requestQueue.cancelAll();
    }

    public void stopAll() {
        requestQueue.stop();
    }
}
