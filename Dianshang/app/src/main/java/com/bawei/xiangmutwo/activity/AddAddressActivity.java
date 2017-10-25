package com.bawei.xiangmutwo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.okhttplibrary.utils.GsonObjectCallback;
import com.bawei.okhttplibrary.utils.OkhttpUtils;
import com.bawei.xiangmutwo.R;
import com.bawei.xiangmutwo.api.Api;
import com.bawei.xiangmutwo.bean.AddAddressBean;
import com.bawei.xiangmutwo.bean.UnregBean;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

public class AddAddressActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_addaddressback;
    private EditText add_true_name,add_mob_phone,add_area_info,add_address;
    private String click;
    private Intent intent;
    private int address_id;
    private TextView tv_tou;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        initView();

        intent = getIntent();
        click = intent.getStringExtra("click");
        //判断如果点击了编辑
        if(click.equals("edit")){
            tv_tou.setText("修改收获地址");
            String name = intent.getStringExtra("true_name");
            String mob_phone = intent.getStringExtra("mob_phone");
            String address = intent.getStringExtra("address");
            String area_info = intent.getStringExtra("area_info");
            add_true_name.setText(name);
            add_mob_phone.setText(mob_phone);
            add_address.setText(address);
            add_area_info.setText(area_info);
        }

    }

    private void initView() {
        iv_addaddressback = (ImageView) findViewById(R.id.iv_addaddressback);
        add_true_name = (EditText) findViewById(R.id.add_true_name);
        add_mob_phone = (EditText) findViewById(R.id.add_mob_phone);
        add_address = (EditText) findViewById(R.id.add_address);
        add_area_info = (EditText) findViewById(R.id.add_area_info);
        tv_tou = (TextView) findViewById(R.id.tv_tou);
        findViewById(R.id.bt_address_submit).setOnClickListener(this);
    }

    //请求数据添加到后台
    private void getAddData(){
        Map<String,String> map = new HashMap<>();
        map.put("key",Api.sharedPreferences.getString("key",""));
        map.put("true_name",add_true_name.getText().toString());
        map.put("mob_phone",add_mob_phone.getText().toString());
        map.put("city_id","12");
        map.put("area_id","33");
        map.put("address",add_address.getText().toString());
        map.put("area_info",add_area_info.getText().toString());
        OkhttpUtils.doPost(map, Api.AddAddressURL, new GsonObjectCallback<AddAddressBean>() {
            @Override
            public void onUi(AddAddressBean addAddressBean) {
                if(addAddressBean.getCode() == 200){
                    Toast.makeText(AddAddressActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                    //添加成功后将地址id保存
                    Api.editor.putInt("address_id",addAddressBean.getDatas().getAddress_id()).commit();
                    AddAddressActivity.this.finish();
                }
            }

            @Override
            public void onFailed(Call call, IOException e) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_address_submit:
                if(click.equals("tianjia")){
                    getAddData();
                }else if(click.equals("edit")){
                    address_id = intent.getIntExtra("addressid",0);
                    getUpdateData();
                }
                break;
        }
    }

    //编辑数据
    private void getUpdateData(){
        Map<String,String> map = new HashMap<>();
        map.put("key",Api.sharedPreferences.getString("key",""));
        map.put("true_name",add_true_name.getText().toString());
        map.put("mob_phone",add_mob_phone.getText().toString());
        map.put("city_id","12");
        map.put("area_id","33");
        map.put("address",add_address.getText().toString());
        map.put("area_info",add_area_info.getText().toString());
        map.put("address_id",address_id+"");
        map.put("is_default","1");
        OkhttpUtils.doPost(map, Api.UpdateAddress, new GsonObjectCallback<UnregBean>() {
            @Override
            public void onUi(UnregBean unregBean) {
                if(unregBean.getCode()==200){
                    Toast.makeText(AddAddressActivity.this,"修改地址成功",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddAddressActivity.this, AddressActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailed(Call call, IOException e) {

            }
        });
    }

}
