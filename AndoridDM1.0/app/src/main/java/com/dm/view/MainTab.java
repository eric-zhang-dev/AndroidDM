package com.dm.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.dm.R;
import com.dm.fragment.ManageFragment;
import com.dm.fragment.PendingFragment;
import com.dm.fragment.ProcessingFragment;
import com.dm.fragment.SettingFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyue on 2016/6/20.
 */
public class MainTab implements View.OnClickListener {
    private List<Fragment> fragments;

    private FragmentActivity activity;

    private ViewPager viewPager;
    private FragmentPagerAdapter fragmentPagerAdapter;

    private SelectBtn btn0;
    private SelectBtn btn1;
    private SelectBtn btn2;
    private SelectBtn btn3;

    private int currentItem = 0;

    public void setActivity(FragmentActivity activity) {
        this.activity = activity;
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    public void setBottomView(View view) {
        btn0 = (SelectBtn) view.findViewById(R.id.btn0);
        btn1 = (SelectBtn) view.findViewById(R.id.btn1);
        btn2 = (SelectBtn) view.findViewById(R.id.btn2);
        btn3 = (SelectBtn) view.findViewById(R.id.btn3);
    }

    public void createView() {
        fragments = new ArrayList<>();
        fragments.add(new PendingFragment());
        fragments.add(new ProcessingFragment());
        fragments.add(new ManageFragment());
        fragments.add(new SettingFragment());
        fragmentPagerAdapter = new FragmentPagerAdapter(activity.getSupportFragmentManager()) {
            @Override
            public android.support.v4.app.Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        };
        viewPager.setAdapter(fragmentPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
    }

    public void setCurrentItem(int i) {
        viewPager.setCurrentItem(i);
    }

    private void setTab(int i) {
        if (i == currentItem)
            return;
        currentItem = i;
        resetSelectBtns();
        switch (i) {
            case 0:
                btn0.setSelect(true);
                break;
            case 1:
                btn1.setSelect(true);
                break;
            case 2:
                btn2.setSelect(true);
                break;
            case 3:
                btn3.setSelect(true);
                break;
        }
    }

    private void resetSelectBtns() {
        btn0.setSelect(false);
        btn1.setSelect(false);
        btn2.setSelect(false);
        btn3.setSelect(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn0:
                viewPager.setCurrentItem(0);
                break;
            case R.id.btn1:
                viewPager.setCurrentItem(1);
                break;
            case R.id.btn2:
                viewPager.setCurrentItem(2);
                break;
            case R.id.btn3:
                viewPager.setCurrentItem(3);
                break;
        }
    }
}
