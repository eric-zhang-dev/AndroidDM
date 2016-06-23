package com.dm.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dm.R;
import com.dm.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhangyue on 2016/6/20.
 */
public class PrinterServingsActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.tv1)
     TextView tv1;
    @BindView(R.id.tv2)
     TextView tv2;
    @BindView(R.id.tv3)
     TextView tv3;
    @BindView(R.id.tv4)
     TextView tv4;
    @BindView(R.id.tv5)
     TextView tv5;
    @BindView(R.id.et)
     EditText et;
    @BindView(R.id.save)
     View save;

    private int servings;

    public void initView() {
        setContentView(R.layout.activity_printer_servings);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
//        String s = SPTools.getS(K.PRINTER_SERVINGS);
//        if (TextUtils.isEmpty(s)) {
//            servings = 1;
//            SPTools.setS(K.PRINTER_SERVINGS, servings + "");
//        } else {
//            servings = Integer.parseInt(s);
//        }
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);
        tv5.setOnClickListener(this);
        save.setOnClickListener(this);
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    String temp = s.toString();
                    String tem = temp.substring(temp.length()-1, temp.length());
                    char[] temC = tem.toCharArray();
                    int mid = temC[0];
                    if(mid>=48&&mid<=57){//数字
                        servings = Integer.parseInt(temp);
                        resetViews();
                        return;
                    }
                    s.delete(temp.length()-1, temp.length());
                } catch (Exception e) {
                }
            }
        });
        resetViews();
    }

    private void resetViews() {
        tv1.setBackgroundResource(R.drawable.radius_bg_pink_);
        tv1.setTextColor(getResources().getColor(R.color.text_normal));
        tv2.setBackgroundResource(R.drawable.radius_bg_pink_);
        tv2.setTextColor(getResources().getColor(R.color.text_normal));
        tv3.setBackgroundResource(R.drawable.radius_bg_pink_);
        tv3.setTextColor(getResources().getColor(R.color.text_normal));
        tv4.setBackgroundResource(R.drawable.radius_bg_pink_);
        tv4.setTextColor(getResources().getColor(R.color.text_normal));
        tv5.setBackgroundResource(R.drawable.radius_bg_pink_);
        tv5.setTextColor(getResources().getColor(R.color.text_normal));

        switch (servings) {
            case 1:
                setTvSelect(tv1);
                break;
            case 2:
                setTvSelect(tv2);
                break;
            case 3:
                setTvSelect(tv3);
                break;
            case 4:
                setTvSelect(tv4);
                break;
            case 5:
                setTvSelect(tv5);
                break;
            default:
                if (TextUtils.isEmpty(et.getText()))
                    et.setText(servings + "");
                break;
        }
    }

    private void setTvSelect(TextView tv) {
        tv.setBackgroundResource(R.drawable.radius_bg_pink);
        tv.setTextColor(getResources().getColor(android.R.color.white));
        et.setText("");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv1:
                servings = 1;
                resetViews();
                break;
            case R.id.tv2:
                servings = 2;
                resetViews();
                break;
            case R.id.tv3:
                servings = 3;
                resetViews();
                break;
            case R.id.tv4:
                servings = 4;
                resetViews();
                break;
            case R.id.tv5:
                servings = 5;
                resetViews();
                break;
            case R.id.save:
//                SPTools.setS(K.PRINTER_SERVINGS, servings + "");
                finish();
                break;
        }
    }
}
