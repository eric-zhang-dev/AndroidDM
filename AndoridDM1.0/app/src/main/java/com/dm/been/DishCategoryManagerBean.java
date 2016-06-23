package com.dm.been;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


import com.dm.R;
import com.dm.view.BaseListView;

import java.util.HashMap;

/**
 * Created by Vinny on 2016/3/27.
 */
public class DishCategoryManagerBean extends ClassBean {

    private Context context;
    private HashMap<String, Object> hashMap;

    public DishCategoryManagerBean(Context context, HashMap<String, Object> hashMap) {
        super(context);
        this.context = context;
        this.hashMap = hashMap;
    }

    @Override
    public View createConvertView() {
        return LayoutInflater.from(mContext).inflate(R.layout.bean_dish_category_manager, null);
    }

    public class ViewHolder {
        public TextView name;
        public TextView price;
        public TextView btn;
    }

    @Override
    public void setViewData(View bindView, final BaseListView baseListView) {
        ViewHolder holder = (ViewHolder) getViewHolder(bindView);
    }

    @Override
    public Class getViewHolderClass() {
        return ViewHolder.class;
    }
}
