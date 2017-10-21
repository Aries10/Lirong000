package com.bawei.xiangmutwo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bawei.xiangmutwo.R;

public class AddressActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_addressback;
    private TextView tv_add_address;
    private ListView lv_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        initView();
    }

    private void initView() {
        iv_addressback = (ImageView) findViewById(R.id.iv_addressback);
        tv_add_address = (TextView) findViewById(R.id.tv_add_address);
        lv_address = (ListView) findViewById(R.id.lv_address);

        iv_addressback.setOnClickListener(this);
        tv_add_address.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_addressback:
                finish();
                break;
            case R.id.tv_add_address:
                break;
        }
    }
}
