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
import com.dm.net.HttpHelper;
import com.dm.utils.ServerAddress;
import com.dm.view.dialog.WaitDialog;
import com.yolanda.nohttp.RequestMethod;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
        String s = DmApplication.getInstance().getToken();
        Log.e("sssss",s);
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
                HttpHelper.Http(ServerAddress.getVerifyCodeAddress(),1,getIdentification(),jsonObject,this,RequestMethod.POST,true);
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
                Map<String,Object> map = new HashMap<>();
                map.put("mobile",phoneNum);
                map.put("verifyCode",code);
                map.put("operationType","1");
                HttpHelper.Http(ServerAddress.commitVerifyCodeAddress(),2,getIdentification(),new JSONObject(map),this,RequestMethod.POST,true);
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    public void refreshUI(int id, Object params) {
        super.refreshUI(id, params);
        if (!params.toString().contains("SUCCESS")){//暂时这么处理
            return;
        }
        switch (id){
            case 1:
                Toast.makeText(AuthenticateMobileActivity.this,"发送成功",Toast.LENGTH_LONG).show();
                break;
            case 2:
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(params.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONObject jsonObject1 = jsonObject.optJSONObject("resultData");
                DmApplication.getInstance().setMobile(jsonObject1.optString("mobile"));
                DmApplication.getInstance().setToken(jsonObject1.optString("token"));
                startActivity(new Intent(AuthenticateMobileActivity.this,RegistInfoActivity.class));
                break;
        }
    }
}
