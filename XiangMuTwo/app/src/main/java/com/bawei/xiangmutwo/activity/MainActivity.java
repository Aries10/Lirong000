package com.bawei.xiangmutwo.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bawei.xiangmutwo.R;
import com.bawei.xiangmutwo.fragment.FragmentIndex;
import com.bawei.xiangmutwo.fragment.FragmentMine;
import com.bawei.xiangmutwo.fragment.FragmentShop;
import com.bawei.xiangmutwo.fragment.FragmentType;

public class MainActivity extends BaseActivity {

    private RadioGroup rg;
    private FragmentManager manager;
    private RadioButton rb1,rb2,rb3,rb4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取Fragment管理者
        manager = getSupportFragmentManager();
        //默认加载第一个页面
        manager.beginTransaction().replace(R.id.fram,new FragmentIndex()).commit();

        initView();

    }

    private void initView() {
        rg = (RadioGroup) findViewById(R.id.rg);
        rb1 = (RadioButton) findViewById(R.id.rb1);
        rb2 = (RadioButton) findViewById(R.id.rb2);
        rb3 = (RadioButton) findViewById(R.id.rb3);
        rb4 = (RadioButton) findViewById(R.id.rb4);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i){
                    case R.id.rb1:
                        getFragment(new FragmentIndex());
                        break;
                    case R.id.rb2:
                        getFragment(new FragmentType());
                        break;
                    case R.id.rb3:
                        getFragment(new FragmentShop());
                        break;
                    case R.id.rb4:
                        getFragment(new FragmentMine());
                        break;
                }
            }
        });

    }

    /**
     * 加载Fragment
     */
    private void getFragment(Fragment fragment){
        manager.beginTransaction().replace(R.id.fram,fragment).commit();
    }

}
