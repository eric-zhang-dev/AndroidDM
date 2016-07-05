package com.dm.ui;

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
import com.dm.net.HttpHelper;
import com.dm.utils.PasswordEncoder;
import com.dm.utils.ServerAddress;
import com.dm.utils.SignUtil;
import com.dm.view.dialog.WaitDialog;
import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.HttpHeaders;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhangyue on 2016/6/20.
 */
public class RegistInfoActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.back)
    ImageView mImgBack;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.business_name)
    EditText mBusinessName;
    @BindView(R.id.business_info)
    EditText mBusinessInfo;
    @BindView(R.id.user_name)
    EditText mUserName;
    @BindView(R.id.mobile_number)
    EditText mMobileNumber;
    @BindView(R.id.password)
    EditText mPassword;
    @BindView(R.id.business_des)
    EditText mBusinessDes;
    @BindView(R.id.submit)
    RelativeLayout mSubmit;
    private WaitDialog mWaitDialog;
    private RequestQueue requestQueue;

    @Override
    public void initView() {
        setContentView(R.layout.layout_regist_info);
        ButterKnife.bind(this);
        requestQueue = NoHttp.newRequestQueue();
    }

    @Override
    public void initData() {
        mTitle.setText(getString(R.string.business_info));
        mImgBack.setOnClickListener(this);
        mSubmit.setOnClickListener(this);
        mMobileNumber.setText(DmApplication.getInstance().getMobile());
        mWaitDialog = new WaitDialog(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.submit:
                if (TextUtils.isEmpty(mBusinessName.getText().toString())) {
                    Toast.makeText(RegistInfoActivity.this, "请输入店铺名称", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(mBusinessInfo.getText().toString())) {
                    Toast.makeText(RegistInfoActivity.this, "请输入店铺地址", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(mUserName.getText().toString())) {
                    Toast.makeText(RegistInfoActivity.this, "请输入姓名", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(mPassword.getText().toString())) {
                    Toast.makeText(RegistInfoActivity.this, "请输入密码", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(mBusinessDes.getText().toString())) {
                    Toast.makeText(RegistInfoActivity.this, "请输入店铺描述", Toast.LENGTH_LONG).show();
                    return;
                }
                PasswordEncoder encoderMd5 = new PasswordEncoder(DmApplication.getInstance().getToken().substring(0, 64), "MD5");
                String encode = encoderMd5.encode(mPassword.getText().toString());
                Map<String, Object> map = new HashMap<>();
                map.put("mobile", mMobileNumber.getText().toString());
                map.put("loginPassword", encode);
                map.put("merchantName", mBusinessName.getText().toString());
                map.put("realName", mUserName.getText().toString());
                map.put("address", mBusinessInfo.getText().toString());
                map.put("merchantDesc", mBusinessDes.getText().toString());
                map.put("token", tm.getDeviceId());//设备码
                map.put("version", "1.0.0");
                map.put("source", "1");
                map.put("sign", SignUtil.signMD5(map, DmApplication.getInstance().getToken().replace(DmApplication.getInstance().getToken().substring(0, 64), "")));
                HttpHelper.Http(ServerAddress.registerAddress(), 1, getIdentification(), new JSONObject(map), this, RequestMethod.POST,true);
                break;
        }
    }

    @Override
    public void refreshUI(int id, Object params) {
        super.refreshUI(id, params);
        switch (id) {
            case 1:
                Toast.makeText(RegistInfoActivity.this,params.toString(),Toast.LENGTH_LONG).show();
                break;
        }
    }
}
