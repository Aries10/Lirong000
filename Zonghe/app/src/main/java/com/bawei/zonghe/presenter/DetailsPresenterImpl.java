package com.bawei.zonghe.presenter;

import android.content.Context;

import com.bawei.zonghe.bean.DetailsBean;
import com.bawei.zonghe.model.DetailsModel;
import com.bawei.zonghe.model.DetailsModelImpl;
import com.bawei.zonghe.view.DetailsView;

/**
 * Created by dell-pc on 2017/10/25.
 */

public class DetailsPresenterImpl implements DetailsPresenter,DetailsShow {

    private DetailsView detailsView;
    private final DetailsModel detailsModel;

    public DetailsPresenterImpl(DetailsView detailsView) {
        this.detailsView = detailsView;
        detailsModel = new DetailsModelImpl();
    }

    //给m层传递数据
    @Override
    public void pass(Context context, int id) {
        //关联p层m层
        detailsModel.getData(context,id,this);
    }

    //m层请求到数据后  传递给v层
    @Override
    public void onData(DetailsBean bean) {
        if(detailsView != null){
            detailsView.setData(bean);
        }
    }
}
