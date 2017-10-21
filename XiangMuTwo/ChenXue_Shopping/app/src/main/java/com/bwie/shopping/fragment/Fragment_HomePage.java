package com.bwie.shopping.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.shopping.R;
import com.bwie.shopping.activity.SearchActivity;
import com.bwie.shopping.adapter.MyAdapter_HomePage;
import com.bwie.shopping.api.API;
import com.bwie.shopping.app.MyApplication;
import com.bwie.shopping.bean.HomeBean;
import com.bwie.shopping.bean.HomePageBean;
import com.bwie.shopping.bean.User;
import com.bwie.shopping.greendao.UserDao;
import com.bwie.shopping.utils.GetServerData;
import com.google.gson.Gson;
import com.google.zxing.WeChatCaptureActivity;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.QueryBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 1:类的用途
 * 2：@author Dell
 * 3：@date 2017/8/31
 */

public class Fragment_HomePage extends Fragment {
    private ImageView sao;
    private TextView search_content;
    private RelativeLayout search;
    private WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;
    private RecyclerView recyclerView;
    private Handler handler = new Handler();
    private UserDao userDao;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homepage, container, false);
        //获取控件
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //得到数据库
        userDao = MyApplication.getDaoMaster(getActivity()).newSession().getUserDao();
        //初始化RecycleView
        initRecycleView();
        //判断网络
        ConnectivityManager mConnectivityManager = (ConnectivityManager) getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo == null) {//没有有网络
            Toast.makeText(getActivity(), "网络不可用", Toast.LENGTH_SHORT).show();
            //查询数据库
            QueryBuilder<User> qb = userDao.queryBuilder();
            List<User> list = qb.list();
            //解析数据库查询到的json 进行展示
            User user = list.get(0);
            Gson gson = new Gson();
            HomeBean homeBean = gson.fromJson(user.getJson(), HomeBean.class);
            HomeBean.DataBean data = homeBean.getData();
            MyAdapter_HomePage myAdapter_homePage = new MyAdapter_HomePage(data, getActivity());
            recyclerView.setAdapter(myAdapter_homePage);
        } else {//有网
            getServerDate();
        }
        //刷新监听
        mWaveSwipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getServerDate();
                        mWaveSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });
        //点击搜索监听
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                CharSequence text = search_content.getText();
                intent.putExtra("search_content", text);
                startActivity(intent);
            }
        });
        //扫描二维码点击监听
        sao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WeChatCaptureActivity.class);
                startActivity(intent);
            }
        });
    }

    //获取控件的方法
    private void initView(View view) {
        sao = (ImageView) view.findViewById(R.id.sao);
        search_content = (TextView) view.findViewById(R.id.serch_content);
        search = (RelativeLayout) view.findViewById(R.id.search);
        recyclerView = (RecyclerView) view.findViewById(R.id.homepage_rv);
        mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) view.findViewById(R.id.waveSwipeRefresh);
        mWaveSwipeRefreshLayout.setColorSchemeColors(Color.WHITE, Color.WHITE);
        mWaveSwipeRefreshLayout.setWaveColor(Color.argb(200, 255, 0, 0));
    }

    //获取网络数据
    private void getServerDate() {
//        GetServerData.getServerData(getActivity(), API.HOME_PAGE_PATH, new GetServerData.OnGetServerDateLisnter() {
//            @Override
//            public void getData(String string) {
//                Log.e("++++++++++", string + "++++++++");
//                Gson gson = new Gson();
//                HomeBean homeBean = gson.fromJson(string, HomeBean.class);
//                HomeBean.DataBean data = homeBean.getData();
//                MyAdapter_HomePage myAdapter_homePage = new MyAdapter_HomePage(data, getActivity());
//                recyclerView.setAdapter(myAdapter_homePage);
//            }
//        });
        GetServerData.doGet(API.HOME_PAGE_PATH, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String string = response.body().string();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //清除数据库
                            userDao.deleteAll();
                            //将请求到的数据加入数据库
                            userDao.insert(new User((long) 1, string));
                            //解析请求到的json
                            Gson gson = new Gson();
                            Log.e("++++++++++", string + "++++++++");
                            HomeBean homeBean = gson.fromJson(string, HomeBean.class);
                            HomeBean.DataBean data = homeBean.getData();
                            MyAdapter_HomePage myAdapter_homePage = new MyAdapter_HomePage(data, getActivity());
                            recyclerView.setAdapter(myAdapter_homePage);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    //初始化RecycleView
    public void initRecycleView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        //设置item间隔为16
        SpacesItemDecoration spacesItemDecoration = new SpacesItemDecoration(16);
        recyclerView.addItemDecoration(spacesItemDecoration);
    }

    //设置间隔,自定义了一个SpacesItemDecoration
    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.top = space;
            }
        }
    }
}