package com.bawei.myproject.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.myproject.R;
import com.bawei.myproject.bean.Bean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by dell-pc on 2017/9/15.
 */

public class MyAdapter extends BaseAdapter {

    private List<Bean.ResultBean.DataBean> list;
    private Context context;
    private final DisplayImageOptions options;

    public MyAdapter(List<Bean.ResultBean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
        options = new DisplayImageOptions.Builder()
                .showImageOnFail(R.mipmap.ic_launcher)
                .showImageOnLoading(R.mipmap.ic_launcher)
                .build();
    }

    //加载更多数据
    public void loadMore(List<Bean.ResultBean.DataBean> beanList,boolean flag){
        //遍历传入的集合
        for (int i = 0;i<beanList.size();i++){
            if(flag == true){
                this.list.addAll(0,beanList);
            }else{
                this.list.addAll(beanList);
            }
        }
        notifyDataSetChanged();
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

        ViewHolder holder = null;
        if(view == null){
            holder = new ViewHolder();
            view = View.inflate(context, R.layout.item01, null);
            holder.tv = view.findViewById(R.id.tv);
            holder.iv1 = view.findViewById(R.id.iv1);
            holder.iv2 = view.findViewById(R.id.iv2);
            holder.iv3 = view.findViewById(R.id.iv3);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        Bean.ResultBean.DataBean dataBean = list.get(i);

        holder.tv.setText(dataBean.getTitle());

        ImageLoader.getInstance().displayImage(dataBean.getThumbnail_pic_s(),holder.iv1,options);
        ImageLoader.getInstance().displayImage(dataBean.getThumbnail_pic_s02(),holder.iv2,options);
        ImageLoader.getInstance().displayImage(dataBean.getThumbnail_pic_s03(),holder.iv3,options);

        return view;
    }

    //存放组件的类
    class ViewHolder{
        TextView tv;
        ImageView iv1,iv2,iv3;
    }

}
