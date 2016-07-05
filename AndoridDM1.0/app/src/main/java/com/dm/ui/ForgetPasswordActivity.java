package com.dm.ui;

import android.text.TextUtils;
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
import com.yolanda.nohttp.RequestMethod;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhangyue on 2016/6/20.
 */
public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.back)
    ImageView mImgBack;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.phone_mun)
    EditText mEditText;
    @BindView(R.id.code)
    EditText mCode;
    @BindView(R.id.get_code)
    TextView mGetCode;
    @BindView(R.id.next)
    RelativeLayout mNext;
    @BindView(R.id.password)
    EditText mEditPassWord;
    private String phoneNum,code;
    @Override
    public void initView() {
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        mImgBack.setOnClickListener(this);
        mNext.setOnClickListener(this);
        mGetCode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        phoneNum = mEditText.getText().toString();
        switch (v.getId()){
            case R.id.get_code:
                if (TextUtils.isEmpty(phoneNum) && phoneNum.length() < 11) {
                    Toast.makeText(ForgetPasswordActivity.this, "手机号有误", Toast.LENGTH_LONG).show();
                    return;
                }
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("mobile",phoneNum);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                HttpHelper.Http(ServerAddress.getVerifyCodeAddress(),1,getIdentification(),jsonObject,this, RequestMethod.POST,true);
                break;
            case R.id.back:
                finish();
                break;
            case R.id.next:
                if (TextUtils.isEmpty(phoneNum) && phoneNum.length() < 11) {
                    Toast.makeText(ForgetPasswordActivity.this, "手机号有误", Toast.LENGTH_LONG).show();
                    return;
                }
                code = mCode.getText().toString();
                if (TextUtils.isEmpty(code)){
                    Toast.makeText(ForgetPasswordActivity.this, "验证码有误", Toast.LENGTH_LONG).show();
                    return;
                }
                Map<String,Object> map = new HashMap<>();
                map.put("mobile",phoneNum);
                map.put("verifyCode",code);
                map.put("operationType","2");
                HttpHelper.Http(ServerAddress.commitVerifyCodeAddress(),2,getIdentification(),new JSONObject(map),this,RequestMethod.POST,false);
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
                Toast.makeText(ForgetPasswordActivity.this,"发送成功",Toast.LENGTH_LONG).show();
                break;
            case 2:
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(params.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                PasswordEncoder encoderMd5 = new PasswordEncoder(DmApplication.getInstance().getToken().substring(0, 64), "MD5");
                String encode = encoderMd5.encode(phoneNum);
                JSONObject jsonObject1 = jsonObject.optJSONObject("resultData");
                DmApplication.getInstance().setMobile(jsonObject1.optString("mobile"));
                DmApplication.getInstance().setToken(jsonObject1.optString("token"));
                Map<String,Object> map = new HashMap<>();
                map.put("mobile", phoneNum);
                map.put("loginPassword", encode);
                map.put("token", tm.getDeviceId());//设备码
                map.put("version", "1.0.0");
                map.put("source", "1");
                map.put("sign", SignUtil.signMD5(map, DmApplication.getInstance().getToken().replace(DmApplication.getInstance().getToken().substring(0, 64), "")));
                HttpHelper.Http(ServerAddress.forgetPasswoedAddress(),3,getIdentification(),new JSONObject(map),this,RequestMethod.POST,true);
                break;
            case 3:
                finish();
                break;
        }
    }
}
