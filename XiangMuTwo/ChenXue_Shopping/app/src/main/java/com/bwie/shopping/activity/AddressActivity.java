package com.bwie.shopping.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.shopping.R;
import com.bwie.shopping.adapter.MyAdapter_Address;
import com.bwie.shopping.api.API;
import com.bwie.shopping.bean.AddressBean;
import com.bwie.shopping.utils.MyUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class AddressActivity extends AppCompatActivity {
    private ListView listView;
    private TextView addAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        //获取控件ID
        initView();
        //获取后台数据
        postServerData();
        //点击添加新地址
        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddressActivity.this, AddAddressActivity.class);
                intent.putExtra("click", "tianjia");
                startActivity(intent);
            }
        });
    }

    //获取控件ID
    private void initView() {
        listView = (ListView) findViewById(R.id.lv_address);
        addAddress = (TextView) findViewById(R.id.addaddress);
    }

    //获取后台数据
    private void postServerData() {
        RequestParams params = new RequestParams(API.ADDRESS_PATH);
        params.addBodyParameter("key", MyUtils.getSharedPreferencesInstance(this).getString("key", ""));
        params.setMultipart(true);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.optInt("code");
                    if (code == 200) {
                        Gson gson = new Gson();
                        AddressBean addressBean = gson.fromJson(result, AddressBean.class);
                        final MyAdapter_Address myAdapter_address = new MyAdapter_Address(AddressActivity.this, addressBean.getDatas().getAddress_list());
                        listView.setAdapter(myAdapter_address);
                        myAdapter_address.setOnChangeDefault(new MyAdapter_Address.onChangeDefault() {
                            @Override
                            public void changeDefault(boolean flag) {
                                if (flag) {
                                    myAdapter_address.notifyDataSetChanged();
                                }
                            }
                        });
                        myAdapter_address.setOnDeleteAddress(new MyAdapter_Address.onDeleteAddress() {
                            @Override
                            public void deleteAddress(String address_id) {
                                //删除地址 重新获取数据
                                postDeleteAddress(address_id);
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    public void postDeleteAddress(String address_id) {
        RequestParams params = new RequestParams(API.DELETE_ADDRESS_PATH);
        params.addBodyParameter("key", MyUtils.getSharedPreferencesInstance(this).getString("key", ""));
        params.addBodyParameter("address_id", address_id);
        params.setMultipart(true);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.optInt("code");
                    if (code == 200) {
                        Toast.makeText(AddressActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                        postServerData();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
