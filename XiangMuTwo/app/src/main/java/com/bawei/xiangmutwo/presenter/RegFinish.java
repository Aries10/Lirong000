package com.bawei.xiangmutwo.presenter;

/**
 * Created by dell-pc on 2017/10/12.
 */

public interface RegFinish {

    /**
     * 用户名输入错误
     */
    void onRegNameError();

    /**
     * 密码输入错误
     */
    void onRegPwd1Error();

    /**
     * 确认密码输入错误
     */
    void onRegPwd2Error();

    /**
     * 邮箱输入错误
     */
    void onRegEmailError();

    /**
     * 提交成功
     */
    void onRegSuccess();

    /**
     * 提交失败
     */
    void onRegFail();

}
