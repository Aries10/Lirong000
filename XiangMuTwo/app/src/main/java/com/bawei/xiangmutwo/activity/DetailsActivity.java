package com.bawei.xiangmutwo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.okhttplibrary.app.MyApp;
import com.bawei.okhttplibrary.utils.GsonObjectCallback;
import com.bawei.okhttplibrary.utils.OkhttpUtils;
import com.bawei.xiangmutwo.R;
import com.bawei.xiangmutwo.api.Api;
import com.bawei.xiangmutwo.bean.DetailsBean;
import com.bawei.xiangmutwo.bean.UnregBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

public class DetailsActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_detailsback;
    private ImageView d_googs_pic;
    private TextView d_goods_name;
    private TextView d_goods_price;
    private TextView area_name;
    private TextView content;
    private TextView if_store_cn;
    private WebView webview;
    private Button buy_now;
    private Button add_cart;
    private String goods_id;
    private String goodsid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        initView();

        //获取传入的值
        goods_id = getIntent().getStringExtra("goods_id");

        //获取商品详情数据
        getDetailsData();

        //获取webview展示数据
        getWebViewData();

    }

    /**
     * 展示详情数据
     */
    private void getDetailsData() {
        OkhttpUtils.doGet(Api.DetailsURL + goods_id, new GsonObjectCallback<DetailsBean>() {
            @Override
            public void onUi(DetailsBean detailsBean) {
                goodsid = detailsBean.getDatas().getGoods_info().getGoods_id();
                DetailsBean.DatasBean.GoodsInfoBean goods_info = detailsBean.getDatas().getGoods_info();
                ImageLoader.getInstance().displayImage(detailsBean.getDatas().getGoods_image(),d_googs_pic, MyApp.getDisplay());
                d_goods_name.setText(goods_info.getGoods_name());
                d_goods_price.setText("￥"+goods_info.getGoods_price());
                area_name.setText(detailsBean.getDatas().getGoods_hair_info().getArea_name());
                content.setText(detailsBean.getDatas().getGoods_hair_info().getContent());
                if_store_cn.setText(detailsBean.getDatas().getGoods_hair_info().getIf_store_cn());
            }

            @Override
            public void onFailed(Call call, IOException e) {

            }
        });
    }

    /**
     * 获取webview数据
     */
    private void getWebViewData() {
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webview.setWebChromeClient(new WebChromeClient());
        webview.setWebViewClient(new WebViewClient());
        webview.loadUrl(Api.WebViewURL+goods_id);
    }

    private void initView() {
        iv_detailsback = (ImageView) findViewById(R.id.iv_detailsback);
        d_googs_pic = (ImageView) findViewById(R.id.d_googs_pic);
        d_goods_name = (TextView) findViewById(R.id.d_goods_name);
        d_goods_price = (TextView) findViewById(R.id.d_goods_price);
        area_name = (TextView) findViewById(R.id.area_name);
        content = (TextView) findViewById(R.id.content);
        if_store_cn = (TextView) findViewById(R.id.if_store_cn);
        webview = (WebView) findViewById(R.id.webview);
        buy_now = (Button) findViewById(R.id.buy_now);
        add_cart = (Button) findViewById(R.id.add_cart);

        buy_now.setOnClickListener(this);
        add_cart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buy_now:
                break;
            case R.id.add_cart:
                if(Api.sharedPreferences.getBoolean("isLogin",false) == false){
                    startActivity(new Intent(DetailsActivity.this,LoginActivity.class));
                    return;
                }
                //获取登录成功之后保存的key值 存入map集合
                Map<String,String> map = new HashMap<>();
                map.put("key",Api.sharedPreferences.getString("key",""));
                map.put("goods_id",goodsid);
                map.put("quantity",1+"");
                OkhttpUtils.doPost(map, Api.CartADDURL, new GsonObjectCallback<UnregBean>() {
                    @Override
                    public void onUi(UnregBean unregBean) {
                        if(unregBean.getCode() == 200){
                            Toast.makeText(DetailsActivity.this,"成功添加到购物车",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailed(Call call, IOException e) {

                    }
                });
                break;
            case R.id.iv_detailsback:
                finish();
                break;
        }
    }
}
