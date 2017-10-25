package com.bawei.xiangmutwo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.xiangmutwo.R;
import com.bawei.xiangmutwo.bean.IndexBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by dell-pc on 2017/10/22.
 */

public class MyIndexStagAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<IndexBean.DataBean.DefaultGoodsListBean> list;
    private Context context;
    private List<Integer> heights;

    public MyIndexStagAdapter(List<IndexBean.DataBean.DefaultGoodsListBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View pbl = LayoutInflater.from(parent.getContext()).inflate(R.layout.stag_index_item, null);
        MYViewHolder myViewHolder = new MYViewHolder(pbl);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MYViewHolder viewHolder = new MYViewHolder(holder.itemView);
        ViewGroup.LayoutParams params = viewHolder.imageView.getLayoutParams();//得到item的LayoutParams布局参数
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
}
