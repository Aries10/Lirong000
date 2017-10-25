package com.bawei.xiangmutwo.api;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by dell-pc on 2017/10/12.
 */

public class Api {

    public static String API_IP = "169.254.131.247";
    public static String Client = "android";
    public static String IndexURL = "http://m.yunifang.com/yunifang/mobile/home";
    public static String REGURL = "http://"+API_IP+"/mobile/index.php?act=login&op=register";
    public static String LOGINURL = "http://"+API_IP+"/mobile/index.php?act=login";
    public static String TYPEBEANURL = "http://"+API_IP+"/mobile/index.php?act=goods_class";
    public static String UNREG = "http://"+API_IP+"/mobile/index.php?act=logout";
    public static String SEARCHURL = "http://"+API_IP+"/mobile/index.php?act=goods&op=goods_list&page=100";
    public static String WebViewURL = "http://"+API_IP+"/mobile/index.php?act=goods&op=goods_body&goods_id=";
    public static String DetailsURL = "http://"+API_IP+"/mobile/index.php?act=goods&op=goods_detail&goods_id=";
    public static String CartADDURL = "http://"+API_IP+"/mobile/index.php?act=member_cart&op=cart_add";
    public static String ShopShowURL = "http://"+API_IP+"/mobile/index.php?act=member_cart&op=cart_list";
    public static String DeleteURL = "http://"+API_IP+"/mobile/index.php?act=member_cart&op=cart_del";
    public static String AddressURL = "http://"+API_IP+"/mobile/index.php?act=member_address&op=address_list";
    public static String AddAddressURL = "http://"+API_IP+"/mobile/index.php?act=member_address&op=address_add";

    public static String DeleteAddress = "http://"+API_IP+"/mobile/index.php?act=member_address&op=address_del";
    public static String UpdateAddress = "http://"+API_IP+"/mobile/index.php?act=member_address&op=address_edit";

    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;
    public static void init(Context context){
        sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

}
