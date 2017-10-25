package com.bawei.xiangmutwo.model;

import android.content.Context;

/**
 * Created by dell-pc on 2017/10/12.
 */

public interface RegModel {

    /**
     * 注册判断
     * @param context
     * @param regname
     * @param regpwd1
     * @param regpwd2
     * @param email
     */
    void reg(Context context,String regname, String regpwd1,String regpwd2,String email);

    /**
     * 注册数据
     * @param context
     * @param regname
     * @param regpwd1
     * @param regpwd2
     * @param email
     */
    void regData(Context context,String regname, String regpwd1,String regpwd2,String email);

}
