package com.bwie.shopping.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.shopping.R;
import com.bwie.shopping.bean.CartBean;
import com.bwie.shopping.bean.GoodsBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 1:类的用途
 * 2：@author Dell
 * 3：@date 2017/9/15
 */

public class MyAdapter_GoodsLV extends BaseAdapter {
    private Context context;
    private List<GoodsBean> list;

    public MyAdapter_GoodsLV(Context context, List<GoodsBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            //得到布局
            convertView = View.inflate(context, R.layout.goods_lv_item,null);
            //实例ViewHolder
            holder = new ViewHolder();
            //获取控件
            holder.shopName = (TextView) convertView.findViewById(R.id.cart_shopname);
            holder.goodsName = (TextView) convertView.findViewById(R.id.cart_goodsname);
            holder.goodsNum = (TextView) convertView.findViewById(R.id.cart_goodsnum);
            holder.goodsPrice = (TextView) convertView.findViewById(R.id.cart_goodsprice);
            holder.goodsPic = (ImageView) convertView.findViewById(R.id.cart_goodspic);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        //适配
        holder.shopName.setText(list.get(position).getShopName());
        Picasso.with(context).load(list.get(position).getGoodsPic()).into(holder.goodsPic);
        holder.goodsPrice.setText(list.get(position).getGoodsPrice());
        holder.goodsName.setText(list.get(position).getGoodsName());
        holder.goodsNum.setText(list.get(position).getGoodsNum());
        return convertView;
    }

    class ViewHolder {
        TextView shopName, goodsName, goodsPrice, goodsNum;
        ImageView goodsPic;
    }
}
