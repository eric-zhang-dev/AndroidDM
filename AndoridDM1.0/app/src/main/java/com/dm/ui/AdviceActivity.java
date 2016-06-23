package com.dm.ui;

import android.widget.ImageView;
import android.widget.TextView;

import com.dm.R;
import com.dm.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhangyue on 2016/6/20.
 * 建议
 */
public class AdviceActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView mImgBack;
    @BindView(R.id.title)
    TextView mTitle;

    @Override
    public void initView() {
        setContentView(R.layout.activity_advice);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        mTitle.setText(getString(R.string.title_advice));
    }
}
