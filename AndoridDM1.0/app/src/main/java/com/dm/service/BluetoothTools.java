package com.dm.service;

import android.bluetooth.BluetoothDevice;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2016/3/27.
 */
public class BluetoothTools {

    public static void createBond(BluetoothDevice bluetoothDevice) {
        try {
            Method createBondMethod = BluetoothDevice.class.getMethod("createBond");
            Boolean b = (Boolean) createBondMethod.invoke(bluetoothDevice);
        } catch (Exception e) {
        }
    }

    public static void cancelBond(BluetoothDevice bluetoothDevice) {
        try {
            Method createBondMethod = BluetoothDevice.class.getMethod("removeBond");
            Boolean b = (Boolean) createBondMethod.invoke(bluetoothDevice);
        } catch (Exception e) {
        }
    }

    public static boolean setPin(BluetoothDevice bluetoothDevice) {
        try {
            Method removeBondMethod = bluetoothDevice.getClass().getDeclaredMethod("setPin", new Class[] {byte[].class});
            Boolean returnValue = (Boolean) removeBondMethod.invoke(bluetoothDevice, new Object[]{"0000".getBytes()});
        } catch (SecurityException e) {
            // throw new RuntimeException(e.getMessage());
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // throw new RuntimeException(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;

    }

    public static boolean cancelPairingUserInput(BluetoothDevice bluetoothDevice) {
        try {
            Method createBondMethod = bluetoothDevice.getClass().getMethod("cancelPairingUserInput");
            Boolean returnValue = (Boolean) createBondMethod.invoke(bluetoothDevice);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }
}
