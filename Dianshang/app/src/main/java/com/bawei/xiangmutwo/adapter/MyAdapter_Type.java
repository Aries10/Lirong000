package com.bawei.xiangmutwo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bawei.xiangmutwo.R;
import com.bawei.xiangmutwo.bean.TypeBean;

import java.util.List;

/**
 * Created by dell-pc on 2017/10/16.
 */

public class MyAdapter_Type extends RecyclerView.Adapter<MyAdapter_Type.MyViewHolder> {

    private List<TypeBean.DatasBean.ClassListBean> list;
    private Context context;

    public MyAdapter_Type(List<TypeBean.DatasBean.ClassListBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.item_type, parent, false);
        final MyViewHolder myViewHolder = new MyViewHolder(view);
        //设置监听
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecyclerViewItemClickListener.recyclerViewListener(myViewHolder.getPosition());
            }
        });

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.type_gc_name.setText(list.get(position).getGc_name());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private final TextView type_gc_name;

        public MyViewHolder(View itemView) {
            super(itemView);
            type_gc_name = (TextView) itemView.findViewById(R.id.type_gc_name);
        }
    }

    public onRecyclerViewItemClickListener onRecyclerViewItemClickListener;
    //定义接口
    public interface onRecyclerViewItemClickListener{
        void recyclerViewListener(int position);
    }
    //提供set方法
    public void setOnRecyclerViewItemClickListener(MyAdapter_Type.onRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }
}
