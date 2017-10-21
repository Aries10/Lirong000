package com.bwie.shopping.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.shopping.R;
import com.bwie.shopping.utils.SearchHistoryUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 1:类的用途
 * 2：@author Dell
 * 3：@date 2017/9/1
 */

public class MyAdapter_History extends BaseAdapter{
    private List<String> list;
    private Context context;

    public MyAdapter_History(List<String> list, Context context) {
        this.list = list;
        this.context = context;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            //加载子条目布局
            convertView = View.inflate(context, R.layout.history_item,null);
            //实例ViewHolder
            holder = new ViewHolder();
            //获取控件
            holder.title = (TextView) convertView.findViewById(R.id.history_content);
            convertView.setTag(holder);
        }else{
            //复用
            holder = (ViewHolder) convertView.getTag();
        }
        //适配数据
        holder.title.setText(list.get(position));
        return convertView;
    }
    class ViewHolder{
        TextView title;
    }
}
