package com.dm.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.dm.R;
import com.dm.application.DmApplication;
import com.dm.base.BaseFragment;
import com.dm.net.HttpHelper;
import com.dm.service.PrintDataService;
import com.dm.utils.Constant;
import com.dm.utils.ServerAddress;
import com.dm.utils.SignUtil;
import com.yolanda.nohttp.RequestMethod;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
        Map<String, Object> map = new HashMap<>();
        map.put("merchantId", DmApplication.getInstance().getMerchantId());
        map.put("status", "1");
        map.put("pageSize", "20");
        map.put("pageNo", "1");
        map.put("token", tm.getDeviceId());//设备码
        map.put("version", "1.0.0");
        map.put("source", "1");
        map.put("sign", SignUtil.signMD5(map, Constant.SIGN));
        HttpHelper.Http(ServerAddress.untreatedOrderListAddress(), 1, getIdentification(), new JSONObject(map), getActivity(), RequestMethod.POST,true);
    }

    @Override
    public void refreshUI(int id, Object params) {
        super.refreshUI(id, params);
        switch (id){
            case 1:
                Toast.makeText(getActivity(),"safas",Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.print:
                Map<String, Object> map = new HashMap<>();
                map.put("merchantId", DmApplication.getInstance().getMerchantId());
                map.put("status", "1");
                map.put("pageSize", "20");
                map.put("pageNo", "1");
                map.put("token", tm.getDeviceId());//设备码
                map.put("version", "1.0.0");
                map.put("source", "1");
                map.put("sign", SignUtil.signMD5(map, Constant.SIGN));
                HttpHelper.Http(ServerAddress.untreatedOrderListAddress(), 1, getIdentification(), new JSONObject(map), getActivity(), RequestMethod.POST,true);
//                if (printDataService == null) {
//                    printDataService = new PrintDataService(getActivity(), "D0:B5:C2:B2:E5:69");
//                    if (printDataService.connect()) {
//                        printDataService.send("dddddddddddddddddddddddd");
//                    } else {
//                        printDataService = null;
//                    }
//                } else {
//                    printDataService.send("dddddddddddddddddddddddd");
//                }
                break;
        }
    }
}
