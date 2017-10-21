package com.bwie.shopping.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bwie.shopping.R;
import com.bwie.shopping.api.API;
import com.bwie.shopping.utils.MyUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class AddAddressActivity extends AppCompatActivity {
    private EditText name, phone, address, addressInfo;
    private Button ok;
    private String click;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        //获取控件
        initView();
        //得到传过来的值
        intent = getIntent();
        click = intent.getStringExtra("click");
        if (click.equals("bianji")) {
            String true_name = intent.getStringExtra("true_name");
            String mob_phone = intent.getStringExtra("mob_phone");
            String address1 = intent.getStringExtra("address");
            String areaInfo = intent.getStringExtra("addressInfo");
            name.setText(true_name);
            phone.setText(mob_phone);
            address.setText(address1);
            addressInfo.setText(areaInfo);
        }
        //点击提交
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click.equals("tianjia")) {
                    postServerData(API.ADD_ADDRESS_PATH);
                } else if (click.equals("bianji")) {
                    String address_id = intent.getStringExtra("address_id");
                    postServerData2(API.BIANJI_ADDRESS_PATH, address_id);
                }
            }
        });
    }

    //获取控件
    private void initView() {
        name = (EditText) findViewById(R.id.name);
        phone = (EditText) findViewById(R.id.phone);
        address = (EditText) findViewById(R.id.youaddress);
        addressInfo = (EditText) findViewById(R.id.addressinfo);
        ok = (Button) findViewById(R.id.commint);
    }

    //上传后台
    private void postServerData(String path) {
        RequestParams params = new RequestParams(path);
        params.addBodyParameter("key", MyUtils.getSharedPreferencesInstance(this).getString("key", ""));
        params.addBodyParameter("true_name", name.getText().toString());
        params.addBodyParameter("mob_phone", phone.getText().toString());
        params.addBodyParameter("city_id", "1");
        params.addBodyParameter("area_id", "2");
        params.addBodyParameter("address", address.getText().toString());
        params.addBodyParameter("area_info", addressInfo.getText().toString());
        params.setMultipart(true);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.optInt("code");
                    if (code == 200) {
                        Toast.makeText(AddAddressActivity.this, "添加成功~", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddAddressActivity.this, AddressActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(AddAddressActivity.this, "添加失败，请重试！", Toast.LENGTH_SHORT).show();
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

    //上传后台
    private void postServerData2(String path, String address_id) {
        RequestParams params = new RequestParams(path);
        params.addBodyParameter("key", MyUtils.getSharedPreferencesInstance(this).getString("key", ""));
        params.addBodyParameter("true_name", name.getText().toString());
        params.addBodyParameter("mob_phone", phone.getText().toString());
        params.addBodyParameter("city_id", "1");
        params.addBodyParameter("area_id", "2");
        params.addBodyParameter("address", address.getText().toString());
        params.addBodyParameter("area_info", addressInfo.getText().toString());
        params.addBodyParameter("address_id", address_id);
        params.setMultipart(true);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.optInt("code");
                    if (code == 200) {
                        Toast.makeText(AddAddressActivity.this, "修改成功~", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddAddressActivity.this, AddressActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(AddAddressActivity.this, "修改失败，请重试！", Toast.LENGTH_SHORT).show();
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
