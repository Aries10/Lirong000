package com.bawei.zonghe.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bawei.zonghe.R;
import com.bawei.zonghe.adapter.MyNewAdapter;
import com.bawei.zonghe.api.Api;
import com.bawei.zonghe.bean.NewBean;
import com.bwei.okhttp3ps.utils.GsonObjectCallback;
import com.bwei.okhttp3ps.utils.OkHttp3Utils;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

/**
 * Created by dell-pc on 2017/10/25.
 */

public class FragmentNew extends Fragment {
    private RecyclerView rv_new;
    private SwipeRefreshLayout swipeRefresh;
    private MyNewAdapter myNewAdapter;
    private LinearLayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmenttablayout, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    private void initView(View view) {
        rv_new = (RecyclerView) view.findViewById(R.id.rv_new);
        swipeRefresh = view.findViewById(R.id.swipeRefresh);

    }

    private void getData() {
        layoutManager = new LinearLayoutManager(getActivity());
        rv_new.setLayoutManager(layoutManager);
        OkHttp3Utils.doGet(Api.NEWURL, new GsonObjectCallback<NewBean>() {
            @Override
            public void onUi(NewBean newBean) {
                if(myNewAdapter == null) {
                    //将数据传入适配器
                    myNewAdapter = new MyNewAdapter(getActivity(), newBean.getStories(), newBean.getTop_stories());
                    rv_new.setAdapter(myNewAdapter);
                }else{
                    myNewAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailed(Call call, IOException e) {

            }
        });

        //下拉刷新
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                OkHttp3Utils.doGet(Api.RefreshURL, new GsonObjectCallback<NewBean>() {
                    @Override
                    public void onUi(NewBean refreshBean) {
                        List<NewBean.StoriesBean> stories = refreshBean.getStories();
                        myNewAdapter.refreshMore(stories);
                    }

                    @Override
                    public void onFailed(Call call, IOException e) {

                    }
                });
                //如果正在刷新  完成后设置为false
                if(swipeRefresh.isRefreshing()){
                    swipeRefresh.setRefreshing(false);
                }
            }
        });

        //给RecycleView天机滚动监听
        rv_new.addOnScrollListener(new RecyclerView.OnScrollListener(){
            private int lastPOsition;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if(lastPOsition+1==myNewAdapter.getItemCount()&&newState==RecyclerView.SCROLL_STATE_IDLE){
                    OkHttp3Utils.doGet(Api.RefreshURL, new GsonObjectCallback<NewBean>() {
                        @Override
                        public void onUi(NewBean newsBean) {
                            List<NewBean.StoriesBean> stories = newsBean.getStories();
                            myNewAdapter.loadMore(stories);
                        }
                        @Override
                        public void onFailed(Call call, IOException e) {
                        }
                    });
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastPOsition = layoutManager.findLastVisibleItemPosition();
            }
        });
    }
}
