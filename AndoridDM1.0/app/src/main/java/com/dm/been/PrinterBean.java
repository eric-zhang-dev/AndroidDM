package com.dm.been;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.dm.R;
import com.dm.service.BluetoothTools;
import com.dm.view.BaseListView;

public class PrinterBean extends ClassBean {

    private Context context;
    private BluetoothDevice bluetoothDevice;

    public PrinterBean(Context context, BluetoothDevice bluetoothDevice) {
        super(context);
        this.context = context;
        this.bluetoothDevice = bluetoothDevice;
    }

    public BluetoothDevice getBluetoothDevice() {
        return bluetoothDevice;
    }

    @Override
    public View createConvertView() {
        return LayoutInflater.from(mContext).inflate(R.layout.bean_printer, null);
    }

    public class ViewHolder {
        public TextView name;
        public TextView status;
    }

    @Override
    public void setViewData(View bindView, final BaseListView baseListView) {
        ViewHolder holder = (ViewHolder) getViewHolder(bindView);
        holder.name.setText(bluetoothDevice.getName());
        if (bluetoothDevice.getBondState() == BluetoothDevice.BOND_BONDED) {
            holder.status.setText("断开");
            holder.status.setBackgroundResource(R.drawable.radius_bg_blue);
        } else {
            holder.status.setText("连接");
            holder.status.setBackgroundResource(R.drawable.radius_bg_grey);
        }
        holder.status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bluetoothDevice.getBondState() == BluetoothDevice.BOND_BONDED) {
                    BluetoothTools.cancelBond(bluetoothDevice);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            baseListView.notifyDataSetChanged();
                        }
                    }, 200);
                } else {
                    BluetoothTools.setPin(bluetoothDevice);
                    BluetoothTools.createBond(bluetoothDevice);
                }
            }
        });
    }

    @Override
    public Class getViewHolderClass() {
        return ViewHolder.class;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof PrinterBean)
            return bluetoothDevice.equals(((PrinterBean) o).getBluetoothDevice());
        return false;
    }

    Handler handler = new Handler();
}
