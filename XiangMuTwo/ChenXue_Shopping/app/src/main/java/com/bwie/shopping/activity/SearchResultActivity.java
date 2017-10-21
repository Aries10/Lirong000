package com.bwie.shopping.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bwie.shopping.R;
import com.bwie.shopping.adapter.MyAdapter_GoodsList;
import com.bwie.shopping.bean.SearchBean;
import com.bwie.xrecycleview.XRecyclerView;
import com.google.gson.Gson;

public class SearchResultActivity extends AppCompatActivity {
    private RelativeLayout resule_search;
    private TextView tv_result,noGoods;
    private ImageView back;
    private XRecyclerView xRecyclerView;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        //获取控件
        initView();
        //获取搜索结果
        Intent intent = getIntent();
        final String json = intent.getStringExtra("json");
        String edit = intent.getStringExtra("edit");
        tv_result.setText(edit);
        if (json.equals("暂无此商品")){
            xRecyclerView.setVisibility(View.INVISIBLE);
            noGoods.setText(json);
            noGoods.setVisibility(View.VISIBLE);
            xRecyclerView.setVisibility(View.GONE);
        }else{
            //解析json添加适配器
            setXrv(json);
        }
        //返回点击监听
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchResultActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });
        //刷新监听
        xRecyclerView.setPullRefreshEnabled(true);
        xRecyclerView.setLoadingMoreEnabled(true);
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setXrv(json);
                        xRecyclerView.refreshComplete();
                    }
                },2000);
            }

            @Override
            public void onLoadMore() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setXrv(json);
                        xRecyclerView.loadMoreComplete();
                    }
                },2000);
            }
        });
    }

    //获取控件的方法
    private void initView() {
        resule_search = (RelativeLayout) findViewById(R.id.result_search);
        back = (ImageView) findViewById(R.id.result_back);
        xRecyclerView = (XRecyclerView) findViewById(R.id.goods_list);
        tv_result = (TextView) findViewById(R.id.tv_result);
        noGoods = (TextView) findViewById(R.id.nogoods);
    }
    //解析json添加适配器
    private void setXrv(String json) {
        //得到布局管理器
        LinearLayoutManager manager = new LinearLayoutManager(this);
        //设置布局管理器
        xRecyclerView.setLayoutManager(manager);
        //解析json
        Gson gson = new Gson();
        final SearchBean searchBean = gson.fromJson(json, SearchBean.class);
        //添加适配器
        MyAdapter_GoodsList myAdapter_goodsList = new MyAdapter_GoodsList(this, searchBean.getDatas().getGoods_list());
        xRecyclerView.setAdapter(myAdapter_goodsList);
        //item项点击监听
        myAdapter_goodsList.setGoodsListsItemClickListener(new MyAdapter_GoodsList.OnGoodsListsItemClickListener() {
            @Override
            public void goodsListsItemClickListener(int position) {
                Intent intent = new Intent(SearchResultActivity.this, ParticularsActivity.class);
                intent.putExtra("goodsid",searchBean.getDatas().getGoods_list().get(position-1).getGoods_id());
                startActivity(intent);
            }
        });
    }
}
