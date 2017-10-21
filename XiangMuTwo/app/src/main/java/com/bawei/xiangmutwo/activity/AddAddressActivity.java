package com.bawei.xiangmutwo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;

import com.bawei.xiangmutwo.R;

public class AddAddressActivity extends AppCompatActivity {

    private ImageView iv_addaddressback;
    private EditText add_city_id,add_true_name,add_mob_phone,add_area_info,add_address,add_area_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        initView();
    }

    private void initView() {
        iv_addaddressback = (ImageView) findViewById(R.id.iv_addaddressback);
        add_true_name = (EditText) findViewById(R.id.add_true_name);
        add_mob_phone = (EditText) findViewById(R.id.add_mob_phone);
        add_city_id = (EditText) findViewById(R.id.add_city_id);
        add_area_id = (EditText) findViewById(R.id.add_area_id);
        add_address = (EditText) findViewById(R.id.add_address);
        add_area_info = (EditText) findViewById(R.id.add_area_info);
    }
}
