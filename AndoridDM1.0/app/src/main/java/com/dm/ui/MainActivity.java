package com.dm.ui;

import android.support.v4.view.ViewPager;

import com.dm.R;
import com.dm.base.BaseActivity;
import com.dm.view.MainTab;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhangyue on 2016/6/20.
 */
public class MainActivity extends BaseActivity {
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private MainTab mainTab;
    @Override
    public void initView() {
        setContentView(R.layout.main_tab);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        mainTab = new MainTab();
        mainTab.setActivity(MainActivity.this);
        mainTab.setViewPager(viewPager);
        mainTab.setBottomView(findViewById(R.id.bottom));
        mainTab.createView();
    }
}
