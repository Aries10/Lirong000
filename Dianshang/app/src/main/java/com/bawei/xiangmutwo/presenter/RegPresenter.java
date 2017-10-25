package com.bawei.xiangmutwo.presenter;

import android.content.Context;

/**
 * Created by dell-pc on 2017/10/12.
 */

public interface RegPresenter {

    /**
     * 注册通过
     */
    void regPass(Context context, String regname, String regpwd1, String regpwd2, String email);

}
