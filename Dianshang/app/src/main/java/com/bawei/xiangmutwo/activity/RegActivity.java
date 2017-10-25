package com.bawei.xiangmutwo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bawei.xiangmutwo.R;
import com.bawei.xiangmutwo.presenter.RegPresenter;
import com.bawei.xiangmutwo.presenter.RegPresenterImpl;
import com.bawei.xiangmutwo.view.RegView;

public class RegActivity extends BaseActivity implements RegView, View.OnClickListener {

    private EditText et_regname, et_regpwd1, et_regpwd2, et_email;
    private RegPresenter regPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        initView();

    }

    /**
     * 初始化组件
     */
    private void initView() {
        et_regname = (EditText) findViewById(R.id.et_regname);
        et_regpwd1 = (EditText) findViewById(R.id.et_regpwd1);
        et_regpwd2 = (EditText) findViewById(R.id.et_regpwd2);
        et_email = (EditText) findViewById(R.id.et_email);

        //获取p层实例
        regPresenter = new RegPresenterImpl(this);

        findViewById(R.id.bt_reg).setOnClickListener(this);
        findViewById(R.id.iv_regback).setOnClickListener(this);

    }

    //设置监听
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_reg:
                //判断是否验证成功
                regPresenter.regPass(RegActivity.this,
                        et_regname.getText().toString(),
                        et_regpwd1.getText().toString(),
                        et_regpwd2.getText().toString(),
                        et_email.getText().toString());
                break;
            case R.id.iv_regback:
                finish();
                break;
        }
    }
    /**
     * 重写方法
     */
    @Override
    public void setRegNameError() {
        et_regname.setError("用户名不能为空");
    }

    @Override
    public void setRegPwd1Error() {
        et_regpwd1.setError("密码不能为空");
    }

    @Override
    public void setRegPwd2Error() {
        et_regpwd2.setError("密码不能为空");
    }

    @Override
    public void setRegEmailError() {
        et_email.setError("邮箱不能为空");
    }

    @Override
    public void setRegSuccess() {
        Toast.makeText(RegActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(RegActivity.this,LoginRegActivity.class));
    }

    @Override
    public void setRegFail() {
        Toast.makeText(RegActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
    }

}
