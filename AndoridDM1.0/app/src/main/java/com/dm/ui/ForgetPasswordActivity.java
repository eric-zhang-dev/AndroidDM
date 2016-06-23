package com.dm.ui;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dm.R;
import com.dm.base.BaseActivity;

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

    @Override
    public void initView() {
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        mImgBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
        }
    }
}
