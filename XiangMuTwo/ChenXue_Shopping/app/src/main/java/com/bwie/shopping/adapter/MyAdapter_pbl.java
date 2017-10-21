package com.bwie.shopping.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.shopping.R;
import com.bwie.shopping.bean.HomeBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * 1:类的用途
 * 2：@author Dell
 * 3：@date 2017/9/6
 */

public class MyAdapter_pbl extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<HomeBean.DataBean.DefaultGoodsListBean> list;
    private Context context;
    private List<Integer> heights;

    public MyAdapter_pbl(List<HomeBean.DataBean.DefaultGoodsListBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        final View pbl = LayoutInflater.from(parent.getContext()).inflate(R.layout.pbl_item, null);
        final MYViewHolder myViewHolder = new MYViewHolder(pbl);
        //点击监听
        pbl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pbl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        recycleViewPItemListener.recycleViewPItemListener(myViewHolder.getPosition());
                    }
                });
            }
        });
        //长按监听
        pbl.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                recycleViewItemLongListener.recycleViewItemLongListener(myViewHolder.getPosition());
                return true;
            }
        });
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MYViewHolder viewHolder = new MYViewHolder(holder.itemView);
        ViewGroup.LayoutParams params = viewHolder.imageView.getLayoutParams();//得到item的LayoutParams布局参数
        getRandomHeight(list);
        params.height = heights.get(position);//把随机的高度赋予itemView布局
        viewHolder.imageView.setLayoutParams(params);//把params设置给itemView布局
        viewHolder.textView.setText(list.get(position).getGoods_name());
        Picasso.with(context).load(list.get(position).getGoods_img()).into(viewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MYViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public MYViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.pbl_iv);
            textView = (TextView) itemView.findViewById(R.id.pbl_tv);
        }
    }

    //生成随机数
    private void getRandomHeight(List<HomeBean.DataBean.DefaultGoodsListBean> lists) {//得到随机item的高度
        heights = new ArrayList<>();
        for (int i = 0; i < lists.size(); i++) {
            heights.add((int) (200 + Math.random() * 200));
        }
    }

    //声明成员变量
    private OnRecycleViewPItemListener recycleViewPItemListener;
    private OnRecycleViewItemLongListener recycleViewItemLongListener;

    //定义点击接口
    public interface OnRecycleViewPItemListener {
        void recycleViewPItemListener(int position);
    }

    //定义长按接口
    public interface OnRecycleViewItemLongListener {
        void recycleViewItemLongListener(int position);
    }

    //提供set方法
    public void setRecycleViewPItemListener(OnRecycleViewPItemListener recycleViewPItemListener) {
        this.recycleViewPItemListener = recycleViewPItemListener;
    }
    public void setRecycleViewItemLongListener(OnRecycleViewItemLongListener recycleViewItemLongListener) {
        this.recycleViewItemLongListener = recycleViewItemLongListener;
    }
}
