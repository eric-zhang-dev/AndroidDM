package com.dm.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.dm.R;
import com.dm.base.BaseFragment;
import com.dm.ui.BillActivity;
import com.dm.ui.DishCategorysManagerActivity;
import com.dm.ui.DishSalesActivity;
import com.dm.ui.PrinterActivity;
import com.dm.ui.ProcessedActivity;
import com.dm.ui.RestaurantInfoActivity;
import com.dm.view.DotViewPager;
import com.dm.view.IconItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 管理
 */
public class ManageFragment extends BaseFragment implements View.OnClickListener {

    private List<View> views;
    private DotViewPager viewPager;
    private IconItem icon_open;
    private boolean isOpen = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage, container, false);
        viewPager = (DotViewPager) view.findViewById(R.id.viewPager);
        view.findViewById(R.id.icon_bill).setOnClickListener(this);
        view.findViewById(R.id.icon_dish).setOnClickListener(this);
        icon_open = (IconItem) view.findViewById(R.id.icon_open);
        icon_open.setOnClickListener(this);
        view.findViewById(R.id.icon_sales).setOnClickListener(this);
        view.findViewById(R.id.icon_information).setOnClickListener(this);
        view.findViewById(R.id.icon_printer).setOnClickListener(this);
        view.findViewById(R.id.icon_processed).setOnClickListener(this);
        initView();
        return view;
    }

    @Override
    protected void initData() {

    }


    private void initView() {
        views = new ArrayList<>();
        views.add(LayoutInflater.from(getActivity()).inflate(R.layout.fragment_manage_page_1, null, false));
        views.add(LayoutInflater.from(getActivity()).inflate(R.layout.fragment_manage_page_2, null, false));
        viewPager.initView(views);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.icon_bill:
                startActivity(new Intent(getActivity(), BillActivity.class));
                break;
            case R.id.icon_dish:
                startActivity(new Intent(getActivity(), DishCategorysManagerActivity.class));
                break;
            case R.id.icon_open:
                isOpen = !isOpen;
                if (isOpen) {
                    icon_open.setIconImg(R.mipmap.icon_open);
                } else {
                    icon_open.setIconImg(R.mipmap.icon_close);
                }
                break;
            case R.id.icon_sales:
//                JumpTools.jump(getActivity(), JUMP_DISH_SALES);
                startActivity(new Intent(getActivity(), DishSalesActivity.class));
                break;
            case R.id.icon_information:
//                JumpTools.jump(getActivity(), JUMP_RESTAURANT_INFO);
                startActivity(new Intent(getActivity(),RestaurantInfoActivity.class));
                break;

            case R.id.icon_printer:
//                JumpTools.jump(getActivity(), JUMP_PRINTER);
                startActivity(new Intent(getActivity(),PrinterActivity.class));
                break;
            case R.id.icon_processed:
//                JumpTools.jump(getActivity(), JUMP_PROCESSED);
                startActivity(new Intent(getActivity(),ProcessedActivity.class));
                break;
        }
    }
}
