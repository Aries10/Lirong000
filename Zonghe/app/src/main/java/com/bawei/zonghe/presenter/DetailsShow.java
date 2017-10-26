package com.bawei.zonghe.presenter;

import com.bawei.zonghe.bean.DetailsBean;

/**
 * Created by dell-pc on 2017/10/25.
 */

public interface DetailsShow {

    /**
     * 设置一个中间值  进行p层的数据传输
     * @param bean
     */
    void onData(DetailsBean bean);

}
