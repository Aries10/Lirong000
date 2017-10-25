package com.bawei.xiangmutwo.presenter;

import android.content.Context;

import com.bawei.xiangmutwo.model.RegModel;
import com.bawei.xiangmutwo.model.RegModelImpl;
import com.bawei.xiangmutwo.view.RegView;

/**
 * Created by dell-pc on 2017/10/12.
 */

public class RegPresenterImpl implements RegPresenter,RegFinish {

    private RegView regView = null;
    private final RegModel regModel;

    public RegPresenterImpl(RegView regView) {
        this.regView = regView;
        regModel = new RegModelImpl(this);
    }

    @Override
    public void regPass(Context context, String regname, String regpwd1, String regpwd2, String email) {
        regModel.reg(context,regname,regpwd1,regpwd2,email);
    }

    @Override
    public void onRegNameError() {
        if(regView != null){
            regView.setRegNameError();
        }
    }

    @Override
    public void onRegPwd1Error() {
        if(regView != null){
            regView.setRegPwd1Error();
        }
    }

    @Override
    public void onRegPwd2Error() {
        if(regView != null){
            regView.setRegPwd2Error();
        }
    }

    @Override
    public void onRegEmailError() {
        if(regView != null){
            regView.setRegEmailError();
        }
    }

    @Override
    public void onRegSuccess() {
        if(regView != null){
            regView.setRegSuccess();
        }
    }

    @Override
    public void onRegFail() {
        if(regView != null){
            regView.setRegFail();
        }
    }
}
