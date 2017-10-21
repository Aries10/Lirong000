package com.bawei.xiangmutwo.view;

/**
 * Created by dell-pc on 2017/10/12.
 */

public interface RegView {

    /**
     * 用户名输入错误
     */
    void setRegNameError();

    /**
     * 密码输入错误
     */
    void setRegPwd1Error();

    /**
     * 确认密码输入错误
     */
    void setRegPwd2Error();

    /**
     * 邮箱输入错误
     */
    void setRegEmailError();

    /**
     * 提交成功
     */
    void setRegSuccess();

    /**
     * 提交失败
     */
    void setRegFail();

}
