package com.bwie.shopping.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.shopping.R;
import com.bwie.shopping.bean.HomeBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 1:类的用途
 * 2：@author Dell
 * 3：@date 2017/9/6
 */

public class MyAdapter_gridview extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private List<HomeBean.DataBean.Ad5Bean> list;

    public MyAdapter_gridview(Context context, List<HomeBean.DataBean.Ad5Bean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gv_item,null);
        final MyGridViewHolder myGridViewHolder = new MyGridViewHolder(view);
        //点击监听
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recycleViewGItemListener.recycleViewGItemListener(myGridViewHolder.getPosition());
            }
        });
        //长按监听
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                recycleViewGItemLongListener.recycleViewGItemLongListener(myGridViewHolder.getPosition());
                return true;
            }
        });
        return myGridViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyGridViewHolder myGridViewHolder = new MyGridViewHolder(holder.itemView);
        myGridViewHolder.textView.setText(list.get(position).getTitle());
        Picasso.with(context).load(list.get(position).getImage()).into(myGridViewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyGridViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView textView;
        public MyGridViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.gv_iv);
            textView = (TextView) itemView.findViewById(R.id.gv_tv);
        }
    }

    //声明成员变量
    private OnRecycleViewGItemListener recycleViewGItemListener;
    private OnRecycleViewGItemLongListener recycleViewGItemLongListener;

    //定义点击监听接口
    public interface OnRecycleViewGItemListener{
        void recycleViewGItemListener(int position);
    }
    //定义长按监听接口
    public interface OnRecycleViewGItemLongListener{
        void recycleViewGItemLongListener(int position);
    }

    //定义set方法

    public void setRecycleViewGItemListener(OnRecycleViewGItemListener recycleViewGItemListener) {
        this.recycleViewGItemListener = recycleViewGItemListener;
    }
    public void setRecycleViewGItemLongListener(OnRecycleViewGItemLongListener recycleViewGItemLongListener) {
        this.recycleViewGItemLongListener = recycleViewGItemLongListener;
    }
}
