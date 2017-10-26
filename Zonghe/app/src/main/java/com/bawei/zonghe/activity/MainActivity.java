package com.bawei.zonghe.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bawei.zonghe.R;
import com.bawei.zonghe.fragment.Fragment1;
import com.hjm.bottomtabbar.BottomTabBar;

public class MainActivity extends AppCompatActivity {

    private BottomTabBar bottomTabBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        bottomTabBar = (BottomTabBar) findViewById(R.id.bottomTabBar);

        //加载Fragment
        getPage();

    }

    /**
     * 加载Fragment
     */
    private void getPage(){
        bottomTabBar.init(getSupportFragmentManager())
                .setImgSize(0, 0)
                .setFontSize(16)
                .setTabPadding(4, 6, 10)
                .setChangeColor(Color.GREEN, Color.RED)
                .addTabItem("主页", R.mipmap.ic_launcher, Fragment1.class)
                .addTabItem("二页", R.mipmap.ic_launcher, Fragment1.class)
                .addTabItem("三页", R.mipmap.ic_launcher, Fragment1.class);
    }

}
