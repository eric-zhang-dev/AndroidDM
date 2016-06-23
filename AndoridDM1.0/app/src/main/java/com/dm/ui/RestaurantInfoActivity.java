package com.dm.ui;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.dm.R;
import com.dm.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Vinny on 2016/3/10.
 */
public class RestaurantInfoActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView mImgBack;
    @BindView(R.id.title)
    TextView mTitle;
    @Override
    public void initView() {
        setContentView(R.layout.activity_restaurant_info);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
    mTitle.setText(getString(R.string.title_restaurant_information));
    }
}
