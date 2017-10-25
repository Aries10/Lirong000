package com.bawei.myproject;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.bawei.myproject.bean.Bean;
import com.bawei.myproject.fragment.FragmentMiddle;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.util.ArrayList;
import java.util.List;

public class WebViewActivity extends AppCompatActivity implements View.OnClickListener {

    private WebView webView;
    private ImageView img_share;
    private Bean.ResultBean.DataBean dataBean;

    private static final String APP_ID = "1105602574"; //获取的APPID
    private Tencent mTencent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        //传入参数APPID
        mTencent = Tencent.createInstance(APP_ID, WebViewActivity.this.getApplicationContext());

        //获取组件
        webView = (WebView) findViewById(R.id.webView);
        img_share = (ImageView) findViewById(R.id.img_share);

        //设置点击事件
        img_share.setOnClickListener(this);

        /**
         * 显示网页
         */
        getUrlShow();

    }

    private void getUrlShow() {

        //接受值
        String url = getIntent().getStringExtra("url");

        webView.getSettings().setJavaScriptEnabled(true);

        //加载网页
        webView.loadUrl(url);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_share:
                View v = LayoutInflater.from(WebViewActivity.this).inflate(R.layout.shareitem, null);
                PopupWindow window = new PopupWindow(v, ActionBar.LayoutParams.FILL_PARENT,300);
                window.showAsDropDown(img_share);

                //点击其他部分就关闭弹窗
                window.setBackgroundDrawable(new ColorDrawable());
                window.setOutsideTouchable(true);

                //调用数据
                Bean bean = FragmentMiddle.bean;

                //设置监听
                setClick(v,window,bean);

                break;
        }
    }

    /**
     * 分享
     * @param v
     * @param window
     */
    private void setClick(View v, final PopupWindow window,Bean bean) {

        //获取分享的数据
        List<Bean.ResultBean.DataBean> dataBeanList = bean.getResult().getData();
        for (int i =0;i<dataBeanList.size();i++){
            dataBean = dataBeanList.get(i);
        }

        //获取组件
        //点击取消
        v.findViewById(R.id.bt_cancel_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                window.dismiss();
            }
        });
        //分享至qq好友
        v.findViewById(R.id.bt_share_qqfriend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Bundle params = new Bundle();
                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);//分享的类型
                params.putString(QQShare.SHARE_TO_QQ_TITLE, dataBean.getTitle());//分享标题
                params.putString(QQShare.SHARE_TO_QQ_SUMMARY,dataBean.getAuthor_name());//要分享作者名
                params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,dataBean.getUrl());//内容地址
                params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,dataBean.getThumbnail_pic_s());//分享的图片URL
                params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "测试");//应用名称
                mTencent.shareToQQ(WebViewActivity.this, params, new ShareUiListener());
            }
        });
        v.findViewById(R.id.bt_share_space).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int QzoneType = QzoneShare.SHARE_TO_QZONE_TYPE_NO_TYPE;
                Bundle params = new Bundle();
                params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneType);
                params.putString(QzoneShare.SHARE_TO_QQ_TITLE, dataBean.getTitle());//分享标题
                params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, dataBean.getAuthor_name());//分享的作者名
                params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, "http://blog.csdn.net/sandyran/article/details/53204529");//分享的链接
                //分享的图片, 以ArrayList<String>的类型传入，以便支持多张图片（注：图片最多支持9张图片，多余的图片会被丢弃）
                ArrayList<String> imageUrls = new ArrayList<String>();
                imageUrls.add("http://avatar.csdn.net/B/3/F/1_sandyran.jpg");//添加一个图片地址
                params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);//分享的图片URL
                mTencent.shareToQzone(WebViewActivity.this, params, new ShareUiListener());
            }
        });
    }


    /**
     * 自定义监听器实现IUiListener，需要3个方法
     * onComplete完成 onError错误 onCancel取消
     */
    private class ShareUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            //分享成功
            Toast.makeText(WebViewActivity.this,"分享成功",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(UiError uiError) {
            //分享失败
            Toast.makeText(WebViewActivity.this,"分享失败",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel() {
            //分享取消
            Toast.makeText(WebViewActivity.this,"分享取消",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 回调
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (null != mTencent) {
            mTencent.onActivityResult(requestCode, resultCode, data);
        }
    }

}
