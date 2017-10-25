package com.bawei.myproject.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.myproject.R;
import com.bawei.myproject.bean.ListBean;

import java.util.List;

/**
 * Created by dell-pc on 2017/9/12.
 */

public class MyLeftListViewAdapter extends BaseAdapter {

    private List<ListBean> list;
    private Context context;

    public MyLeftListViewAdapter(List<ListBean> list,Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        //初始化ViewHolder
        ViewHolder holder = null;
        if(view == null){
            //实例化ViewHolder
            holder = new ViewHolder();
            view = View.inflate(context, R.layout.leftlistviewitem, null); //加载布局
            holder.left_lv_img = view.findViewById(R.id.left_lv_img); //获取组件
            holder.left_lv_tv = view.findViewById(R.id.left_lv_tv);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        //获取数据
        ListBean listBean = list.get(i);
        holder.left_lv_img.setImageResource(listBean.image);
        holder.left_lv_tv.setText(listBean.name);

        return view;
    }

    //存放组件
    class ViewHolder{
        TextView left_lv_tv;
        ImageView left_lv_img;
    }
}
