package com.bwie.shopping.model;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.bwie.shopping.activity.RegActivity;
import com.bwie.shopping.api.API;
import com.bwie.shopping.fragment.Fragment_Main;
import com.bwie.shopping.utils.MyUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * 1:类的用途
 * 2：@author Dell
 * 3：@date 2017/9/12
 */

public class LoginModel implements ILoginModel {

    private int code;

    @Override
    public int login(final Context context, final String uname, final String pwd) {
        RequestParams params = new RequestParams(API.LOGIN_PATH);
        params.addBodyParameter("username", uname);
        params.addBodyParameter("password", pwd);
        params.addBodyParameter("client", API.SYSTEM_TYPE);
        params.setMultipart(true);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //解析得到的字符串
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    code = jsonObject.optInt("code");
                    if (code ==200) {
                        //得到userid
                        JSONObject datas = jsonObject.optJSONObject("datas");
                        String userid = datas.optString("userid");
                        String key = datas.optString("key");
                        //登录成功  存储用户信息
                        SharedPreferences sharedPreferencesInstance = MyUtils.getSharedPreferencesInstance(context);
                        SharedPreferences.Editor edit = sharedPreferencesInstance.edit();
                        edit.putString("username", uname);
                        edit.putString("password", pwd);
                        edit.putString("userid",userid);
                        edit.putString("key",key);
                        edit.putBoolean("islogin",true);
                        edit.commit();
                    }else {
                        Toast.makeText(context, "用户名或密码有误", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
        return code;
    }

}
