package com.bawei.xiangmutwo.model;

import android.content.Context;
import android.text.TextUtils;

import com.bawei.okhttplibrary.utils.GsonObjectCallback;
import com.bawei.okhttplibrary.utils.OkhttpUtils;
import com.bawei.xiangmutwo.api.Api;
import com.bawei.xiangmutwo.bean.LoginBean;
import com.bawei.xiangmutwo.presenter.LoginFinish;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

//import com.bawei.okhttplibrary.utils.GsonObjectCallback;
//import com.bawei.okhttplibrary.utils.OkhttpUtils;

/**
 * Created by dell-pc on 2017/10/12.
 */

public class LoginModelImpl implements LoginModel {

    @Override
    public void login(Context context, String loginname, String loginpwd, LoginFinish loginFinish) {
        if(TextUtils.isEmpty(loginname) || TextUtils.isEmpty(loginpwd)){
            loginFinish.onNameError();
            loginFinish.onPwdError();
        }else{
            loginData(context, loginname, loginpwd, loginFinish);
        }

    }

    //登录数据请求
    @Override
    public void loginData(Context context, String loginname, String loginpwd, final LoginFinish finish) {
        Map<String,String> map = new HashMap<>();
        map.put("username",loginname);
        map.put("password",loginpwd);
        map.put("client",Api.Client);
        OkhttpUtils.doPost(map, Api.LOGINURL, new GsonObjectCallback<LoginBean>() {
            @Override
            public void onUi(LoginBean loginBean) {
                if(loginBean.getCode() == 200){
                    finish.onLoginSuccess(loginBean);
                }
                if(loginBean.getCode() == 400){
                    finish.onLoginFail();
                }
            }

            @Override
            public void onFailed(Call call, IOException e) {

            }
        });
    }
}
