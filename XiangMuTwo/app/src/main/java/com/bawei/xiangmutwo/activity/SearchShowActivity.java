package com.bawei.xiangmutwo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bawei.xiangmutwo.R;
import com.bawei.xiangmutwo.adapter.MyAdapter_SearchShow;
import com.bawei.xiangmutwo.bean.SearchBean;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.List;

public class SearchShowActivity extends AppCompatActivity implements View.OnClickListener/*, PullLoadMoreRecyclerView.PullLoadMoreListener*/ {

    private ImageView iv_searchshowback;
    private EditText et_searchshow;
    private Button bt_searchshow;
    private PullLoadMoreRecyclerView rv_searchshow;
    private SearchBean searchbean;
    private MyAdapter_SearchShow searchShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_show);

        initView();

        //获取传入的值
        final Intent intent = getIntent();
        //获取值
        searchbean = (SearchBean) intent.getSerializableExtra("searchbean");
        String s = intent.getStringExtra("s");
        et_searchshow.setText(s);

        getSearchData();
        bt_searchshow.setOnClickListener(this);
        searchShow.setOnRecycler(new MyAdapter_SearchShow.OnRecycler() {
            @Override
            public void onrecyclerview(int position) {
                Intent i = new Intent(SearchShowActivity.this,DetailsActivity.class);
                i.putExtra("goods_id",searchbean.getDatas().getGoods_list().get(position).getGoods_id());
                startActivity(i);
            }
        });

    }

    private void initView() {
        iv_searchshowback = (ImageView) findViewById(R.id.iv_searchshowback);
        et_searchshow = (EditText) findViewById(R.id.et_searchshow);
        bt_searchshow = (Button) findViewById(R.id.bt_searchshow);
        rv_searchshow = (PullLoadMoreRecyclerView) findViewById(R.id.rv_searchshow);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_searchshowback:
                finish();
                break;
        }
    }

    private void getSearchData() {
        rv_searchshow.setLinearLayout();
        List<SearchBean.DatasBean.GoodsListBean> goods_list = searchbean.getDatas().getGoods_list();
        if(searchShow == null){
            searchShow = new MyAdapter_SearchShow(SearchShowActivity.this,goods_list);
            rv_searchshow.setAdapter(searchShow);
        }
    }
}
