package com.bawei.xiangmutwo.presenter;

import android.content.Context;

import com.bawei.xiangmutwo.bean.LoginBean;
import com.bawei.xiangmutwo.model.LoginModel;
import com.bawei.xiangmutwo.model.LoginModelImpl;
import com.bawei.xiangmutwo.view.LoginView;

/**
 * Created by dell-pc on 2017/10/12.
 */

public class LoginPresenterImpl implements LoginPresenter,LoginFinish {

    private LoginView loginView = null;
    private final LoginModel loginModel;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        loginModel = new LoginModelImpl();
    }

    @Override
    public void loginPass(Context context, String loginname, String loginpwd) {
        loginModel.login(context,loginname,loginpwd,this);
    }

    @Override
    public void onNameError() {
        if(loginView != null){
            loginView.setNameError();
        }
    }

    @Override
    public void onPwdError() {
        if(loginView != null){
            loginView.setPwdError();
        }
    }

    @Override
    public void onLoginSuccess(LoginBean loginBean) {
        if(loginView != null){
            loginView.setLoginSuccess(loginBean);
        }
    }

    @Override
    public void onLoginFail() {
        if(loginView != null){
            loginView.setLoginFail();
        }
    }
}
