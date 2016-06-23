package com.dm.utils;

/**
 * api help
 * Created by zhangyue on 2016/6/23.
 */
public class ServerAddress {
    public static final String dm_server = "http://mydian.me/order-merchant/";

    /**
     * get verify code
     * method Post
     *
     * @return
     */
    public static String getVerifyCodeAddress() {
        return dm_server + "account/verifyCode/send";
    }

    /**
     * forget password and register
     * @return
     */
    public static String commitVerifyCodeAddress() {
        return dm_server + "account/verifyCode/validate";
    }
    /**
     * user register
     * @return
     */
    public static String registerAddress() {
        return dm_server + "account/register";
    }
    /**
     * get token
     * @return
     */
    public static String getTokenAddress() {
        return dm_server + "account/token";
    }
    /**
     * login
     * @return
     */
    public static String loginAddress() {
        return dm_server + "account/login";
    }

    /**
     * forget password
     * @return
     */
    public static String forgetPasswoedAddress(){
        return dm_server+"account/reset";
    }

    /**
     * update password
     * @return
     */
    public static String updatePasswordAddress(){
        return dm_server+"account/modify";
    }
    /**
     * untreated order list
     * @return
     */
    public static String untreatedOrderListAddress(){
        return dm_server+"merchant/order/list";
    }
}
