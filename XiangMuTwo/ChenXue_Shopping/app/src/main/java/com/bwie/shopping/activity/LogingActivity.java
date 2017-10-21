package com.bwie.shopping.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.shopping.R;
import com.bwie.shopping.fragment.Fragment_Main;
import com.bwie.shopping.presenter.LoginPresenter;
import com.bwie.shopping.view.LoginView;

public class LogingActivity extends AppCompatActivity implements LoginView, View.OnClickListener {
    private ImageView back;
    private EditText uName, password;
    private TextView reg;
    private Button login;
    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loging);
        //获取控件
        initView();
        //实例p
        presenter = new LoginPresenter(this);
        login.setOnClickListener(this);
        back.setOnClickListener(this);
        reg.setOnClickListener(this);
        //将传过来的值赋给输入框
        Intent intent = getIntent();
        String regunmae = intent.getStringExtra("regunmae");
        setUname(regunmae);
    }

    //获取控件的方法
    private void initView() {
        back = (ImageView) findViewById(R.id.loging_back);
        uName = (EditText) findViewById(R.id.uname);
        password = (EditText) findViewById(R.id.password);
        reg = (TextView) findViewById(R.id.reg);
        login = (Button) findViewById(R.id.loging_but);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loging_but:
                int code = presenter.login(this, getUname(), getPassWord());
                if (code == 200) {
                    Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                    Intent intent = getIntent();
                    String where = intent.getStringExtra("where");
                    if (where.equals("cart")) {
                        Intent intentCart = new Intent(this, ParticularsActivity.class);
                        String goodsid = intent.getStringExtra("goodsid");
                        intentCart.putExtra("goodsid", goodsid);
                        startActivity(intentCart);
                        finish();
                    } else if (where.equals("main")) {
                        finish();
                    } else if (where.equals("carts")) {
                        finish();
                    }
                }
                break;
            case R.id.loging_back:
                Intent intent = getIntent();
                String where = intent.getStringExtra("where");
                if (where.equals("carts")) {
                    Intent intent2 = new Intent(this, MainActivity.class);
                    intent2.putExtra("homepage","homepage");
                    startActivity(intent2);
                } else {
                    finish();
                }
                break;
            case R.id.reg:
                Intent intent3 = new Intent(this, RegActivity.class);
                startActivity(intent3);
                break;
        }
    }

    @Override
    public void setUname(String uname) {
        uName.setText(uname);
    }

    @Override
    public void setPassWord(String passWord) {
        password.setText(passWord);
    }

    @Override
    public String getUname() {
        return uName.getText().toString();
    }

    @Override
    public String getPassWord() {
        return password.getText().toString();
    }
}
