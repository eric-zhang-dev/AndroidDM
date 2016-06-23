package com.dm.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;

import com.dm.R;
import com.dm.utils.Utils;
import com.dm.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BillActivity extends BaseActivity {

    @BindView(R.id.gallery)
    Gallery gallery;

    private int selected = 10;
    private GalleryAdapter adapter;
    public void initView() {
        setContentView(R.layout.activity_bill);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        List<Double> list = new ArrayList<>();
        list.add(0.8);
        list.add(1.0);
        list.add(0.2);
        list.add(0.5);
        list.add(0.6);
        list.add(0.9);
        list.add(0.3);
        list.add(0.1);
        list.add(0.7);
        list.add(0.8);
        list.add(1.0);
        list.add(0.2);
        list.add(0.5);
        list.add(0.6);
        list.add(0.9);
        list.add(0.3);
        list.add(0.1);
        list.add(0.7);
        adapter = new GalleryAdapter(list);
        gallery.setAdapter(adapter);
        gallery.setCallbackDuringFling(false);
        gallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected = position;
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        gallery.setSelection(10);
        adapter.notifyDataSetChanged();
    }

    class GalleryAdapter extends BaseAdapter {

        class ViewHolder {
            public View column;
            public View point;
        }
        private List<Double> list;
        public GalleryAdapter(List<Double> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(BillActivity.this).inflate(R.layout.view_bill_item, null);
                holder = new ViewHolder();
                holder.column = convertView.findViewById(R.id.column);
                holder.point = convertView.findViewById(R.id.point);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            ViewGroup.LayoutParams layoutParams = holder.column.getLayoutParams();
            layoutParams.height = (int) (list.get(position) * Utils.dip2px(BillActivity.this, 100));
            holder.column.setLayoutParams(layoutParams);
            if (selected == position) {
                holder.column.setBackgroundResource(R.color.blue);
                holder.point.setBackgroundResource(R.drawable.radius_bg_red);
            } else {
                holder.column.setBackgroundResource(R.color.blue_light);
                holder.point.setBackgroundResource(R.drawable.radius_bg_grey);
            }
            return convertView;
        }


    }
}
