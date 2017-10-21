package com.bwie.shopping.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.shopping.R;

public class BannerConnectActivity extends AppCompatActivity {
    private WebView webView;
    private ImageView back;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_connect);
        //获取控件
        initView();
        //得到webViewSetting
        WebSettings settings = webView.getSettings();
        //设置支持Java代码 可在新窗口打开
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());

        //得到传过来的URL
        Intent intent = getIntent();
        String url = intent.getStringExtra("bannerUrl");
        String bannerTitle = intent.getStringExtra("bannerTitle");

        //设置文本标题
        title.setText(bannerTitle);
        //WebView设置加载url
        webView.loadUrl(url);

        //back点击监听
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        webView = (WebView) findViewById(R.id.banner_wv);
        back = (ImageView) findViewById(R.id.banner_back);
        title = (TextView) findViewById(R.id.banner_title);
    }
}
