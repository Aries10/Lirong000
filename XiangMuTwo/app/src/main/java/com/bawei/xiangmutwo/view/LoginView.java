package com.bawei.xiangmutwo.view;

import com.bawei.xiangmutwo.bean.LoginBean;

/**
 * 登录view
 * Created by dell-pc on 2017/10/12.
 */

public interface LoginView {

    /**
     * 姓名错误
     */
    void setNameError();

    /**
     * 密码错误
     */
    void setPwdError();

    /**
     * 登录成功
     */
    void setLoginSuccess(LoginBean loginBean);

    /**
     * 登录失败
     */
    void setLoginFail();

}
