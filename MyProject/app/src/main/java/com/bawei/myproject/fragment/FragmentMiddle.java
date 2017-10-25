package com.bawei.myproject.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.bawei.myproject.R;
import com.bawei.myproject.WebViewActivity;
import com.bawei.myproject.adapter.MyAdapter;
import com.bawei.myproject.bean.Bean;
import com.bawei.myproject.utils.StreamTools;
import com.google.gson.Gson;
import com.limxing.xlistview.view.XListView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by dell-pc on 2017/9/12.
 */

public class FragmentMiddle extends Fragment implements XListView.IXListViewListener {

    private XListView xlv;
    private String title;
    private boolean flag;
    public static Bean bean;

    //传值
    public static Fragment newInstance(String title){
        Bundle bundle = new Bundle();
        bundle.putString("title",title);
        FragmentMiddle fragmentMiddle = new FragmentMiddle();
        fragmentMiddle.setArguments(bundle);
        return fragmentMiddle;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //fragment一旦被创建则接受传递的值
        title = getArguments().getString("title");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //加载布局
        View view = inflater.inflate(R.layout.fragmentmiddle, container, false);

        //获取组件
        xlv = view.findViewById(R.id.xlv);
        //设置可下拉刷新
        xlv.setPullLoadEnable(true);
        //设置监听
        xlv.setXListViewListener(this);

        getListData();

        //条目点击监听
        xlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bean.ResultBean.DataBean bean = (Bean.ResultBean.DataBean) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("url",bean.getUrl());
                startActivity(intent);
            }
        });

        return view;
    }

    private void getListData() {
        getData("http://v.juhe.cn/toutiao/index?type="+title+"&key=54e3d5f4ee64f51bef570ce8505d37b5");
    }

    public void getData(String path) {
        new AsyncTask<String, Void, String>() {

            private MyAdapter adapter;

            @Override
            protected void onPostExecute(String s) {
                if(s == null){
                    return;
                }

                Gson gson = new Gson();
                bean = gson.fromJson(s, Bean.class);
                List<Bean.ResultBean.DataBean> data = bean.getResult().getData();

                if(adapter == null){
                    adapter = new MyAdapter(data,getActivity());
                    xlv.setAdapter(adapter);
                }else{
                    adapter.loadMore(data,flag);
                }

            }

            @Override
            protected String doInBackground(String... strings) {

                try {
                    URL url = new URL(strings[0]);
                    //打开连接
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    //设置属性
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);
                    //判断是否响应成功
                    if(connection.getResponseCode() == 200){
                        //获取数据
                        InputStream is = connection.getInputStream();
                        return StreamTools.getStr(is);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }
        }.execute(path);
    }

    //下拉刷新
    @Override
    public void onRefresh() {
        flag = true;
        getListData();
        xlv.stopRefresh(true);
    }

    @Override
    public void onLoadMore() {
        flag = false;
        getListData();
        xlv.stopLoadMore();
    }
}
