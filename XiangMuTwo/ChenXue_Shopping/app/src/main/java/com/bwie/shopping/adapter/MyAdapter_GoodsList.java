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
import com.bwie.shopping.bean.SearchBean;
import com.bwie.shopping.utils.MyUtils;
import com.bwie.xrecycleview.XRecyclerView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 1:类的用途
 * 2：@author Dell
 * 3：@date 2017/9/10
 */

public class MyAdapter_GoodsList extends XRecyclerView.Adapter<XRecyclerView.ViewHolder>{
    private Context context;
    private List<SearchBean.DatasBean.GoodsListBean> list;

    public MyAdapter_GoodsList(Context context, List<SearchBean.DatasBean.GoodsListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public XRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.goodslist_item, parent, false);
        final MyViewHolder myViewHolder = new MyViewHolder(view);
        //点击监听
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goodsListsItemClickListener.goodsListsItemClickListener(myViewHolder.getPosition());
            }
        });
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(XRecyclerView.ViewHolder holder, int position) {
        ((MyViewHolder)holder).goods_name.setText(list.get(position).getGoods_name());
        ((MyViewHolder)holder).goods_price.setText("￥"+list.get(position).getGoods_price());
        Picasso.with(context).load(list.get(position).getGoods_image_url()).into(((MyViewHolder)holder).goods_pic);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends XRecyclerView.ViewHolder{
        private ImageView goods_pic;
        private TextView goods_name,goods_price;
        public MyViewHolder(View itemView) {
            super(itemView);
            goods_pic = (ImageView) itemView.findViewById(R.id.goods_pic);
            goods_name = (TextView) itemView.findViewById(R.id.goods_name);
            goods_price = (TextView) itemView.findViewById(R.id.goods_price);
        }
    }

    //声明成员变量
    private OnGoodsListsItemClickListener goodsListsItemClickListener;

    //定义点击监听
    public interface OnGoodsListsItemClickListener{
        void goodsListsItemClickListener(int position);
    }

    //提供set方法

    public void setGoodsListsItemClickListener(OnGoodsListsItemClickListener goodsListsItemClickListener) {
        this.goodsListsItemClickListener = goodsListsItemClickListener;
    }
}
