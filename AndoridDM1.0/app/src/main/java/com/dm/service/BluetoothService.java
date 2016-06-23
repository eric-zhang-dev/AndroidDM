//package com.dm.service;
//
//import android.app.Activity;
//import android.app.ProgressDialog;
//import android.bluetooth.BluetoothAdapter;
//import android.bluetooth.BluetoothDevice;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.os.Handler;
//
//import com.mohe.dianmi.base.BaseListView;
//import com.mohe.dianmi.bean.classbean.PrinterBean;
//import com.mohe.dianmi.view.SwitchView;
//
//import java.util.ArrayList;
//
///**
// * Created by Administrator on 2016/3/20.
// */
//public class BluetoothService {
//
//    private Context context = null;
//    private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//    private SwitchView switchView = null;
//    private BaseListView baseListView;
//    private ArrayList<BluetoothDevice> devices = null; // 用于存放未配对蓝牙设备
//
//    public BluetoothService(Context context, BaseListView baseListView, SwitchView switchView) {
//        this.context = context;
//        this.baseListView = baseListView;
//        this.switchView = switchView;
//        this.devices = new ArrayList<>();
//        this.initListView();
//        this.initIntentFilter();
//    }
//
//    private void initListView() {
//        baseListView.createView(PrinterBean.class);
//        if (isOpen()) {
//            switchView.setChecked(true, false);
//            searchDevices();
//        } else {
//            switchView.setChecked(false, false);
//        }
//        switchView.setOnCheckedChangeListener(new SwitchView.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(boolean isChecked) {
//                if (isChecked) {
//                    openBluetooth((Activity) context);
//                } else {
//                    closeBluetooth();
//                }
//            }
//        });
//
//    }
//
//    private void initIntentFilter() {
//        // 设置广播信息过滤
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
//        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
//        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
//        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
//        intentFilter.addAction("android.bluetooth.device.action.PAIRING_REQUEST");
//        // 注册广播接收器，接收并处理搜索结果
//        context.registerReceiver(receiver, intentFilter);
//    }
//
//    /**
//     * 打开蓝牙
//     */
//    public void openBluetooth(Activity activity) {
//        Intent enableBtIntent = new Intent(
//                BluetoothAdapter.ACTION_REQUEST_ENABLE);
//        activity.startActivityForResult(enableBtIntent, 1);
//    }
//
//    /**
//     * 关闭蓝牙
//     */
//    public void closeBluetooth() {
//        this.bluetoothAdapter.disable();
//    }
//
//    /**
//     * 判断蓝牙是否打开
//     *
//     * @return boolean
//     */
//    public boolean isOpen() {
//        return this.bluetoothAdapter.isEnabled();
//
//    }
//
//    /**
//     * 搜索蓝牙设备
//     */
//    public void searchDevices() {
//        baseListView.clearBeanData();
//        // 寻找蓝牙设备，android会将查找到的设备以广播形式发出去
//        this.bluetoothAdapter.startDiscovery();
//    }
//
//    /**
//     * 蓝牙广播接收器
//     */
//    private BroadcastReceiver receiver = new BroadcastReceiver() {
//
//        ProgressDialog progressDialog = null;
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            System.out.println("onReceive----" + action);
//            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
//                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//                PrinterBean printerBean = new PrinterBean(context, device);
//                if (!baseListView.getDatas().contains(printerBean)) {
//                    baseListView.addBeanData(printerBean);
//                }
//            } else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
//                progressDialog = ProgressDialog.show(context, "请稍等...",
//                        "搜索蓝牙设备中...", true);
//
//            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED
//                    .equals(action)) {
//                System.out.println("设备搜索完毕");
//                if (progressDialog != null)
//                    progressDialog.dismiss();
//                baseListView.notifyDataSetChanged();
//            } else if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
//                if (bluetoothAdapter.getState() == BluetoothAdapter.STATE_ON) {
//                    System.out.println("--------打开蓝牙-----------");
//                    switchView.setChecked(true, false);
//                    baseListView.setEnabled(true);
//                    searchDevices();
//                } else if (bluetoothAdapter.getState() == BluetoothAdapter.STATE_OFF) {
//                    System.out.println("--------关闭蓝牙-----------");
//                    switchView.setChecked(false, false);
//                    baseListView.setEnabled(false);
//                    baseListView.clearBeanData();
//                    baseListView.notifyDataSetChanged();
//                }
//            } else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)){
//                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//                switch (device.getBondState()) {
//                    case BluetoothDevice.BOND_BONDING:
//                        System.out.println("正在配对......");
//                        break;
//                    case BluetoothDevice.BOND_BONDED:
//                        System.out.println("完成配对");
//                        break;
//                    case BluetoothDevice.BOND_NONE:
//                        System.out.println("取消配对");
//                    default:
//                        System.out.println("default");
//                        break;
//                }
//            } else if ("android.bluetooth.device.action.PAIRING_REQUEST".equals(action)) {
//                final BluetoothDevice bluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//                try {
//                    BluetoothTools.setPin(bluetoothDevice); //手机和蓝牙采集器配对
//                    BluetoothTools.createBond(bluetoothDevice);
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            BluetoothTools.cancelPairingUserInput(bluetoothDevice);
//                            baseListView.notifyDataSetChanged();
//                        }
//                    }, 1000);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    };
//
//    Handler handler = new Handler();
//}
