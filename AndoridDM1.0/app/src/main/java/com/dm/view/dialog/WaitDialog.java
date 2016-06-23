package com.dm.view.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.Window;

import com.dm.R;

/**
 * Created by zhangyue on 2016/6/23.
 */
public class WaitDialog extends ProgressDialog {
    public WaitDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCanceledOnTouchOutside(false);
        setProgressStyle(STYLE_SPINNER);
        setMessage(context.getText(R.string.wait_dialog_title));
    }
}
