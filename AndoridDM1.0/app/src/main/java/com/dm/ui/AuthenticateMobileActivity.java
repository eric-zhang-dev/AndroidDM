package com.dm.ui;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dm.R;
import com.dm.application.DmApplication;
import com.dm.base.BaseActivity;
import com.dm.utils.ServerAddress;
import com.dm.view.dialog.WaitDialog;
import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhangyue on 2016/6/20.
 *
 */
public class AuthenticateMobileActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.back)
    ImageView mImgBack;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.phone)
    EditText mPhoneNumber;
    @BindView(R.id.code)
    EditText mCode;
    @BindView(R.id.get_code)
    TextView mGetCode;
    @BindView(R.id.submit)
    RelativeLayout mSubmit;
    private String phoneNum, code;
    private WaitDialog mWaitDialog;
    @Override
    public void initView() {
        setContentView(R.layout.layout_authenticate_mobile);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        mTitle.setText(getString(R.string.title_regist_mobile));
        mGetCode.setOnClickListener(this);
        mSubmit.setOnClickListener(this);
        mImgBack.setOnClickListener(this);
        mWaitDialog = new WaitDialog(this);
    }

    @Override
    public void onClick(View v) {
        phoneNum = mPhoneNumber.getText().toString().trim();
        switch (v.getId()) {
            case R.id.get_code:
                if (TextUtils.isEmpty(phoneNum) && phoneNum.length() < 11) {
                    Toast.makeText(AuthenticateMobileActivity.this, "手机号有误", Toast.LENGTH_LONG).show();
                    return;
                }
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("mobile",phoneNum);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                requestToServer(ServerAddress.getVerifyCodeAddress(),jsonObject,1);
                break;
            case R.id.submit:
                if (TextUtils.isEmpty(phoneNum) && phoneNum.length() < 11) {
                    Toast.makeText(AuthenticateMobileActivity.this, "手机号有误", Toast.LENGTH_LONG).show();
                    return;
                }
                code = mCode.getText().toString();
                if (TextUtils.isEmpty(code)){
                    Toast.makeText(AuthenticateMobileActivity.this, "验证码有误", Toast.LENGTH_LONG).show();
                    return;
                }
                JSONObject json = new JSONObject();
                try {
                    json.put("mobile",phoneNum);
                    json.put("verifyCode",code);
                    json.put("operationType","1");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                requestToServer(ServerAddress.commitVerifyCodeAddress(),json,2);
                break;
            case R.id.back:
                finish();
                break;
        }
    }
    private void requestToServer(String url,JSONObject jsonObject,int num){
        // 创建请求对象。
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        // 添加请求参数。
//        request.add("mobile", phoneNum);
        /**
         * 上传文件；上传文件支持File、Bitmap、ByteArrayBinary、InputStream四种，这里推荐File、InputStream。
         * 其他两种小的可以，大的话容易内存溢出(OOM)。
         */
        // request.add("userHead", new FileBinary());
        // request.add("userHead", new BitmapBinary());
        // request.add("userHead", new ByteArrayBinary());
        // request.add("", new InputStreamBinary());

        // 请求头，是否要添加头，添加什么头，要看开发者服务器端的要求。
//        request.addHeader("type", "application/json");
        request.setContentType("application/json");
        // 设置一个tag, 在请求完(失败/成功)时原封不动返回; 多数情况下不需要。
        request.setTag(new Object());
        request.setDefineRequestBodyForJson(jsonObject);
		/*
         * what: 当多个请求同时使用同一个OnResponseListener时用来区分请求, 类似handler的what一样。
		 * request: 请求对象。
		 * onResponseListener 回调对象，接受请求结果。
		 */
        requestQueue.add(num, request, onResponseListener);
    }
    /**
     * 回调对象，接受请求结果.
     */
    private OnResponseListener<String> onResponseListener = new OnResponseListener<String>() {
        @SuppressWarnings("unused")
        @Override
        public void onSucceed(int what, Response<String> response) {
            switch (what){
                case 1:
                    int responseCode = response.getHeaders().getResponseCode();// 服务器响应码。

                    if (responseCode == 200) {// 如果是是用NoHttp的默认的请求或者自己没有对NoHttp做封装，这里最好判断下Http状态码。
                        String result = response.get();// 响应结果。
//                    ((TextView) findView(R.id.tv_result)).setText(result);

                        Object tag = response.getTag();// 拿到请求时设置的tag。
                        byte[] responseBody = response.getByteArray();// 如果需要byteArray自己解析的话。

                        // 响应头
                        Headers headers = response.getHeaders();
                        String headResult="";
//                            = getString(R.string.request_original_result);
                        headResult = String.format(Locale.getDefault(), headResult, headers.getResponseCode(), response.getNetworkMillis());
                        Log.e("Tag",result);
//                    ((TextView) findView(R.id.tv_head)).setText(headResult);
                        Toast.makeText(AuthenticateMobileActivity.this,"发送成功",Toast.LENGTH_LONG).show();
                    }
                    break;
                case 2:
                    int codedd = response.getHeaders().getResponseCode();// 服务器响应码。

                    if (codedd == 200) {// 如果是是用NoHttp的默认的请求或者自己没有对NoHttp做封装，这里最好判断下Http状态码。
                        String result = response.get();// 响应结果。
//                    ((TextView) findView(R.id.tv_result)).setText(result);

                        Object tag = response.getTag();// 拿到请求时设置的tag。
                        byte[] responseBody = response.getByteArray();// 如果需要byteArray自己解析的话。

                        // 响应头
                        Headers headers = response.getHeaders();
                        String headResult="";
//                            = getString(R.string.request_original_result);
                        headResult = String.format(Locale.getDefault(), headResult, headers.getResponseCode(), response.getNetworkMillis());
                        Log.e("Tag",result);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        JSONObject jsonObject1 = jsonObject.optJSONObject("resultData");
                        DmApplication.getInstance().setMobile(jsonObject1.optString("mobile"));
                        DmApplication.getInstance().setToken(jsonObject1.optString("token"));
                        startActivity(new Intent(AuthenticateMobileActivity.this,RegistInfoActivity.class));
                    }
                    break;
            }
        }

        @Override
        public void onStart(int what) {
            // 请求开始，这里可以显示一个dialog
            if (mWaitDialog != null && !mWaitDialog.isShowing())
                mWaitDialog.show();
        }

        @Override
        public void onFinish(int what) {
            // 请求结束，这里关闭dialog
            if (mWaitDialog != null && mWaitDialog.isShowing())
                mWaitDialog.dismiss();
        }

        @Override
        public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
            // 请求失败
//            ((TextView) findView(R.id.tv_result)).setText("请求失败: " + exception.getMessage());
        }
    };
}
