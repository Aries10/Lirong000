package com.bawei.xiangmutwo.presenter;

import com.bawei.xiangmutwo.bean.LoginBean;

/**
 * Created by dell-pc on 2017/10/12.
 */

public interface LoginFinish {

    /**
     * 用户名错误
     */
    void onNameError();

    /**
     * 密码错误
     */
    void onPwdError();

    /**
     * 登录成功
     */
    void onLoginSuccess(LoginBean loginBean);

    /**
     * 登录失败
     */
    void onLoginFail();

}
