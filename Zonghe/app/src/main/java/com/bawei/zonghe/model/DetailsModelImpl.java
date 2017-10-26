package com.bawei.zonghe.model;

import android.content.Context;

import com.bawei.zonghe.api.Api;
import com.bawei.zonghe.bean.DetailsBean;
import com.bawei.zonghe.presenter.DetailsShow;
import com.bwei.okhttp3ps.utils.GsonObjectCallback;
import com.bwei.okhttp3ps.utils.OkHttp3Utils;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by dell-pc on 2017/10/25.
 */

public class DetailsModelImpl implements DetailsModel {
    @Override
    public void getData(Context context, int id, final DetailsShow show) {
        OkHttp3Utils.doGet(Api.DetailsURL+ id, new GsonObjectCallback<DetailsBean>() {
            @Override
            public void onUi(DetailsBean detailsBean) {
                //请求成功后回传给p层
                show.onData(detailsBean);
            }

            @Override
            public void onFailed(Call call, IOException e) {

            }
        });
    }
}
