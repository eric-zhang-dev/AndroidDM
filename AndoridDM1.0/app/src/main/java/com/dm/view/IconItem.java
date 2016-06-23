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

public class IconItem extends RelativeLayout {
    Drawable iconImg = null;
    String iconText;
    private TextView tv;
    private ImageView iv;

    public IconItem(Context context) {
        super(context);
    }

    public IconItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.IconItem);
        iconImg = a.getDrawable(R.styleable.IconItem_iconImg);
        iconText = a.getString(R.styleable.IconItem_iconText);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_icon_item, this, true);
        tv = ((TextView) findViewById(R.id.tv));
        iv = ((ImageView) findViewById(R.id.iv));
        tv.setText(iconText);
        iv.setImageDrawable(iconImg);
    }

    public void setIconImg(int img) {
        iv.setImageResource(img);
    }
}
