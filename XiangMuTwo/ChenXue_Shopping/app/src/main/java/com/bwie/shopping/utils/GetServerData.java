package com.bwie.shopping.utils;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * 1:类的用途  网络请求工具类
 * 2：@author Dell
 * 3：@date 2017/9/5
 */

public class GetServerData {
    //单例模式 封装OKhttp
    private static OkHttpClient okHttpClient = null;

    public GetServerData() {
    }

    //OkHttpClient请求网络
    public static OkHttpClient getInstance() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient();
        }
        return okHttpClient;
    }

    public static void doGet(String path, Callback callback) {
        //得到OkHttpClient
        OkHttpClient okHttpClient = getInstance();
        //得到Request
        Request request = new Request.Builder().url(path).build();
        //得到Call对象
        Call call = okHttpClient.newCall(request);
        //调用enqueue方法
        call.enqueue(callback);
    }

    //获取网络数据的方法
    public static void getServerData(Context context, String url, final OnGetServerDateLisnter onGetServerDateLisnter) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.get(context, url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                onGetServerDateLisnter.getData(responseString);
            }
        });
    }

    public interface OnGetServerDateLisnter {
        void getData(String string);
    }

}
