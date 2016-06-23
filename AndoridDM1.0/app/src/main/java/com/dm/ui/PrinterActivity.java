package com.dm.ui;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.dm.R;
import com.dm.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Vinny on 2016/3/10.
 */
public class PrinterActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.servings)
     View servings;
    @BindView(R.id.servings_tv)
     TextView servings_tv;

//    private BluetoothService bluetoothService;

    public void initView() {
        setContentView(R.layout.activity_printer);
        ButterKnife.bind(this);
        servings.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
//        if (hasFocus) {
//            if (bluetoothService == null) {
//                BaseListView baseListView = (BaseListView) findViewById(R.id.listView);
//                SwitchView switchView0 = (SwitchView) findViewById(R.id.switchView0);
//                bluetoothService = new BluetoothService(PrinterActivity.this, baseListView, switchView0);
//            }
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        String s = SPTools.getS(K.PRINTER_SERVINGS);
//        if (TextUtils.isEmpty(s)) {
//            s = "1";
//            SPTools.setS(K.PRINTER_SERVINGS, s);
//        }
//        servings_tv.setText(s + "份");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.servings:
//                JumpTools.jump(PrinterActivity.this, JUMP_PRINTER_SERVINGS);
                startActivity(new Intent(PrinterActivity.this,PrinterServingsActivity.class));
                break;
        }
    }
}
