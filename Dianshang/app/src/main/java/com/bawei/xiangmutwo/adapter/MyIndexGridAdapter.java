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

public class MyIndexGridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private List<IndexBean.DataBean.Ad5Bean> list;

    public MyIndexGridAdapter(Context context, List<IndexBean.DataBean.Ad5Bean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_index_item,null);
        final MyGridViewHolder myGridViewHolder = new MyGridViewHolder(view);
        //点击监听
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
}
