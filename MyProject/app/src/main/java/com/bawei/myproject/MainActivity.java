package com.bawei.myproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.andy.library.ChannelActivity;
import com.andy.library.ChannelBean;
import com.bawei.myproject.adapter.MyFragmentAdapter;
import com.bawei.myproject.adapter.MyLeftListViewAdapter;
import com.bawei.myproject.bean.ListBean;
import com.bawei.myproject.utils.BaseUiListener;
import com.bawei.myproject.utils.FinishLogin;
import com.bawei.myproject.utils.NetUtils;
import com.bawei.myproject.utils.Pindao;
import com.example.city_picker.CityListActivity;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.Tencent;

import java.util.ArrayList;
import java.util.List;

/**
 * 类的用途：进入首页面后 显示加载数据 及相关布局
 * create by 李蓉 on 2017/9/12.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TabLayout tabLayout;
    private SlidingMenu slidingMenu;
    private ImageView img_touxiang, img_qq, img_jia, img_phone,img_sousuo;
    private String[] titles = new String[]{"推荐", "国内", "国际", "社会", "娱乐", "体育", "军事", "科技", "财经", "时尚"};
    private String[] mTitles = new String[]{"top","guonei","guoji","shehui","yule","tiyu","junshi","keji","caijing","shishang"};
    private ViewPager viewPager;
    //创建存放侧滑listview内容的集合
    private List<ListBean> listBeen = new ArrayList<>();

    //频道管理
    private List<ChannelBean> list;
    private String jsonStr;

    // 默认是日间模式
    private int theme = R.style.AppTheme;

    //qq登录
    private static final String APP_ID = "1105602574";//官方获取的APPID
    private Tencent mTencent;
    private UserInfo mUserInfo;
    private BaseUiListener mIUiListener;
    private TextView tv_headname, tv_headgender;
    private ImageView ll_img;
    private View listv;

    private boolean isExit;//定义变量用于标示是否退出
    //根据exit()方法中的的消息，写一个Handler
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit=false;
        }
    };
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 判断是否有主题存储
        if(savedInstanceState != null){
            theme = savedInstanceState.getInt("theme");
            setTheme(theme);
        }

        setContentView(R.layout.activity_main);

        //判断是否有网
        boolean b=chexkNet();
        if(!b){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setMessage("网络没有连接");
            builder.setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent=new Intent("android.settings.WIRELESS_SETTINGS");
                    startActivity(intent);
                    return;
                }
            });
            builder.setNegativeButton("取消",null);
            builder.create();
            builder.show();
        }
        else{
            //有网时判断网络类型
            int netWrokState = NetUtils.getNetWrokState(MainActivity.this);
            if(netWrokState == 1){
                Toast.makeText(MainActivity.this,"WIFI连接",Toast.LENGTH_SHORT).show();
            }else if(netWrokState == 2){
                Toast.makeText(MainActivity.this,"当前网络2G",Toast.LENGTH_SHORT).show();
            }else if(netWrokState == 3){
                Toast.makeText(MainActivity.this,"当前网络3G",Toast.LENGTH_SHORT).show();
            }else if(netWrokState == 4){
                Toast.makeText(MainActivity.this,"当前网络4G",Toast.LENGTH_SHORT).show();
            }else if(netWrokState == -2){
                Toast.makeText(MainActivity.this,"没有网络连接",Toast.LENGTH_SHORT).show();
            }
            //传入参数APPID和全局Context上下文
            mTencent = Tencent.createInstance(APP_ID, MainActivity.this.getApplicationContext());

            //存频道管理数据
            preferences = getSharedPreferences("pindao", MODE_PRIVATE);

            initView();
        }

    }

    /**
     * 初始化组件
     */
    private void initView() {

        //获取资源ID
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        img_touxiang = (ImageView) findViewById(R.id.img_touxiang);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        img_jia = (ImageView) findViewById(R.id.img_jia);
        img_sousuo = (ImageView) findViewById(R.id.img_sousuo);
        /**
         * 侧滑页面的布局设置方法
         */
        getSlidemenu();

        //为头像设置点击监听
        img_touxiang.setOnClickListener(this);
        //为搜索按钮设置监听  频道管理
        img_jia.setOnClickListener(this);

        //为TabLayout设置标签
        for (String str : titles) {
            tabLayout.addTab(tabLayout.newTab().setText(str));
        }

        /**
         * 解决ViewPager与SlideMenu冲突问题
         */
        slidingMenu.addIgnoredView(viewPager);
        //关联适配器
        viewPager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager(), titles,mTitles));
        tabLayout.setupWithViewPager(viewPager);

        //点击三点 弹出popupwindow
        img_sousuo.setOnClickListener(this);

    }

    /**
     * 侧滑页面的布局listview方法
     */
    private void getSlidemenu() {
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(slidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setBehindOffset(170);
        slidingMenu.setFadeDegree(1f);
        slidingMenu.attachToActivity(this, slidingMenu.SLIDING_CONTENT);
        //加载布局
        listv = View.inflate(MainActivity.this, R.layout.leftitem, null);

        //隐藏布局的id
        tv_headname = listv.findViewById(R.id.tv_headname);
        tv_headgender = listv.findViewById(R.id.tv_headgender);
        ll_img = listv.findViewById(R.id.ll_img);

        //获取ID
        ListView lv = listv.findViewById(R.id.lv);
        img_qq = listv.findViewById(R.id.img_qq);
        img_phone = listv.findViewById(R.id.img_phone);
        TextView tv_better = listv.findViewById(R.id.tv_better);


        final WindowManager windowManager = getWindowManager();
        //为头像设置监听  点击登录后的头像  弹出退出登录的弹窗
        ll_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            FinishLogin.showDialog(MainActivity.this,windowManager,slidingMenu,img_touxiang);
            }
        });

        /**
         * 点击手机 跳转至手机登录页面
         */
        img_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MobileLoginActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        //点击tv_better更多登录模式 跳转至下一页面
        tv_better.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, BetterLoginActivity.class));
            }
        });

        img_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mIUiListener = new BaseUiListener(mTencent, mUserInfo, MainActivity.this, img_touxiang, listv);
                //all表示获取所有权限
                mTencent.login(MainActivity.this, "all", mIUiListener);
            }
        });

        //将要显示的listview数据添加到集合中
        listBeen.add(new ListBean("好友动态", R.mipmap.l3));
        listBeen.add(new ListBean("我的话题", R.mipmap.l6));
        listBeen.add(new ListBean("收藏", R.mipmap.l2));
        listBeen.add(new ListBean("活动", R.mipmap.l1));
        listBeen.add(new ListBean("商城", R.mipmap.l5));
        listBeen.add(new ListBean("反馈", R.mipmap.l4));
        //创建适配器
        MyLeftListViewAdapter myLeftListViewAdapter = new MyLeftListViewAdapter(listBeen, MainActivity.this);
        //关联数据
        lv.setAdapter(myLeftListViewAdapter);

        slidingMenu.setMenu(listv);
    }

    /**
     * 日夜间模式切换
     * @param view
     */
    public void setMoshi(View view){
        theme = (theme == R.style.AppTheme) ? R.style.NightAppTheme : R.style.AppTheme;
        MainActivity.this.recreate();
    }

    /**
     * 点击设置 跳转至设置页面
     */
    public void setSettings(View view) {
        startActivity(new Intent(MainActivity.this, SettingsActivity.class));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_touxiang:
                //点击头像 拖拉抽屉
                slidingMenu.toggle();
                break;
            case R.id.img_jia:
                //点击频道管理
                Pindao pindao = new Pindao(list, MainActivity.this,jsonStr);
                pindao.getPindaoData();
                break;
            case R.id.img_sousuo:
                CityListActivity.startCityActivityForResult(MainActivity.this);
                break;
        }
    }

    /**
     * 在调用Login的Activity或者Fragment中重写onActivityResult方法
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //第三方qq
        if (requestCode == Constants.REQUEST_LOGIN) {
            Tencent.onActivityResultData(requestCode, resultCode, data, mIUiListener);
        }
        //频道管理
        if (requestCode == ChannelActivity.REQUEST_CODE && resultCode == ChannelActivity.RESULT_CODE) {
            jsonStr = data.getStringExtra(ChannelActivity.RESULT_JSON_KEY);
        }
        //手机登录后显示资料信息
        if (resultCode == 10) {
            slidingMenu.findViewById(R.id.ll_head01).setVisibility(View.GONE);
            slidingMenu.findViewById(R.id.ll_head02).setVisibility(View.VISIBLE);
            slidingMenu.findViewById(R.id.ll_head02).setBackgroundColor(Color.YELLOW);
            int touxiang = data.getIntExtra("touxiang", R.id.img_touxiang);
            String mobile_name = data.getStringExtra("mobile_name");
            String mobile_gender = data.getStringExtra("mobile_gender");
            tv_headname.setText("姓名:" + mobile_name);
            tv_headgender.setText("性别:" + mobile_gender);
            img_touxiang.setImageResource(touxiang);
            ll_img.setImageResource(touxiang);
//            }
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    //重写Activity中onKeyDown方法

    /**
     * onKeyDown 用来捕捉键盘按下的事件
     * @param keyCode  键盘码 手机键盘中每个按钮都有一个键盘码 通过键盘码确定是哪个按键
     * @param event  该参数为按键事件的对象 包含了触发事件的详细信息 可判断事件类型
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            exit();
            return  false;
        }else {
            return  super.onKeyDown(keyCode,event);
        }

    }

    //写一个退出方法
    public void exit(){
        if(!isExit){
            isExit=true;
            Toast.makeText(this,"再按一次退出程序",Toast.LENGTH_SHORT).show();
            mHandler.sendEmptyMessageDelayed(0,2000);
        }else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            System.exit(0);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("theme", theme);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        theme = savedInstanceState.getInt("theme");
    }

    /**
     * 是否有网
     * @return
     */
    private boolean chexkNet(){
        ConnectivityManager conn= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conn.getActiveNetworkInfo();
        if(info!=null&&info.isConnected()){
            return true;
        }
        return false;
    }

}
