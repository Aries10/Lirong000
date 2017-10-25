package com.bawei.myproject;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.myproject.utils.ClearCache;

import androidkun.com.versionupdatelibrary.entity.VersionUpdateConfig;
import cn.jpush.android.api.JPushInterface;

import static com.bawei.myproject.R.id.iv_setting_back;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private AlertDialog.Builder builder;
    private TextView tv_huancun;
    private ClearCache clearCache;
    private CheckBox cb_tuisong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //初始化组件的方法
        initView();

    }

    private void initView() {
        //获取资源ID

        //设置点击监听
        findViewById(iv_setting_back).setOnClickListener(this);
        findViewById(R.id.ll_banben).setOnClickListener(this);
        findViewById(R.id.ll_clear).setOnClickListener(this);

        //当前缓存
        tv_huancun = (TextView) findViewById(R.id.tv_huancun);
        cb_tuisong = (CheckBox) findViewById(R.id.cb_tuisong);

        cb_tuisong.setOnClickListener(this);

        //实例化清除缓存工具类
        clearCache = new ClearCache();

        //计算当前缓存
        try {
            String size = clearCache.getTotalCacheSize(SettingsActivity.this);
            tv_huancun.setText("当前缓存"+size);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View view) {
        //判断点击的是哪一个按钮
        switch (view.getId()){
            case iv_setting_back:
                finish(); //返回
                break;
            //版本更新
            case R.id.ll_banben:
                setBanben();
                break;
            //清除缓存
            case R.id.ll_clear:
                clearHuan();
                break;
            //推送通知
            case R.id.cb_tuisong:
                tuisong();
                break;
        }
    }

    /**
     * 推送通知
     */
    private void tuisong() {
        SharedPreferences preferences = getSharedPreferences("a", MODE_PRIVATE);
        boolean flag = preferences.getBoolean("flag", false);
        if (flag == true) {
            preferences.edit().putBoolean("flag", false).commit();
            Toast.makeText(this, "接收推送", Toast.LENGTH_SHORT).show();
            JPushInterface.resumePush(getApplicationContext());
        } else {
            preferences.edit().putBoolean("flag", true).commit();
            Toast.makeText(this, "不接收推送", Toast.LENGTH_SHORT).show();
            JPushInterface.stopPush(getApplicationContext());
        }
    }

    /**
     * 清理缓存
     */
    private void clearHuan() {
        clearCache.clearAllCache(SettingsActivity.this);
        String size = null;
        try {
            size = clearCache.getTotalCacheSize(SettingsActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tv_huancun.setText("当前缓存:"+size);
    }

    /**
     * 版本更新的方法
     */
    private void setBanben() {
        //点击弹出对话框
        builder = new AlertDialog.Builder(SettingsActivity.this);
        builder.setTitle("是否进行版本更新?");
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                VersionUpdateConfig.getInstance()//获取配置实例
                        .setContext(SettingsActivity.this)//设置上下文
                        .setDownLoadURL("http://gdown.baidu.com/data/wisegame/65f486476dcc3567/jinritoutiao_634.apk")//设置文件下载链接
                        .setNotificationIconRes(R.mipmap.icon)//设置通知图标
                        .setNotificationTitle("版本更新Demo")//设置通知标题
                        .startDownLoad();//开始下载
            }
        });
        builder.setNegativeButton("否", null);
        builder.create();
        builder.show();
    }
}
