package com.dm.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.StatFs;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dm.R;
import com.dm.base.BaseFragment;
import com.dm.ui.AccountActivity;
import com.dm.ui.AdviceActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 设置
 */
public class SettingFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.advice)
    LinearLayout mAdvice;
    @BindView(R.id.account)
    LinearLayout mAccount;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    protected void initData() {
        mAdvice.setOnClickListener(this);
        mAccount.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.advice:
                startActivity(new Intent(getActivity(), AdviceActivity.class));
                break;
            case R.id.account:
                startActivity(new Intent(getActivity(), AccountActivity.class));
                break;
        }
    }
}
