package com.bawei.xiangmutwo.model;

import android.content.Context;

import com.bawei.xiangmutwo.presenter.LoginFinish;

/**
 * Created by dell-pc on 2017/10/12.
 */

public interface LoginModel {

    /**
     * 判断是否登录成功
     */
    void login(Context context, String loginname, String loginpwd, LoginFinish finish);

    void loginData(Context context, String loginname, String loginpwd, LoginFinish finish);

}
