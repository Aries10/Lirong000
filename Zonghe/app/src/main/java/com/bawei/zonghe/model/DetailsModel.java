package com.bawei.zonghe.model;

import android.content.Context;

import com.bawei.zonghe.presenter.DetailsShow;

/**
 * Created by dell-pc on 2017/10/25.
 */

public interface DetailsModel {

    /**
     * 请求数据的方法
     */
    void getData(Context context,int id, DetailsShow show);

}
