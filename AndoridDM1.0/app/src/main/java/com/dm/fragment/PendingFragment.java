package com.dm.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.dm.R;
import com.dm.base.BaseFragment;
import com.dm.service.PrintDataService;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 *待处理
 */
public class PendingFragment extends BaseFragment implements View.OnClickListener{
    @BindView(R.id.print)
    RelativeLayout mPrint;
    private PrintDataService printDataService;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pending, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initData() {
        mPrint.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.print:
                if (printDataService == null) {
                    printDataService = new PrintDataService(getActivity(), "D0:B5:C2:B2:E5:69");
                    if (printDataService.connect()) {
                        printDataService.send("dddddddddddddddddddddddd");
                    } else {
                        printDataService = null;
                    }
                } else {
                    printDataService.send("dddddddddddddddddddddddd");
                }
                break;
        }
    }
}
