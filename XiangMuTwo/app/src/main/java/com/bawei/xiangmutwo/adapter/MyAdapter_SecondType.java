package com.bawei.xiangmutwo.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bawei.okhttplibrary.utils.GsonObjectCallback;
import com.bawei.okhttplibrary.utils.OkhttpUtils;
import com.bawei.xiangmutwo.R;
import com.bawei.xiangmutwo.api.Api;
import com.bawei.xiangmutwo.bean.TypeSecondBean;
import com.bawei.xiangmutwo.bean.TypeThirdBean;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

/**
 * Created by dell-pc on 2017/10/16.
 */

public class MyAdapter_SecondType extends RecyclerView.Adapter<MyAdapter_SecondType.MyViewHolder> {

    private List<TypeSecondBean.DatasBean.ClassListBean> list;
    private Context context;

    public MyAdapter_SecondType(List<TypeSecondBean.DatasBean.ClassListBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_secondtype, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.type_gc_name.setText(list.get(position).getGc_name());
        holder.rv_third.setLayoutManager(new GridLayoutManager(context,3));
        OkhttpUtils.doGet(Api.TYPEBEANURL + "&gc_id=" + list.get(position).getGc_id(), new GsonObjectCallback<TypeThirdBean>() {
            @Override
            public void onUi(TypeThirdBean typeThirdBean) {
                MyAdapter_ThirdType myAdapter_thirdType = new MyAdapter_ThirdType(typeThirdBean.getDatas().getClass_list(),context);
                holder.rv_third.setAdapter(myAdapter_thirdType);
            }

            @Override
            public void onFailed(Call call, IOException e) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private final TextView type_gc_name;
        private final RecyclerView rv_third;

        public MyViewHolder(View itemView) {
            super(itemView);
            type_gc_name = (TextView) itemView.findViewById(R.id.type_second_gcname);
            rv_third = (RecyclerView) itemView.findViewById(R.id.recyclerViewThird);
        }
    }
}
