package com.bawei.myproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.bawei.myproject.adapter.MyVPAdapter;

import java.util.ArrayList;
import java.util.List;

public class IndexActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager vp;
    //定义存放ViewPager页面的布局的集合
    private List<View> imageList = new ArrayList<>();
    private RadioGroup rg;
    private SharedPreferences preferences;
    private boolean isfirst;
    private RelativeLayout rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        //隐藏标题栏
//        getSupportActionBar().hide();

        //获取SharedPreferences实例
        preferences = getSharedPreferences("first", MODE_PRIVATE);
        isfirst = preferences.getBoolean("isfirst", false);

        //ViewPager加载
        initView();

    }

    /**
     * 初始化组件
     */
    private void initView() {

        vp = (ViewPager) findViewById(R.id.vp);
        rg = (RadioGroup) findViewById(R.id.rg);
        rl = (RelativeLayout) findViewById(R.id.rl);

        //加载布局
        View view1 = View.inflate(this, R.layout.pager01, null);
        View view2 = View.inflate(this, R.layout.pager02, null);
        View view3 = View.inflate(this, R.layout.pager03, null);

        //添加到集合
        imageList.add(view1);
        imageList.add(view2);
        imageList.add(view3);

        //判断是否是第一次加载
        if(isfirst){
            vp.setVisibility(View.GONE);
            rg.setVisibility(View.GONE);
            rl.setBackgroundResource(R.mipmap.index);
            //设置动画
            Animation animation = AnimationUtils.loadAnimation(IndexActivity.this, R.anim.alpha);
            rl.startAnimation(animation);
            //动画结束后  跳转至主页面
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    startActivity(new Intent(IndexActivity.this,MainActivity.class));
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }

        //创建适配器
        vp.setAdapter(new MyVPAdapter(imageList));

        //创建监听的方法
        getJianting();

        //点击进入 跳转
        view3.findViewById(R.id.tv_into).setOnClickListener(this);

    }

    /**
     * 关联ViewPager 和 RadioButton
     */
    public void getJianting() {

        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        rg.check(R.id.rb1);
                        break;
                    case 1:
                        rg.check(R.id.rb2);
                        break;
                    case 2:
                        rg.check(R.id.rb3);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i){
                    case R.id.rb1:
                        vp.setCurrentItem(0);
                        break;
                    case R.id.rb2:
                        vp.setCurrentItem(1);
                        break;
                    case R.id.rb3:
                        vp.setCurrentItem(2);
                        break;
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(IndexActivity.this,MainActivity.class));
        //记为true
        preferences.edit().putBoolean("isfirst", true).commit();
    }
}
