package com.dm.ui;

import com.dm.R;
import com.dm.base.BaseActivity;
import com.dm.been.DishSalesBean;
import com.dm.view.BaseListView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DishSalesActivity extends BaseActivity {

    @BindView(R.id.listView)
     BaseListView listView;


    public void initView() {
        setContentView(R.layout.activity_dish_sales);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        listView.createView(DishSalesBean.class);
        listView.addBeanData(new DishSalesBean(DishSalesActivity.this, null));
        listView.addBeanData(new DishSalesBean(DishSalesActivity.this, null));
        listView.addBeanData(new DishSalesBean(DishSalesActivity.this, null));
        listView.addBeanData(new DishSalesBean(DishSalesActivity.this, null));
        listView.addBeanData(new DishSalesBean(DishSalesActivity.this, null));
        listView.addBeanData(new DishSalesBean(DishSalesActivity.this, null));
    }

}
