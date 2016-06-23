package com.dm.ui;

import android.widget.ImageView;
import android.widget.TextView;

import com.dm.R;
import com.dm.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhangyue on 2016/6/20.
 */
public class AccountActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView mImgBack;
    @BindView(R.id.title)
    TextView mTitle;

    @Override
    public void initView() {
        setContentView(R.layout.activity_account);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        mTitle.setText(getString(R.string.title_account));
    }
}
