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
 * 3：@date 2017/9/8
 */

public class MyAdapter_HListView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<HomeBean.DataBean.SubjectsBean> list;
    private int POSITION;

    public MyAdapter_HListView(Context context, List<HomeBean.DataBean.SubjectsBean> list, int POSITION) {
        this.context = context;
        this.list = list;
        this.POSITION = POSITION;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lv_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Picasso.with(context).load(list.get(POSITION).getGoodsList().get(position).getGoods_img()).into(((MyViewHolder)holder).image);
        ((MyViewHolder)holder).title.setText(list.get(POSITION).getGoodsList().get(position).getGoods_name());
    }

    @Override
    public int getItemCount() {
        return list.get(POSITION).getGoodsList().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView title;

        public MyViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.lv_pic);
            title = (TextView) itemView.findViewById(R.id.lv_title);
        }
    }
}
