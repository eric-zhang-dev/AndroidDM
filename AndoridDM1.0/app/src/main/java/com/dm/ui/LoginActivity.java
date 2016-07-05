package com.dm.ui;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dm.R;
import com.dm.application.DmApplication;
import com.dm.base.BaseActivity;
import com.dm.net.HttpHelper;
import com.dm.utils.Constant;
import com.dm.utils.PasswordEncoder;
import com.dm.utils.ServerAddress;
import com.dm.utils.SignUtil;
import com.yolanda.nohttp.RequestMethod;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhangyue on 2016/6/16.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.phone)
    EditText mPhoneNumber;
    @BindView(R.id.password)
    EditText mPassWord;
    @BindView(R.id.login)
    RelativeLayout mLogin;
    @BindView(R.id.regist)
    RelativeLayout mRegist;
    @BindView(R.id.forget_password)
    TextView mForgetPassWord;
    private String phoneNumber,passWord;
    @Override
    public void initView() {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        mForgetPassWord.setOnClickListener(this);
        mLogin.setOnClickListener(this);
        mRegist.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                phoneNumber = mPhoneNumber.getText().toString().trim();
                if (TextUtils.isEmpty(phoneNumber)&&phoneNumber.length()<11){
                    Toast.makeText(LoginActivity.this,"手机号有误",Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(mPassWord.getText().toString().trim())){
                    Toast.makeText(LoginActivity.this,"密码有误",Toast.LENGTH_LONG).show();
                    return;
                }
                Map<String,Object> map = new HashMap<>();
                map.put("token",tm.getDeviceId());//设备码
                map.put("version","1.0.0");
                map.put("source","1");
                map.put("mobile",phoneNumber);
                map.put("operationType","3");
                map.put("sign", SignUtil.signMD5(map, Constant.SIGN));
                HttpHelper.Http(ServerAddress.getTokenAddress(),1,getIdentification(),new JSONObject(map),this,RequestMethod.POST,false);
//                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                break;
            case R.id.regist:
                startActivity(new Intent(LoginActivity.this,AuthenticateMobileActivity.class));
                break;
            case R.id.forget_password:
                startActivity(new Intent(LoginActivity.this,ForgetPasswordActivity.class));
                break;
        }
    }

    @Override
    public void refreshUI(int id, Object params) {
        super.refreshUI(id, params);
        if (!params.toString().contains("SUCCESS")){//暂时这么处理
            return;
        }
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(params.toString()).optJSONObject("resultData");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        switch (id){
            case 1:
                DmApplication.getInstance().setMobile(jsonObject.optString("mobile"));
                DmApplication.getInstance().setToken(jsonObject.optString("token"));
                PasswordEncoder encoderMd5 = new PasswordEncoder(DmApplication.getInstance().getToken().substring(0, 64), "MD5");
                passWord = encoderMd5.encode(mPassWord.getText().toString().trim());
                Map<String,Object> map = new HashMap<>();
                map.put("token",tm.getDeviceId());//设备码
                map.put("version","1.0.0");
                map.put("source","1");
                map.put("mobile",phoneNumber);
                map.put("password","8ddc7b7a0b4a778d52c571ec313e9998");
                map.put("sign", SignUtil.signMD5(map, DmApplication.getInstance().getToken().replace(DmApplication.getInstance().getToken().substring(0, 64), "")));
                HttpHelper.Http(ServerAddress.loginAddress(),2,getIdentification(),new JSONObject(map),this,RequestMethod.POST,true);
                break;
            case 2:
                DmApplication.getInstance().setAccountId(jsonObject.optString("accountId"));
                DmApplication.getInstance().setMerchantName(jsonObject.optString("merchantName"));
                DmApplication.getInstance().setMerchantId(jsonObject.optString("merchantId"));
                DmApplication.getInstance().setRealName(jsonObject.optString("realName"));
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                finish();
                break;
        }
    }
}

