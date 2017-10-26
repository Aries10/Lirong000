package com.bawei.zonghe.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.zonghe.R;
import com.bawei.zonghe.bean.NewBean;
import com.bwei.okhttp3ps.app.MyApp;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by dell-pc on 2017/10/25.
 */

public class MyRecyNewAdapter extends RecyclerView.Adapter<MyRecyNewAdapter.MyViewHolder> {

    private Context context;
    private List<NewBean.StoriesBean> list;

    public MyRecyNewAdapter(Context context, List<NewBean.StoriesBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_recy_new, parent, false);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        List<String> images = list.get(position).getImages();
        for (int i = 0;i<images.size();i++){
            ImageLoader.getInstance().displayImage(images.get(i),holder.iv, MyApp.getDisplay());
        }
        holder.tv.setText(list.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private final ImageView iv;
        private final TextView tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv_rv);
            tv = itemView.findViewById(R.id.tv_rv);
        }
    }

}
