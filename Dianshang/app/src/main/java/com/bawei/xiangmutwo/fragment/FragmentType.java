package com.bawei.xiangmutwo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bawei.okhttplibrary.utils.GsonObjectCallback;
import com.bawei.okhttplibrary.utils.OkhttpUtils;
import com.bawei.xiangmutwo.R;
import com.bawei.xiangmutwo.adapter.MyAdapter_SecondType;
import com.bawei.xiangmutwo.adapter.MyAdapter_Type;
import com.bawei.xiangmutwo.api.Api;
import com.bawei.xiangmutwo.bean.TypeBean;
import com.bawei.xiangmutwo.bean.TypeSecondBean;
import com.bawei.xiangmutwo.utils.MyDecoration;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

/**
 * Created by dell-pc on 2017/10/4.
 */

public class FragmentType extends Fragment {

    private RecyclerView recycler_left;
    private RecyclerView recycler_right;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragmenttype, container, false);

        initView(view);
        return view;
    }

    //初始化组件
    private void initView(View view) {
        recycler_left = (RecyclerView) view.findViewById(R.id.recycler_left);
        recycler_right = (RecyclerView) view.findViewById(R.id.recycler_right);

        getData();

    }

    /**
     * 加载数据
     */
    private void getData() {
        //请求一级数据
        OkhttpUtils.doGet(Api.TYPEBEANURL, new GsonObjectCallback<TypeBean>() {
            @Override
            public void onUi(final TypeBean typeBean) {
                //获取数据并添加到适配器
                List<TypeBean.DatasBean.ClassListBean> class_list = typeBean.getDatas().getClass_list();
                recycler_left.setLayoutManager(new LinearLayoutManager(getActivity()));
                recycler_left.addItemDecoration(new MyDecoration(getActivity(),MyDecoration.VERTICAL_LIST));
                final MyAdapter_Type adapter_type = new MyAdapter_Type(class_list,getActivity());
                recycler_left.setAdapter(adapter_type);
                //设置条目点击监听
                adapter_type.setOnRecyclerViewItemClickListener(new MyAdapter_Type.onRecyclerViewItemClickListener() {
                    @Override
                    public void recyclerViewListener(int position) {
                        adapter_type.notifyDataSetChanged();
                        //点击后请求二级数据
                        getSecondData(typeBean.getDatas().getClass_list().get(position).getGc_id());
                    }
                });
            }

            @Override
            public void onFailed(Call call, IOException e) {

            }
        });
    }

    /**
     * 请求二级数据
     */
    public void getSecondData(String gc_id){
        OkhttpUtils.doGet(Api.TYPEBEANURL + "&gc_id=" + gc_id, new GsonObjectCallback<TypeSecondBean>() {
            @Override
            public void onUi(TypeSecondBean typeSecondBean) {
                //二级列表的数据 添加到适配器
                List<TypeSecondBean.DatasBean.ClassListBean> class_list = typeSecondBean.getDatas().getClass_list();
                recycler_right.setLayoutManager(new LinearLayoutManager(getActivity()));
                MyAdapter_SecondType myAdapter_secondType = new MyAdapter_SecondType(class_list,getActivity());
                recycler_right.setAdapter(myAdapter_secondType);
            }

            @Override
            public void onFailed(Call call, IOException e) {

            }
        });
    }

}
