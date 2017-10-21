package com.bwie.shopping.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bwie.shopping.bean.HomeBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 1:类的用途
 * 2：@author Dell
 * 3：@date 2017/9/8
 */

public class MyAdapter_ImageAdapter extends BaseAdapter{

    private Context _context;
    private List<HomeBean.DataBean.ActivityInfoBean.ActivityInfoListBean> imgList;

    public MyAdapter_ImageAdapter(Context _context, List<HomeBean.DataBean.ActivityInfoBean.ActivityInfoListBean> imgList) {
        this._context = _context;
        this.imgList = imgList;
    }

    public int getCount() {
        return Integer.MAX_VALUE;
    }

    public Object getItem(int position) {

        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            ImageView imageView = new ImageView(_context);
            imageView.setAdjustViewBounds(true);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setLayoutParams(new Gallery.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            convertView = imageView;
            viewHolder.imageView = (ImageView) convertView;
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Picasso.with(_context).load(imgList.get(position%imgList.size()).getActivityImg()).into(viewHolder.imageView);

        return convertView;
    }
    private static class ViewHolder {
        ImageView imageView;
    }

}

