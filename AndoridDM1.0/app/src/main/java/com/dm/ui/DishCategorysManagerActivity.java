package com.dm.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dm.R;
import com.dm.base.BaseActivity;
import com.dm.been.DishCategorysManagerBean;
import com.dm.view.BaseListView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *菜品管理
 */
public class DishCategorysManagerActivity extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.back)
    ImageView mImgBack;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.listView)
     BaseListView listView;
    @BindView(R.id.add)
     View add;

    @Override
    public void initView() {
        setContentView(R.layout.activity_dish_categorys_manager);
        ButterKnife.bind(this);
        add.setOnClickListener(this);
//        header.setOnClickFinishListener(new ActivityHeader.OnClickFinishListener() {
//            @Override
//            public void onClick(View v) {
//                listView.changeState(BaseListView.LIST_STATE_EDITING, !listView.checkState(BaseListView.LIST_STATE_EDITING));
//                if (listView.checkState(BaseListView.LIST_STATE_EDITING)) {
//                    ((TextView) v).setText("完成");
//                } else {
//                    ((TextView) v).setText("编辑");
//                }
//                listView.notifyDataSetChanged();
//            }
//        });
    }

    @Override
    public void initData() {
        listView.setDivider(getResources().getDrawable(R.color.border));
        listView.setDividerHeight(1);
        listView.createView(DishCategorysManagerBean.class);
        HashMap<String, Object> hashMap = new HashMap<>();
        listView.addBeanData(new DishCategorysManagerBean(DishCategorysManagerActivity.this, hashMap));
        listView.addBeanData(new DishCategorysManagerBean(DishCategorysManagerActivity.this, hashMap));
        listView.addBeanData(new DishCategorysManagerBean(DishCategorysManagerActivity.this, hashMap));
        listView.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
//                JumpTools.jump(DishCategorysManagerActivity.this, JUMP_DISH_CREATE);
                startActivity(new Intent(DishCategorysManagerActivity.this,DishCreateActivity.class));
                break;
        }
    }
}
