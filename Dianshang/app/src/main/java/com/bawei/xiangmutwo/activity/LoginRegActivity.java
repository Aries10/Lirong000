package com.bawei.xiangmutwo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bawei.xiangmutwo.R;
import com.bawei.xiangmutwo.bean.LoginBean;

import org.greenrobot.eventbus.EventBus;

public class LoginRegActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_reg);

        initeView();

    }

    /**
     * 寻找组件
     */
    private void initeView() {
        findViewById(R.id.login).setOnClickListener(this);
        findViewById(R.id.reg).setOnClickListener(this);
        findViewById(R.id.iv_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                Intent intent = new Intent(LoginRegActivity.this, LoginActivity.class);
                startActivityForResult(intent,1);
                break;
            case R.id.reg:
                startActivity(new Intent(LoginRegActivity.this,RegActivity.class));
                break;
            case R.id.iv_back://点击返回 返回至首页
                startActivity(new Intent(LoginRegActivity.this,MainActivity.class));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取传回的值
        boolean isSuccess = data.getBooleanExtra("isSuccess", false);
        LoginBean loginBean = (LoginBean) data.getSerializableExtra("loginBean");
        //如果为true  结束当前Activity
        if(isSuccess == true){
            EventBus.getDefault().post(loginBean);
            LoginRegActivity.this.finish();
        }
    }
}
