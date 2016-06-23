package com.dm.been;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.dm.R;
import com.dm.ui.DishCategoryManagerActivity;
import com.dm.view.BaseListView;

import java.util.HashMap;

/**
 * Created by Vinny on 2016/3/27.
 */
public class DishCategorysManagerBean extends ClassBean {

    private Context context;
    private HashMap<String, Object> hashMap;

    public DishCategorysManagerBean(Context context, HashMap<String, Object> hashMap) {
        super(context);
        this.context = context;
        this.hashMap = hashMap;
    }

    @Override
    public View createConvertView() {
        return LayoutInflater.from(mContext).inflate(R.layout.bean_dish_categorys_manager, null);
    }

    public class ViewHolder {
        public View delete;
        public View edit;
        public TextView name;
        public TextView count;
        public View arrow;
    }

    @Override
    public void setViewData(View bindView, final BaseListView baseListView) {
        ViewHolder holder = (ViewHolder) getViewHolder(bindView);
        if (baseListView.checkState(BaseListView.LIST_STATE_EDITING)) {
            holder.delete.setVisibility(View.VISIBLE);
            holder.edit.setVisibility(View.VISIBLE);
            holder.count.setVisibility(View.GONE);
            holder.arrow.setVisibility(View.GONE);
            bindView.setOnClickListener(null);
        } else {
            holder.delete.setVisibility(View.GONE);
            holder.edit.setVisibility(View.GONE);
            holder.count.setVisibility(View.VISIBLE);
            holder.arrow.setVisibility(View.VISIBLE);
            bindView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext,DishCategoryManagerActivity.class));
//                    JumpTools.jump((Activity) mContext, JUMP_DISH_CATEGORY_MANAGER);
                }
            });
        }
    }

    @Override
    public Class getViewHolderClass() {
        return ViewHolder.class;
    }
}
