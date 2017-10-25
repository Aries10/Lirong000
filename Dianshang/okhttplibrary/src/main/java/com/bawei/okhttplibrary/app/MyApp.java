package com.bawei.okhttplibrary.app;

import android.app.Application;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by dell-pc on 2017/10/10.
 */

public class MyApp extends Application {

    public static MyApp mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                .build();
        ImageLoader.getInstance().init(configuration);
    }
    public static DisplayImageOptions getDisplay(){
        DisplayImageOptions options = new DisplayImageOptions.Builder().build();
        return options;
    }

    public static MyApp getInatance(){
        return mInstance;
    }

}
