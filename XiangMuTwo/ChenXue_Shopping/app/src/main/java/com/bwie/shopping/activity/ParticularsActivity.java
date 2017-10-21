package com.bwie.shopping.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.shopping.R;
import com.bwie.shopping.api.API;
import com.bwie.shopping.bean.ParticularsBean;
import com.bwie.shopping.utils.MyUtils;
import com.bwie.shopping.view.FlowLayout;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class ParticularsActivity extends AppCompatActivity implements View.OnClickListener {
    private Button addCar,buyNow,ok;
    private ImageView goods_pic,back,xiangqing;
    private TextView goodsName,goodsPrice,area_name,content,if_store_cn;
    private WebView webView;
    private String goodsid;
    private ParticularsBean particularsBean;
    private PopupWindow popupWindow;
    private View view;
    private FlowLayout flowLayout;
    private ImageView jia,jian;
    private TextView count;
    private int total = 1;

    private String mNames[] = {
            "黑色","灰色","宝石蓝",
            "星空灰","红棕色","棕色",
            "深蓝色","酒红色","银色",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particulars);
        //获取控件
        initView();
        //得到传过来的值
        Intent intent = getIntent();
        goodsid = intent.getStringExtra("goodsid");
        //获取后台数据
        getServerData();

        //得到popupwindow布局
        view = View.inflate(this, R.layout.addcart_popupwindow, null);
        //得到布局里的控件
        ok = (Button) view.findViewById(R.id.add_cart_ok);
        jia = (ImageView) view.findViewById(R.id.jia);
        jian = (ImageView) view.findViewById(R.id.jian);
        count = (TextView) view.findViewById(R.id.count);
        //找到流布局控件
        flowLayout = (FlowLayout) view.findViewById(R.id.flowlayout);
        initChildViews();
        jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                total++;
                count.setText(total+"");
            }
        });
        jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (total == 1){
                    Toast.makeText(ParticularsActivity.this, "至少选择一件商品", Toast.LENGTH_SHORT).show();
                }else {
                    total--;
                    count.setText(total+"");
                }
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postAddCart();
            }
        });
        //实例popupwindow
        popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        //设置popupWindow
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        //返回点击监听
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //添加购物车 购买点击监听
        addCar.setOnClickListener(this);
        buyNow.setOnClickListener(this);
    }
    //获取控件的方法
    private void initView() {
        addCar = (Button) findViewById(R.id.add_cart);
        buyNow = (Button) findViewById(R.id.buy_now);
        goods_pic = (ImageView) findViewById(R.id.p_googs_pic);
        xiangqing = (ImageView) findViewById(R.id.xiangqing);
        back = (ImageView) findViewById(R.id.p_back);
        webView = (WebView) findViewById(R.id.p_webview);
        goodsName = (TextView) findViewById(R.id.p_goods_name);
        goodsPrice = (TextView) findViewById(R.id.p_goods_price);
        area_name = (TextView) findViewById(R.id.area_name);
        content = (TextView) findViewById(R.id.content);
        if_store_cn = (TextView) findViewById(R.id.if_store_cn);
    }
    //获取后台数据的方法
    public void getServerData(){
        RequestParams params = new RequestParams(API.PARTICULAR_PATH+"&goods_id="+goodsid);
        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Log.e("---------",result);
                Gson gson = new Gson();
                particularsBean = gson.fromJson(result, ParticularsBean.class);
                //给控件赋值
                Picasso.with(ParticularsActivity.this).load(particularsBean.getDatas().getGoods_image()).into(goods_pic);
                goodsName.setText(particularsBean.getDatas().getGoods_info().getGoods_name());
                goodsPrice.setText("￥"+ particularsBean.getDatas().getGoods_info().getGoods_price());
                area_name.setText(particularsBean.getDatas().getGoods_hair_info().getArea_name());
                content.setText(particularsBean.getDatas().getGoods_hair_info().getContent());
                if_store_cn.setText(particularsBean.getDatas().getGoods_hair_info().getIf_store_cn());
                xiangqing.setScaleType(ImageView.ScaleType.FIT_XY);
                xiangqing.setImageResource(R.mipmap.xiangqing);
                //webView设置
                WebSettings settings = webView.getSettings();
                settings.setJavaScriptCanOpenWindowsAutomatically(true);
                settings.setJavaScriptEnabled(true);
                webView.setWebChromeClient(new WebChromeClient());
                webView.setWebViewClient(new WebViewClient());
                webView.loadUrl(API.INTRODUCED_PATH+"&goods_id="+goodsid);
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

    @Override
    public void onClick(View v) {
        if (MyUtils.getSharedPreferencesInstance(this).getBoolean("islogin",false)){
            switch (v.getId()){
                case R.id.add_cart:
                    if (popupWindow.isShowing()){
                        popupWindow.dismiss();
                    }else {
                        popupWindow.showAtLocation(view, Gravity.BOTTOM,0,0);
                    }
                    break;
                case R.id.buy_now:
                    Intent intent = new Intent(ParticularsActivity.this, AffirmOrderActivity.class);
                    intent.putExtra("particularsBean",particularsBean);
                    startActivity(intent);
                    break;
            }
        }else {
            Intent intent = new Intent(this, LogingActivity.class);
            intent.putExtra("where","cart");
            intent.putExtra("goodsid",goodsid);
            startActivity(intent);
        }
    }

    //添加购物车的方法
    private void postAddCart() {
        RequestParams params = new RequestParams(API.ADDCART_PATH);
        params.addBodyParameter("key",MyUtils.getSharedPreferencesInstance(this).getString("key",""));
        Log.e("key",MyUtils.getSharedPreferencesInstance(this).getString("key",""));
        params.addBodyParameter("goods_id",goodsid);
        params.addBodyParameter("quantity",total+"");
        params.setMultipart(true);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    //解析返回的json
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.optInt("code");
                    if (code == 200){
                        Toast.makeText(ParticularsActivity.this, "添加成功~", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(ParticularsActivity.this, "添加失败~", Toast.LENGTH_SHORT).show();
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

    private void initChildViews() {
        // TODO Auto-generated method stub
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 5;
        lp.rightMargin = 5;
        lp.topMargin = 5;
        lp.bottomMargin = 5;
        for(int i = 0; i < mNames.length; i ++){
            TextView view = new TextView(this);
            view.setText(mNames[i]);
            view.setTextColor(Color.WHITE);
            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.textview_bg));
            flowLayout.addView(view,lp);
        }
    }
}
