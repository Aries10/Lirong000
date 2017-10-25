package com.bawei.xiangmutwo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bawei.okhttplibrary.utils.GsonObjectCallback;
import com.bawei.okhttplibrary.utils.OkhttpUtils;
import com.bawei.xiangmutwo.R;
import com.bawei.xiangmutwo.adapter.MyAddressAdapter;
import com.bawei.xiangmutwo.api.Api;
import com.bawei.xiangmutwo.bean.AddressBean;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

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

        OkhttpUtils.doGet(Api.AddressURL + "&key=" + Api.sharedPreferences.getString("key", ""), new GsonObjectCallback<AddressBean>() {
            @Override
            public void onUi(AddressBean addressBean) {
                List<AddressBean.DatasBean.AddressListBean> address_list = addressBean.getDatas().getAddress_list();
                MyAddressAdapter myAddressAdapter = new MyAddressAdapter(AddressActivity.this,address_list);
                lv_address.setAdapter(myAddressAdapter);
            }

            @Override
            public void onFailed(Call call, IOException e) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_addressback:
                finish();
                break;
            case R.id.tv_add_address:
                Intent intent = new Intent(AddressActivity.this, AddAddressActivity.class);
                intent.putExtra("click", "tianjia");
                startActivity(intent);
                break;
        }
    }
}
