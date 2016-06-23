package com.dm.been;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.dm.R;
import com.dm.view.BaseListView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Vinny on 2016/3/27.
 */
public class DishSalesBean extends ClassBean {

    private Context context;
    private List<HashMap<String, Object>> list;

    public DishSalesBean(Context context, List<HashMap<String, Object>> list) {
        super(context);
        this.context = context;
        this.list = list;
    }

    @Override
    public View createConvertView() {
        return LayoutInflater.from(mContext).inflate(R.layout.bean_dish_sales, null);
    }

    public class ViewHolder {
        public TextView time;
        public LinearLayout layout;
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
