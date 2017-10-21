package com.bawei.xiangmutwo.presenter;

import android.content.Context;

/**
 * Created by dell-pc on 2017/10/12.
 */

public interface LoginPresenter {

    /**
     * 登录通过
     * @param context
     * @param loginname
     * @param loginpwd
     */
    void loginPass(Context context,String loginname,String loginpwd);

}
