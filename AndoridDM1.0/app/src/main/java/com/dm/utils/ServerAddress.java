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
     * category add 2.1
     * @return
     */
    public static String categoryAddAddress(){
        return dm_server+"course/category/add";
    }
    /**
     * category list 2.2
     * @return
     */
    public static String categoryListAddress(){
        return dm_server+"course/category/list";
    }
    /**
     * category update 2.3
     * @return
     */
    public static String categoryUpdateAddress(){
        return dm_server+"course/category/update";
    }
    /**
     * category delete 2.4
     * @return
     */
    public static String categoryDeleteAddress(){
        return dm_server+"course/category/delete";
    }
    /**
     * category delete 2.5(错误)
     * @return
     */
    public static String categoryFuckAddress(){
        return dm_server+"course/category/delete";
    }
    /**
     * list Category 2.6
     * @return
     */
    public static String listByCategoryAddress(){
        return dm_server+"course/listByCategory";
    }
    /**
     * course update 2.7
     * @return
     */
    public static String courseUpdate(){
        return dm_server+"course/update";
    }
    /**
     * course update 2.8(有误)
     * @return
     */
    public static String coursedUpdate(){
        return dm_server+"course/update";
    }
    /**
     * untreated order list
     * @return
     */
    public static String untreatedOrderListAddress(){
        return dm_server+"merchant/order/list";
    }
}
