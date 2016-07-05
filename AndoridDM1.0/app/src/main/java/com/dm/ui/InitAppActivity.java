package com.dm.ui;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.dm.R;
import com.dm.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhangyue on 2016/6/16.
 */
public class InitAppActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.img)
    ImageView img;

    @Override
    public void initView() {
        setContentView(R.layout.layout_app_init);
        ButterKnife.bind(this);
//        img.setOnClickListener(this);
    }

    @Override
    public void initData() {
        img.postDelayed(login, 3000);
    }

    private Runnable login = new Runnable() {
        @Override
        public void run() {
            startActivity(new Intent(InitAppActivity.this, LoginActivity.class));
            finish();
        }
    };

    @Override
    public void onClick(View v) {
        startActivity(new Intent(InitAppActivity.this, LoginActivity.class));
        finish();
    }
}
