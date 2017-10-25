package com.bawei.myproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bawei.myproject.bean.QQBean;
import com.bawei.myproject.utils.BaseUiListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import uk.co.senab.photoview.PhotoView;

public class PhotoViewActivity extends AppCompatActivity {

    private int scale;
    private PhotoView img_photo_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);

        img_photo_view = (PhotoView) findViewById(R.id.img_photo_view);

        //获取头像 并显示
        QQBean qqBean = BaseUiListener.qqBean;
        DisplayImageOptions options = new DisplayImageOptions.Builder().build();
        ImageLoader.getInstance().displayImage(qqBean.getFigureurl_qq_1(),img_photo_view,options);
    }

}
