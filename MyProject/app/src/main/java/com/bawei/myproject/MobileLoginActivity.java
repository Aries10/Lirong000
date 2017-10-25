package com.bawei.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mob.MobSDK;

import org.json.JSONException;
import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class MobileLoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_phone,et_yanzhengma;
    private String phone;
    private EventHandler handler;
    private int a = 60;
    //创建Handler
    private Handler hand = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            bt_yanzhengma.setText("重新发送("+a+"秒)");
            if(what == 0){
                bt_yanzhengma.setText("发送验证码");
            }
        }
    };
    private Button bt_yanzhengma,bt_mobile_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_login);

        //初始化SDK
        MobSDK.init(this, "20a3ca0cdbe86","c8c710c5bf9631d8a6ec7578283a3256");

        initView();

    }

    /**
     * 获取输入手机号的输入框
     */
    private void initView() {

        //获取资源ID
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_yanzhengma = (EditText) findViewById(R.id.et_yanzhengma);
        bt_yanzhengma = (Button) findViewById(R.id.bt_yanzhengma);
        bt_mobile_login = (Button) findViewById(R.id.bt_mobile_login);

        //返回
        ImageView iv_mobile_back = (ImageView) findViewById(R.id.iv_mobile_back);

        //设置监听
        bt_yanzhengma.setOnClickListener(this);
        bt_mobile_login.setOnClickListener(this);
        iv_mobile_back.setOnClickListener(this);

        handler = new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE){
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MobileLoginActivity.this,"验证成功",Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(MobileLoginActivity.this,MainActivity.class);
                                intent.putExtra("touxiang",R.mipmap.mobile_img);
                                intent.putExtra("mobile_name","李蓉");
                                intent.putExtra("mobile_gender","女");
                                MobileLoginActivity.this.setResult(10, intent);
                                finish();
                            }
                        });

                    }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                        //获取验证码成功
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MobileLoginActivity.this,"验证码已发送",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }else{
                    ((Throwable)data).printStackTrace();
                    Throwable throwable = (Throwable) data;
                    try {
                        JSONObject obj = new JSONObject(throwable.getMessage());
                        final String des = obj.optString("detail");
                        if (!TextUtils.isEmpty(des)){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MobileLoginActivity.this,"提交错误信息",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        };

        SMSSDK.registerEventHandler(handler);

    }

    @Override
    public void onClick(View view) {

        //判断按钮
        switch (view.getId()){
            case R.id.iv_mobile_back:
                finish(); //返回按钮
                break;
            case R.id.bt_yanzhengma:
                //获取验证码
                phone = et_phone.getText().toString().trim();
                SMSSDK.getVerificationCode("86", phone);
                //点击后 倒计时
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(a>0){
                            try {
                                Thread.sleep(1000);
                                a--;
                                hand.sendEmptyMessage(a);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
                break;
            case R.id.bt_mobile_login:
                String number = et_yanzhengma.getText().toString();
                SMSSDK.submitVerificationCode("86", phone,number);
                break;
        }

    }

    /**
     * 提交了handler对象所以不用的时候一定要记得销毁，不然一定会内存泄漏
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(handler);
    }
}
