package com.dm.net;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;

import com.dm.base.BaseActivity;
import com.dm.view.dialog.WaitDialog;
import com.yolanda.nohttp.Logger;
import com.yolanda.nohttp.error.NetworkError;
import com.yolanda.nohttp.error.NotFoundCacheError;
import com.yolanda.nohttp.error.TimeoutError;
import com.yolanda.nohttp.error.URLError;
import com.yolanda.nohttp.error.UnKnownHostError;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import java.net.ProtocolException;

/**
 * Created by zhangyue on 2016/6/24.
 */
public class HttpResponseListener<T> implements OnResponseListener<T> {
    private Activity mActivity;
    private WaitDialog mWaitDialog;
    private Request<?> mRequest;
    private HttpListener<T> callback;
    public HttpResponseListener(Activity activity, Request<?> request, HttpListener<T> httpCallback, boolean canCancel, boolean isLoading) {
        this.mActivity = activity;
        this.mRequest = request;
        if (activity != null && isLoading) {
            mWaitDialog = new WaitDialog(activity);
            mWaitDialog.setCancelable(canCancel);
            mWaitDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    mRequest.cancel();
                }
            });
        }
        this.callback = httpCallback;
    }
    @Override
    public void onStart(int what) {
        if (mWaitDialog != null && !mActivity.isFinishing() && !mWaitDialog.isShowing())
            mWaitDialog.show();
    }

    @Override
    public void onSucceed(int what, Response<T> response) {
        int responseCode = response.getHeaders().getResponseCode();
        if (responseCode > 400 && mActivity != null) {
//            if (responseCode == 405) {// 405表示服务器不支持这种请求方法，比如GET、POST、TRACE中的TRACE就很少有服务器支持。
//                mActivity.showMessageDialog(R.string.request_succeed, R.string.request_method_not_allow);
//            } else {// 但是其它400+的响应码服务器一般会有流输出。
//                mActivity.showWebDialog(response);
//            }
        }
        if (callback != null) {
            callback.onSucceed(what, response);
        }
    }

    @Override
    public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
        if (exception instanceof NetworkError) {// 网络不好
//            Snackbar.show(mActivity, "请检查网络。");
        } else if (exception instanceof TimeoutError) {// 请求超时
//            Snackbar.show(mActivity, "请求超时，网络不好或者服务器不稳定。");
        } else if (exception instanceof UnKnownHostError) {// 找不到服务器
//            Snackbar.show(mActivity, "未发现指定服务器。");
        } else if (exception instanceof URLError) {// URL是错的
//            Snackbar.show(mActivity, "URL错误。");
        } else if (exception instanceof NotFoundCacheError) {
            // 这个异常只会在仅仅查找缓存时没有找到缓存时返回
//            Snackbar.show(mActivity, "没有发现缓存。");
        } else if (exception instanceof ProtocolException) {
//            Snackbar.show(mActivity, "系统不支持的请求方式。");
        } else {
//            Snackbar.show(mActivity, "未知错误。");
        }
        Logger.e("错误：" + exception.getMessage());
        if (callback != null)
            callback.onFailed(what, url, tag, exception, responseCode, networkMillis);
    }

    @Override
    public void onFinish(int what) {
        if (mWaitDialog != null && mWaitDialog.isShowing())
            mWaitDialog.dismiss();
    }
}
