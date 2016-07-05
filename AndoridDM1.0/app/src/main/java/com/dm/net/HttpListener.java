package com.dm.net;

import com.yolanda.nohttp.rest.Response;

/**
 * Created by zhangyue on 2016/6/24.
 */
public interface HttpListener<T> {
    void onSucceed(int what, Response<T> response);

    void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis);
}
