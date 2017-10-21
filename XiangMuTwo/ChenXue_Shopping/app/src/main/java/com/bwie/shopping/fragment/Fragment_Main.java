package com.bwie.shopping.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bwie.shopping.R;
import com.bwie.shopping.activity.AddressActivity;
import com.bwie.shopping.activity.LogingActivity;
import com.bwie.shopping.utils.MyUtils;

/**
 * 1:类的用途
 * 2：@author Dell
 * 3：@date 2017/8/31
 */

public class Fragment_Main extends Fragment {
    private ImageView login_tou, myLocation,mainpage;
    private TextView login_state, tvResult;
    private SharedPreferences sharedPreferences;
    private boolean islogin;
    private TextView userstate;
    private Button login_but;
    private View pview;
    private TextView myAddress;

    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        //获取控件的方法
        initView(view);
        //设置图片充满
        mainpage.setScaleType(ImageView.ScaleType.FIT_XY);

        //得到sharedPreferencesInstance  判断是否登录
        sharedPreferences = MyUtils.getSharedPreferencesInstance(getActivity());
        islogin = sharedPreferences.getBoolean("islogin", false);
        if (!islogin) {//没有登录跳转到登录界面
            Intent intent = new Intent(getActivity(), LogingActivity.class);
            intent.putExtra("where","main");
            startActivity(intent);
        } else {
            //将用户名赋值
            String username = sharedPreferences.getString("username", "");
            login_state.setText(username);
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sharedPreferences.getBoolean("islogin", false)) {
            login_state.setText(sharedPreferences.getString("username", ""));
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //实例PopupWindow 并设置
        final PopupWindow popupWindow = new PopupWindow(pview, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        login_tou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPreferences.getBoolean("islogin", false)) {
                    userstate.setText(sharedPreferences.getString("username", ""));
                    login_but.setText("退出");
                } else {
                    userstate.setText("未登录");
                    login_but.setText("登录");
                }
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                } else {
                    popupWindow.showAsDropDown(login_tou);
                }
            }
        });
        login_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (login_but.getText().equals("退出")) {
                    SharedPreferences.Editor edit = sharedPreferences.edit();
                    edit.putBoolean("islogin", false);
                    edit.commit();
                    userstate.setText("未登录");
                    login_but.setText("登录");
                    login_state.setText("未登录");
                } else if (login_but.getText().equals("登录")) {
                    Intent intent = new Intent(getActivity(), LogingActivity.class);
                    intent.putExtra("where","main");
                    startActivity(intent);
                }
            }
        });
        myLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPreferences.getBoolean("islogin", false)) {
                    //初始化定位
                    initLocation();
                    //开始定位
                    startLocation();
                } else {
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //我的地址点击监听
        myAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyUtils.getSharedPreferencesInstance(getActivity()).getBoolean("islogin",false)){
                    Intent intent = new Intent(getActivity(), AddressActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //获取控件的方法
    private void initView(View view) {
        login_tou = (ImageView) view.findViewById(R.id.login_tou);
        myLocation = (ImageView) view.findViewById(R.id.location);
        login_state = (TextView) view.findViewById(R.id.login_state);
        tvResult = (TextView) view.findViewById(R.id.mylocation);
        myAddress = (TextView) view.findViewById(R.id.myaddress);
        mainpage = (ImageView) view.findViewById(R.id.mainpage);
        //得到PopupWindow布局
        pview = View.inflate(getActivity(), R.layout.popupwindow, null);
        //得到布局里的控件
        userstate = (TextView) pview.findViewById(R.id.p_userstate);
        login_but = (Button) pview.findViewById(R.id.unlogin);
    }

    private void initLocation() {
        //初始化client
        locationClient = new AMapLocationClient(getActivity().getApplicationContext());
        locationOption = getDefaultOption();
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        // 设置定位监听
        locationClient.setLocationListener(mLocationListener);
    }

    /**
     * 默认的定位参数
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        return mOption;
    }

    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation location) {
            if (null != location) {

                StringBuffer sb = new StringBuffer();
                //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
                if (location.getErrorCode() == 0) {
                    //sb.append("定位成功" + "\n");
                    //sb.append("定位类型: " + location.getLocationType() + "\n");
                    //sb.append("经    度    : " + location.getLongitude() + "\n");
                    //sb.append("纬    度    : " + location.getLatitude() + "\n");
                    //sb.append("精    度    : " + location.getAccuracy() + "米" + "\n");
                    //sb.append("提供者    : " + location.getProvider() + "\n");

                    //sb.append("速    度    : " + location.getSpeed() + "米/秒" + "\n");
                    //sb.append("角    度    : " + location.getBearing() + "\n");
                    // 获取当前提供定位服务的卫星个数
                    //sb.append("星    数    : " + location.getSatellites() + "\n");
                    //sb.append("国    家    : " + location.getCountry() + "\n");
                    sb.append(location.getProvince());
                    sb.append(location.getCity());
                    //sb.append("城市编码 : " + location.getCityCode() + "\n");
                    sb.append(location.getDistrict());
                    //sb.append("区域 码   : " + location.getAdCode() + "\n");
                    //sb.append(location.getAddress());
                    //sb.append("兴趣点    : " + location.getPoiName() + "\n");
                    //定位完成的时间
                    //sb.append("定位时间: " + Utils.formatUTC(location.getTime(), "yyyy-MM-dd HH:mm:ss") + "\n");
                } else {
                    //定位失败
                    sb.append("定位失败" + "\n");
                    //sb.append("错误码:" + location.getErrorCode() + "\n");
                    //sb.append("错误信息:" + location.getErrorInfo() + "\n");
                    //sb.append("错误描述:" + location.getLocationDetail() + "\n");
                }
                //定位之后的回调时间
                //sb.append("回调时间: " + Utils.formatUTC(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss") + "\n");

                //解析定位结果，
                String result = sb.toString();
                tvResult.setText(result);
            } else {
                tvResult.setText("定位失败，loc is null");
            }
        }
    };

    /**
     * 开始定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    public void startLocation() {
        //根据控件的选择，重新设置定位参数
        //resetOption();
        // 设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
    }
}
