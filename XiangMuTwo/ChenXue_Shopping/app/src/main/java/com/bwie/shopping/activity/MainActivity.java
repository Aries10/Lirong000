package com.bwie.shopping.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bwie.shopping.R;
import com.bwie.shopping.fragment.Fragment_Cart;
import com.bwie.shopping.fragment.Fragment_HomePage;
import com.bwie.shopping.fragment.Fragment_Main;
import com.bwie.shopping.fragment.Fragment_Message;

public class MainActivity extends CheckPermissionsActivity {
    private RadioGroup radioGroup;
    private RadioButton but_homePage;
    private ImageView launch;
    private FragmentManager fragmentManager;
    private Fragment_HomePage homePage;
    private Fragment_Main main;
    private Fragment_Cart cart;
    private Fragment_Message message;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取控件
        initView();
        Intent intent = getIntent();
        String homepage = intent.getStringExtra("homepage");
        if (homepage == null) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    launch.setVisibility(View.GONE);
                }
            }, 3000);
        }else {
            //从购物车登录界面跳转过来不显示导航页直接进入
            launch.setVisibility(View.GONE);
        }
        //得到Fragment管理者
        fragmentManager = getSupportFragmentManager();
        //判断首页是否被选择
        if (but_homePage.isChecked()) {
            //如果被选中  添加fragment
            //由fragmentManager得到事务
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            homePage = new Fragment_HomePage();
            transaction.add(R.id.flayout, homePage);
            transaction.commit();
        }
        //radioGroup监听事件
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                hideFragment();
                switch (checkedId) {
                    case R.id.homepage:
                        //由fragmentManager得到事务
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        if (homePage == null) {
                            homePage = new Fragment_HomePage();
                            transaction.add(R.id.flayout, homePage);
                        } else {
                            transaction.show(homePage);
                        }
                        transaction.commit();
                        break;
                    case R.id.message:
                        //由fragmentManager得到事务
                        FragmentTransaction transaction3 = fragmentManager.beginTransaction();
                        if (message == null) {
                            message = new Fragment_Message();
                            transaction3.add(R.id.flayout, message);
                        } else {
                            transaction3.show(message);
                        }
                        transaction3.commit();
                        break;
                    case R.id.shoppingcart:
                        //由fragmentManager得到事务
                        FragmentTransaction transaction4 = fragmentManager.beginTransaction();
                        if (cart == null) {
                            cart = new Fragment_Cart();
                            transaction4.add(R.id.flayout, cart);
                        } else {
                            transaction4.show(cart);
                        }
                        transaction4.commit();
                        break;
                    case R.id.main:
                        //由fragmentManager得到事务
                        FragmentTransaction transaction5 = fragmentManager.beginTransaction();
                        if (main == null) {
                            main = new Fragment_Main();
                            transaction5.add(R.id.flayout, main);
                        } else {
                            transaction5.show(main);
                        }
                        transaction5.commit();
                        break;
                }
            }
        });
    }

    //获取控件的方法
    private void initView() {
        radioGroup = (RadioGroup) findViewById(R.id.group);
        but_homePage = (RadioButton) findViewById(R.id.homepage);
        launch = (ImageView) findViewById(R.id.launch);
    }

    //隐藏fragment的方法
    public void hideFragment() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (homePage != null) {
            transaction.hide(homePage);
        }
        if (message != null) {
            transaction.hide(message);
        }
        if (cart != null) {
            transaction.hide(cart);
        }
        if (main != null) {
            transaction.hide(main);
        }
        transaction.commit();
    }
}
