package com.bawei.zonghe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bawei.zonghe.R;
import com.bawei.zonghe.adapter.MyDetailsAdapter;
import com.bawei.zonghe.bean.DetailsBean;
import com.bawei.zonghe.presenter.DetailsPresenter;
import com.bawei.zonghe.presenter.DetailsPresenterImpl;
import com.bawei.zonghe.view.DetailsView;

public class DetailsActivity extends AppCompatActivity implements DetailsView{

    private RecyclerView rv_details;
    private int id;
    private DetailsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        //创建presenter实例
        presenter = new DetailsPresenterImpl(this);
        //接收id
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        initView();

    }

    private void initView() {
        rv_details = (RecyclerView) findViewById(R.id.rv_details);
        presenter.pass(DetailsActivity.this,id);
    }

    /**
     * 设置数据
     */
    @Override
    public void setData(DetailsBean bean) {
        rv_details.setLayoutManager(new LinearLayoutManager(DetailsActivity.this));
        MyDetailsAdapter detailsAdapter = new MyDetailsAdapter(DetailsActivity.this,bean.getStories());
        rv_details.setAdapter(detailsAdapter);
    }
}
