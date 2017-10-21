package com.bwie.shopping.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.shopping.R;
import com.bwie.shopping.api.API;
import com.bwie.shopping.bean.AddressBean;
import com.bwie.shopping.bean.ParticularsBean;
import com.bwie.shopping.utils.MyUtils;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.Serializable;

public class AffirmOrderActivity extends AppCompatActivity {
    private ImageView line;
    private TextView uname, uphone, address, shopName, goodsName, goodsPrice, goodsNum;
    ImageView goodsPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affirm_order);
        //获取控件
        initView();
        //设置图片充满
        line.setScaleType(ImageView.ScaleType.FIT_XY);
        //请求得到收货地址，判断是否有默认地址
        postData();
        //得到传过来的值
        Intent intent = getIntent();
        ParticularsBean bean = (ParticularsBean) intent.getSerializableExtra("particularsBean");
        //赋值
        shopName.setText(bean.getDatas().getStore_info().getStore_name());
        Picasso.with(this).load(bean.getDatas().getGoods_image()).into(goodsPic);
        goodsPrice.setText(bean.getDatas().getGoods_info().getGoods_price());
        goodsName.setText(bean.getDatas().getGoods_info().getGoods_name());

    }

    private void initView() {
        line = (ImageView) findViewById(R.id.aff_line);
        uname = (TextView) findViewById(R.id.aff_username);
        uphone = (TextView) findViewById(R.id.aff_userphone);
        address = (TextView) findViewById(R.id.aff_address);
        shopName = (TextView) findViewById(R.id.aff_shopname);
        goodsName = (TextView) findViewById(R.id.aff_goodsname);
        goodsPrice = (TextView) findViewById(R.id.aff_goodsprice);
        goodsNum = (TextView) findViewById(R.id.aff_goodsnum);
        goodsPic = (ImageView) findViewById(R.id.aff_goodspic);
    }

    //获取收货地址
    private void postData() {
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
                        //遍历地址集合 找到默认地址
                        for (int i = 0; i < addressBean.getDatas().getAddress_list().size(); i++) {
                            if (MyUtils.getSharedPreferencesInstance(AffirmOrderActivity.this).getString("address_id", "").equals(addressBean.getDatas().getAddress_list().get(i).getAddress_id())) {
                                //赋值
                                uname.setText("收货人"+addressBean.getDatas().getAddress_list().get(i).getTrue_name());
                                uphone.setText(addressBean.getDatas().getAddress_list().get(i).getMob_phone());
                                uphone.setText(addressBean.getDatas().getAddress_list().get(i).getAddress());
                            }
                        }
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
