package com.bwie.shopping.presenter;

import android.content.Context;
import android.view.View;

import com.bwie.shopping.model.ILoginModel;
import com.bwie.shopping.model.LoginModel;
import com.bwie.shopping.view.LoginView;

/**
 * 1:类的用途
 * 2：@author Dell
 * 3：@date 2017/9/12
 */

public class LoginPresenter {
    private LoginView loginView;
    private ILoginModel iLoginModel;

    public LoginPresenter(LoginView loginView) {
        this.loginView = loginView;
        iLoginModel = new LoginModel();
    }

    public int login(Context context,String uname,String pwd){
        int code = iLoginModel.login(context, uname, pwd);
        return code;
    }
}
