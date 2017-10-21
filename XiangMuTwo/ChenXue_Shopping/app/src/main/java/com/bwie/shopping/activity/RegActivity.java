package com.bwie.shopping.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bwie.shopping.R;
import com.bwie.shopping.api.API;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class RegActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView back;
    private EditText regUname,regPassword,regEmail,regIsPassword;
    private Button reg_but;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        //获取控件
        initView();
        back.setOnClickListener(this);
        reg_but.setOnClickListener(this);
    }
    //获取控件的方法
    private void initView() {
        back = (ImageView) findViewById(R.id.reg_back);
        regUname = (EditText) findViewById(R.id.reg_uname);
        regPassword = (EditText) findViewById(R.id.reg_password);
        regIsPassword = (EditText) findViewById(R.id.reg_ispassword);
        reg_but = (Button) findViewById(R.id.reg_but);
        regEmail = (EditText) findViewById(R.id.reg_email);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.reg_back:
                Intent intent = new Intent(this, LogingActivity.class);
                startActivity(intent);
                break;
            case R.id.reg_but:
                //发送post请求
                doPost();
                break;
        }
    }

    //注册用户 发送post请求
    private void doPost() {
        RequestParams params = new RequestParams(API.REG_PATH);
        params.addBodyParameter("username",regUname.getText().toString());
        params.addBodyParameter("password",regPassword.getText().toString());
        params.addBodyParameter("password_confirm",regIsPassword.getText().toString());
        params.addBodyParameter("email",regEmail.getText().toString());
        params.addBodyParameter("client",API.SYSTEM_TYPE);
        params.setMultipart(true);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.optInt("code");
                    if (code == 200){
                        Toast.makeText(RegActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegActivity.this, LogingActivity.class);
                        intent.putExtra("regunmae",regUname.getText().toString());
                        startActivity(intent);
                        //返回登录界面
                        finish();
                    }else{
                        Toast.makeText(RegActivity.this, "注册失败，用户名已存在", Toast.LENGTH_SHORT).show();
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
    }
}
