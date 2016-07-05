package com.dm.ui;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dm.R;
import com.dm.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by Vinny on 2016/3/10.
 */
public class DishCreateActivity extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.pic)
    ImageView imageView;
    @BindView(R.id.menu_name)
    EditText editName;
    @BindView(R.id.menu_price)
    EditText editPrice;
    @BindView(R.id.save)
    TextView textSave;
    @BindView(R.id.cancel)
    TextView textCancel;
    public void initView() {
        setContentView(R.layout.activity_dish_create);
        imageView.setOnClickListener(this);
        textCancel.setOnClickListener(this);
        textSave.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save:
                break;
            case R.id.cancel:
                break;
            case R.id.pic:
                break;
        }
    }
}
