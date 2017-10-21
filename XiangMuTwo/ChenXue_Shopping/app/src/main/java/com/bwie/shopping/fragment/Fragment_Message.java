package com.bwie.shopping.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bwie.shopping.R;
import com.bwie.shopping.adapter.MyAdapter_Type;
import com.bwie.shopping.adapter.MyAdapter_TypeSon;
import com.bwie.shopping.api.API;
import com.bwie.shopping.bean.TypeBean;
import com.bwie.shopping.bean.TypeSonBean;
import com.bwie.shopping.utils.GetServerData;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 1:类的用途
 * 2：@author Dell
 * 3：@date 2017/8/31
 */

public class Fragment_Message extends Fragment {
    private RecyclerView rv_left, rv_right;
    private MyAdapter_Type myAdapter_type;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        //获取控件
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        //得到WindowManager
        WindowManager windowManager = getActivity().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        //得到屏幕宽
        int width = display.getWidth();
        //将RecyclerView宽设置为屏幕宽的1/5
        params.width = width * 1 / 5;
        rv_left.setLayoutParams(params);
        //得到RecyclerView布局管理器
        LinearLayoutManager leftLayoutManager = new LinearLayoutManager(getActivity());
        //RecyclerView设置布局管理器
        rv_left.setLayoutManager(leftLayoutManager);
        //得到RecyclerView布局管理器
        LinearLayoutManager rightLayoutManager = new LinearLayoutManager(getActivity());
        //RecyclerView设置布局管理器
        rv_right.setLayoutManager(rightLayoutManager);
        //获取后台数据，添加适配器
        getServerData();
    }

    //获取控件的方法
    private void initView(View view) {
        rv_left = (RecyclerView) view.findViewById(R.id.type_rvleft);
        rv_right = (RecyclerView) view.findViewById(R.id.type_rvright);
        progressBar = (ProgressBar) view.findViewById(R.id.pbar);
    }

    //获取后台数据的方法
    public void getServerData() {
        GetServerData.doGet(API.TYPE_PATH, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "服务器飞走了", Toast.LENGTH_SHORT).show();
                        return;
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //后台获取的json串
                final String json = response.body().string();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        //解析json
                        Gson gson = new Gson();
                        final TypeBean typeBean = gson.fromJson(json, TypeBean.class);
                        //添加适配器
                        myAdapter_type = new MyAdapter_Type(getActivity(), typeBean.getDatas().getClass_list());
                        rv_left.setAdapter(myAdapter_type);
                        //第一个子条目显示其二级数据
                        getServerTypeData("1",0);
                        //子条目点击监听
                        myAdapter_type.setRecycleViewItemClickListener(new MyAdapter_Type.OnRecycleViewItemClickListener() {
                            @Override
                            public void recycleViewItemClickListener(int position, View view, RecyclerView.ViewHolder viewHolder) {
                                myAdapter_type.setTagPosition(position);
                                myAdapter_type.notifyDataSetChanged();
                                //请求二级数据
                                getServerTypeData(typeBean.getDatas().getClass_list().get(position).getGc_id(),position);
                            }
                        });
                    }
                });
            }
        });
    }

    //请求二级数据
    public void getServerTypeData(final String gc_id, final int position) {
        GetServerData.doGet(API.TYPE_PATH + "&gc_id=" + gc_id, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String json = response.body().string();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson = new Gson();
                        TypeSonBean typeSonBean = gson.fromJson(json, TypeSonBean.class);
                        MyAdapter_TypeSon myAdapter_typeSon = new MyAdapter_TypeSon(getActivity(), typeSonBean.getDatas().getClass_list());
                        rv_right.setAdapter(myAdapter_typeSon);
                    }
                });
            }
        });
    }
}
