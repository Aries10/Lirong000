package com.bawei.myproject.utils;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.myproject.MainActivity;
import com.bawei.myproject.R;
import com.bawei.myproject.bean.QQBean;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 类的用途: 实现qq登录
 * Created by dell-pc on 2017/9/13.
 */

public class BaseUiListener implements IUiListener {

    private static final String TAG = "MainActivity";
    private Tencent mTencent;
    private  UserInfo mUserInfo;
    private MainActivity mainActivity;
    private ImageView img_touxiang;
    private View listv;
    public static QQBean qqBean;

    public BaseUiListener(Tencent mTencent, UserInfo mUserInfo, MainActivity mainActivity, ImageView img_touxiang, View listv) {
        this.mTencent = mTencent;
        this.mUserInfo = mUserInfo;
        this.mainActivity = mainActivity;
        this.img_touxiang = img_touxiang;
        this.listv = listv;
    }

    @Override
    public void onComplete(Object response) {
        Toast.makeText(mainActivity, "授权成功", Toast.LENGTH_SHORT).show();
        Log.e(TAG, "response:" + response);
        JSONObject obj = (JSONObject) response;
        try {
            String openID = obj.getString("openid");
            String accessToken = obj.getString("access_token");
            String expires = obj.getString("expires_in");
            mTencent.setOpenId(openID);
            mTencent.setAccessToken(accessToken,expires);
            QQToken qqToken = mTencent.getQQToken();
            mUserInfo = new UserInfo(mainActivity,qqToken);
            mUserInfo.getUserInfo(new IUiListener() {

                private LinearLayout ll_head01,ll_head02;

                @Override
                public void onComplete(Object response) {
                    Log.e(TAG,"登录成功"+response.toString());
                    //登录成功  解析数据
                    getGsonData(response);
                }

                /**
                 * 登录成功后解析数据的方法
                 * @param response
                 */
                private void getGsonData(Object response) {
                    Gson gson = new Gson();
                    qqBean = gson.fromJson(response.toString(), QQBean.class);
                    ll_head01 = listv.findViewById(R.id.ll_head01);
                    ll_head02 = listv.findViewById(R.id.ll_head02);
                    ImageView ll_img = listv.findViewById(R.id.ll_img);
                    ll_head01.setVisibility(View.GONE);
                    ll_head02.setVisibility(View.VISIBLE);
                    //把传入的信息显示
                    DisplayImageOptions options = new DisplayImageOptions.Builder()
                            .displayer(new RoundedBitmapDisplayer(360))
                            .build();
                    ImageLoader.getInstance().displayImage(qqBean.getFigureurl_1(),ll_img,options);
                    ImageLoader.getInstance().displayImage(qqBean.getFigureurl_1(),img_touxiang,options);
                    TextView tv_headname = listv.findViewById(R.id.tv_headname);
                    TextView tv_headcity = listv.findViewById(R.id.tv_headcity);
                    TextView tv_headgender = listv.findViewById(R.id.tv_headgender);
                    tv_headname.setText("姓名:"+qqBean.getNickname());
                    tv_headgender.setText("性别:"+qqBean.getGender());
                    tv_headcity.setText("城市:"+qqBean.getCity());
                }

                @Override
                public void onError(UiError uiError) {
                    Log.e(TAG,"登录失败"+uiError.toString());
                }

                @Override
                public void onCancel() {
                    Log.e(TAG,"登录取消");
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(UiError uiError) {
        Toast.makeText(mainActivity, "授权失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancel() {
        Toast.makeText(mainActivity, "授权取消", Toast.LENGTH_SHORT).show();
    }

}
