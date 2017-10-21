package com.bawei.xiangmutwo.model;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.bawei.okhttplibrary.utils.GsonObjectCallback;
import com.bawei.okhttplibrary.utils.OkhttpUtils;
import com.bawei.xiangmutwo.api.Api;
import com.bawei.xiangmutwo.bean.RegBean;
import com.bawei.xiangmutwo.presenter.RegFinish;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by dell-pc on 2017/10/12.
 */

public class RegModelImpl implements RegModel {

    private RegFinish regFinish = null;

    public RegModelImpl(RegFinish regFinish) {
        this.regFinish = regFinish;
    }

    //判断是否注册成功
    @Override
    public void reg(Context context, String regname, String regpwd1, String regpwd2, String email) {
        if(TextUtils.isEmpty(regname) || TextUtils.isEmpty(regpwd1) || TextUtils.isEmpty(regpwd2) || TextUtils.isEmpty(email)){
            regFinish.onRegNameError();
            regFinish.onRegPwd1Error();
            regFinish.onRegPwd2Error();
            regFinish.onRegEmailError();
            return;
        }else{
            regData(context, regname, regpwd1, regpwd2, email);
        }

    }

    /**
     * 请求数据
     */
    @Override
    public void regData(final Context context, String regname, String regpwd1, String regpwd2, String email) {
        Map<String,String> map = new HashMap<>();
        map.put("username",regname);
        map.put("password",regpwd1);
        map.put("password_confirm",regpwd2);
        map.put("email",email);
        map.put("client", Api.Client);
        //POST请求
        OkhttpUtils.doPost(map, Api.REGURL, new GsonObjectCallback<RegBean>() {
            @Override
            public void onUi(RegBean regBean) {
                if(regBean.getCode() == 200){
                    regFinish.onRegSuccess();
                    Toast.makeText(context,"注册成功",Toast.LENGTH_SHORT).show();
                }else{
                    regFinish.onRegFail();
                    Toast.makeText(context,"注册失败",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(Call call, IOException e) {

            }
        });
    }

}
