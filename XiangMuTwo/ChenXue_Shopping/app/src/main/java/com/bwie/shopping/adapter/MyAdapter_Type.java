package com.bwie.shopping.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.shopping.R;
import com.bwie.shopping.bean.TypeBean;
import com.bwie.shopping.utils.MyUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 1:类的用途
 * 2：@author Dell
 * 3：@date 2017/9/8
 */

public class MyAdapter_Type extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<TypeBean.DatasBean.ClassListBean> list;
    public static int tagPosition;

    public static int getTagPosition() {
        return tagPosition;
    }

    public static void setTagPosition(int tagPosition) {
        MyAdapter_Type.tagPosition = tagPosition;
    }

    public MyAdapter_Type(Context context, List<TypeBean.DatasBean.ClassListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.type_item, parent, false);
        final MyLeftViewHolder leftViewHolder = new MyLeftViewHolder(view);
        //点击监听
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recycleViewItemClickListener.recycleViewItemClickListener(leftViewHolder.getPosition(), view, leftViewHolder);
            }
        });
        return leftViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //给文本框赋值
        ((MyLeftViewHolder) holder).tv_left_type.setText(list.get(position).getGc_name());
        if (position == getTagPosition()) {
            holder.itemView.setBackgroundResource(R.color.colorPblBackground);
        } else {
            holder.itemView.setBackgroundResource(R.color.colorWhite3);
        }
        //设置种类图片
        if (list.get(position).getImage().length()>0) {
            Picasso.with(context).load(MyUtils.Unicode2GBK(list.get(position).getImage())).into(((MyLeftViewHolder) holder).iv_type_pic);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyLeftViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_left_type;
        private ImageView iv_type_pic;

        public MyLeftViewHolder(View itemView) {
            super(itemView);
            tv_left_type = (TextView) itemView.findViewById(R.id.tv_type);
            iv_type_pic = (ImageView) itemView.findViewById(R.id.iv_type_pic);
        }
    }

    //声明成员变量
    public OnRecycleViewItemClickListener recycleViewItemClickListener;

    //定义点击接口
    public interface OnRecycleViewItemClickListener {
        void recycleViewItemClickListener(int position, View view, RecyclerView.ViewHolder viewHolder);
    }

    //提供set方法
    public void setRecycleViewItemClickListener(OnRecycleViewItemClickListener recycleViewItemClickListener) {
        this.recycleViewItemClickListener = recycleViewItemClickListener;
    }
}
