package com.dm.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dm.R;

/**
 * Created by zhangyue on 2016/6/20.
 */
public class SelectBtn extends RelativeLayout{
    Drawable selectImg = null;
    Drawable noSelectImg = null;
    int selectColor = 0;
    int noSelectColor = 0;
    String text;
    boolean isSelect = false;
    TextView tv;
    ImageView iv;

    public SelectBtn(Context context) {
        super(context);
    }

    public SelectBtn(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SelectBtn);
        selectImg = a.getDrawable(R.styleable.SelectBtn_selectImg);
        noSelectImg = a.getDrawable(R.styleable.SelectBtn_noSelectImg);
        isSelect = a.getBoolean(R.styleable.SelectBtn_isSelect, false);
        selectColor = a.getColor(R.styleable.SelectBtn_selectColor, 0);
        noSelectColor = a.getColor(R.styleable.SelectBtn_noSelectColor, 0);
        text = a.getString(R.styleable.SelectBtn_text);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_select_btn, this, true);
        tv = (TextView) findViewById(R.id.tv);
        tv.setText(text);
        iv = (ImageView) findViewById(R.id.iv);
        setSelect(isSelect);
    }

    public void setSelect(boolean b) {
        isSelect = b;
        if (b) {
            tv.setTextColor(selectColor);
            iv.setImageDrawable(selectImg);
        } else {
            tv.setTextColor(noSelectColor);
            iv.setImageDrawable(noSelectImg);
        }
    }

    public boolean getIsSelect() {
        return isSelect;
    }
}
