package com.dm.net;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.dm.base.BaseActivity;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by zhangyue on 2016/6/24.
 */
public class HttpHelper {
    private static HttpHelper mHttpHelper = null;
    private static Handler mHandler;
    private static String identification;
    private static Activity mContext;

    private HttpHelper(Handler mHandler) {
        super();
        this.mHandler = mHandler;
    }

    public synchronized static HttpHelper getInstance(Handler mHandler) {
        if (mHttpHelper == null)
            mHttpHelper = new HttpHelper(mHandler);
        return mHttpHelper;
    }

    public synchronized static HttpHelper getInstance() {
        return mHttpHelper;
    }

    public static void Http(String url, int id, String identification, JSONObject object, Activity context, RequestMethod method, boolean isLoading) {
        Request<String> request = null;
        HttpHelper.identification = identification;
        HttpHelper.mContext = context;
        request = NoHttp.createStringRequest(url, method);
        request.setContentType("application/json");
        request.setDefineRequestBodyForJson(object);
        Log.e("params：",object.toString());
        CallServer.getRequestInstance().add(context, id, request, httpListener, true, isLoading);
    }

    private static HttpListener<String> httpListener = new HttpListener<String>() {

        @Override
        public void onSucceed(int what, Response<String> response) {
            int responseCode = response.getHeaders().getResponseCode();// 服务器响应码
            if (responseCode == 200) {
                if (RequestMethod.HEAD == response.getRequestMethod()) {// 请求方法为HEAD时没有响应内容
//                    showMessageDialog(R.string.request_succeed, R.string.request_method_head);
                    Log.e("Tag", "mehtod_head");
                } else {
                    Log.e("Success----->", response.get());
                    Bundle bundle = new Bundle();
                    bundle.putInt("taskId", what);
                    bundle.putString("identification", identification);
                    bundle.putSerializable("result", (Serializable) response.get());
                    Message msg = new Message();
                    msg.setData(bundle);
                    mHandler.sendMessage(msg);
                }
            }
        }

        @Override
        public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
//            showMessageDialog(R.string.request_failed, exception.getMessage());
        }
    };
}
