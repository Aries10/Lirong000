package com.bwie.shopping.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.shopping.R;
import com.bwie.shopping.bean.ShopBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 1:类的用途
 * 2：@author Dell
 * 3：@date 2017/9/13
 */

public class MyAdapter_Cart extends BaseExpandableListAdapter {
    private Context context;
    private List<ShopBean> list;
    private int cartnum = 0;

    public MyAdapter_Cart(Context context, List<ShopBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return list.get(groupPosition).getGoodsBeanList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).getGoodsBeanList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final GroupViewHolder holder;
        if (convertView == null) {
            //得到组布局
            convertView = View.inflate(context, R.layout.shop_item, null);
            //创建GroupViewHolder实例
            holder = new GroupViewHolder();
            //获取控件
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.shop_checked);
            holder.shopName = (TextView) convertView.findViewById(R.id.shopname);
            convertView.setTag(holder);
        } else {
            //复用
            holder = (GroupViewHolder) convertView.getTag();
            //适配数据
            holder.shopName.setText(list.get(groupPosition).getShopName());
            holder.checkBox.setChecked(list.get(groupPosition).issIscheck());
            //设置复选框点击监听
            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //得到复选框状态
                    boolean checked = holder.checkBox.isChecked();
                    list.get(groupPosition).setsIscheck(checked);
                    if (checked) {//选中状态
                        //将其子选项都变为选中状态
                        for (int i = 0; i < list.get(groupPosition).getGoodsBeanList().size(); i++) {
                            list.get(groupPosition).getGoodsBeanList().get(i).setgIscheck(true);
                        }
                        //刷新适配器
                        notifyDataSetChanged();
                    } else {//没有选中
                        //将其子选项都变为没有选中状态
                        for (int i = 0; i < list.get(groupPosition).getGoodsBeanList().size(); i++) {
                            list.get(groupPosition).getGoodsBeanList().get(i).setgIscheck(false);
                        }
                        //刷新适配器
                        notifyDataSetChanged();
                    }
                    //定义boolean变量  与全选框关联
                    boolean flag = true;
                    for (int i = 0; i < list.size(); i++) {
                        //有没有选中的 变量值为false
                        if (!list.get(i).issIscheck()) {
                            flag = false;
                        }
                    }
                    //调用接口里的方法 传值
                    onChecked.checked(flag);
                    //遍历所有子数据 计算选中数量
                    int num = 0;
                    float price = 0;
                    for (int i = 0; i < list.size(); i++) {
                        for (int j = 0; j < list.get(i).getGoodsBeanList().size(); j++) {
                            if (list.get(i).getGoodsBeanList().get(j).isgIscheck()) {
                                num+=Integer.parseInt(list.get(i).getGoodsBeanList().get(j).getGoodsNum());
                                price += Float.parseFloat(list.get(i).getGoodsBeanList().get(j).getGoodsTotal());
                            }
                        }
                    }
                    //调用接口里的方法 传值
                    onCountTotal.countTotal(num);
                    onPriceTotal.priceTotal(price);
                }
            });
        }
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildViewHolder holder;
        if (convertView == null) {
            //得到组布局
            convertView = View.inflate(context, R.layout.goods_item, null);
            //创建GroupViewHolder实例
            holder = new ChildViewHolder();
            //获取控件
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.goods_checked);
            holder.goodsName = (TextView) convertView.findViewById(R.id.goodscart_name);
            holder.goodsPic = (ImageView) convertView.findViewById(R.id.goodscart_pic);
            holder.goodsPrice = (TextView) convertView.findViewById(R.id.goodscart_price);
            holder.goodsCount = (TextView) convertView.findViewById(R.id.goodscart_count);
            holder.addcart = (ImageView) convertView.findViewById(R.id.cart_jia);
            holder.movecart = (ImageView) convertView.findViewById(R.id.cart_jian);
            convertView.setTag(holder);
        } else {
            //复用
            holder = (ChildViewHolder) convertView.getTag();
        }
        //适配数据
        holder.checkBox.setChecked(list.get(groupPosition).getGoodsBeanList().get(childPosition).isgIscheck());
        holder.goodsName.setText(list.get(groupPosition).getGoodsBeanList().get(childPosition).getGoodsName());
        Picasso.with(context).load(list.get(groupPosition).getGoodsBeanList().get(childPosition).getGoodsPic()).into(holder.goodsPic);
        holder.goodsPrice.setText("￥"+list.get(groupPosition).getGoodsBeanList().get(childPosition).getGoodsPrice());
        holder.goodsCount.setText("x" + list.get(groupPosition).getGoodsBeanList().get(childPosition).getGoodsNum());

        //购物车商品数量的加减
        holder.addcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        holder.movecart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //设置复选框点击监听
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //得到复选框状态
                boolean checked = holder.checkBox.isChecked();
                list.get(groupPosition).getGoodsBeanList().get(childPosition).setgIscheck(checked);
                //定义boolean变量 默认值true
                boolean flag = true;
                //定义选中数量    选中商品总价格
                int num = 0;
                float price = 0;
                //遍历所以子数据
                for (int i = 0; i < list.size(); i++) {
                    for (int j = 0; j < list.get(i).getGoodsBeanList().size(); j++) {
                        if (!list.get(i).getGoodsBeanList().get(j).isgIscheck()) {
                            flag = false;
                        } else {
                            num+=Integer.parseInt(list.get(i).getGoodsBeanList().get(j).getGoodsNum());
                            price += Float.parseFloat(list.get(i).getGoodsBeanList().get(j).getGoodsTotal());
                        }
                    }
                }
                //调用接口 传值
                onChecked.checked(flag);
                onCountTotal.countTotal(num);
                onPriceTotal.priceTotal(price);
                //根据当前组内子的复选框状态 确定组的状态
                for (int i = 0; i < list.size(); i++) {
                    for (int j = 0; j < list.get(groupPosition).getGoodsBeanList().size(); j++) {
                        if (!list.get(groupPosition).getGoodsBeanList().get(j).isgIscheck()) {
                            list.get(groupPosition).setsIscheck(false);
                            notifyDataSetChanged();
                            return;
                        }
                        list.get(groupPosition).setsIscheck(true);
                        notifyDataSetChanged();
                    }
                }
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class GroupViewHolder {
        CheckBox checkBox;
        TextView shopName;
    }

    class ChildViewHolder {
        CheckBox checkBox;
        ImageView goodsPic;
        TextView goodsName;
        TextView goodsPrice;
        TextView goodsCount;
        ImageView addcart;
        ImageView movecart;
    }

    //声明成员变量
    private OnChecked onChecked;
    private OnCountTotal onCountTotal;
    private OnPriceTotal onPriceTotal;

    //定义接口 传值
    public interface OnChecked {
        void checked(boolean tag);
    }

    public interface OnCountTotal {
        void countTotal(int count);
    }

    public interface OnPriceTotal {
        void priceTotal(float price);
    }

    //提供set方法

    public void setOnChecked(OnChecked onChecked) {
        this.onChecked = onChecked;
    }

    public void setOnCountTotal(OnCountTotal onCountTotal) {
        this.onCountTotal = onCountTotal;
    }

    public void setOnPriceTotal(OnPriceTotal onPriceTotal) {
        this.onPriceTotal = onPriceTotal;
    }
}
