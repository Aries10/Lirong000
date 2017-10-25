package com.bawei.myproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class BetterLoginActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_better_login);

        initView();

    }

    private void initView() {
        //设置监听
        findViewById(R.id.iv_better_back).setOnClickListener(this);
        findViewById(R.id.bt_better_login).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //返回按钮
            case R.id.iv_better_back:
                finish();
                break;
            //点击手机号登录按钮 跳转至手机号登录界面
            case R.id.bt_better_login:
                startActivity(new Intent(BetterLoginActivity.this,MobileLoginActivity.class));
                break;
        }
    }
}
