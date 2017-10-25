package com.bawei.xiangmutwo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bawei.xiangmutwo.R;
import com.bawei.xiangmutwo.api.Api;
import com.bawei.xiangmutwo.bean.LoginBean;
import com.bawei.xiangmutwo.presenter.LoginPresenter;
import com.bawei.xiangmutwo.presenter.LoginPresenterImpl;
import com.bawei.xiangmutwo.view.LoginView;

public class LoginActivity extends BaseActivity implements LoginView, View.OnClickListener {

    private EditText et_loginame,et_loginpwd;
    private LoginPresenter loginPresenter;
    private boolean islogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //初始化组件
        et_loginame = (EditText) findViewById(R.id.et_loginname);
        et_loginpwd = (EditText) findViewById(R.id.et_loginpwd);

        //获取传入的值
        islogin = getIntent().getBooleanExtra("isLogin", false);

        findViewById(R.id.bt_login).setOnClickListener(this);
        findViewById(R.id.iv_loginback).setOnClickListener(this);

        loginPresenter = new LoginPresenterImpl(this);

    }

    //设置监听
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_login:
                loginPresenter.loginPass(LoginActivity.this,et_loginame.getText().toString(),et_loginpwd.getText().toString());
                break;
            case R.id.iv_loginback:
                finish();
                break;
        }
    }

    //重写方法
    @Override
    public void setNameError() {
        et_loginame.setError("name is not null");
    }

    @Override
    public void setPwdError() {
        et_loginpwd.setError("pwd is not null");
    }

    @Override
    public void setLoginSuccess(LoginBean loginBean) {
        Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
        Api.editor.putString("key",loginBean.getDatas().getKey()).commit();
        Api.editor.putString("username",loginBean.getDatas().getUsername()).commit();
        Intent intent = getIntent();
        intent.putExtra("isSuccess",true);
        intent.putExtra("loginBean",loginBean);
        LoginActivity.this.setResult(20,intent);
        LoginActivity.this.finish();
    }

    @Override
    public void setLoginFail() {
        Toast.makeText(LoginActivity.this,"登录失败",Toast.LENGTH_SHORT).show();
    }

}
