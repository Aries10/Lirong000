package com.bawei.zonghe.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.zonghe.R;
import com.bawei.zonghe.bean.DetailsBean;
import com.bawei.zonghe.utils.TwoImageUtils;
import com.bwei.okhttp3ps.app.MyApp;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by dell-pc on 2017/10/25.
 */

public class MyDetailsAdapter extends RecyclerView.Adapter<MyDetailsAdapter.MyViewHolder> {

    private Context context;
    private List<DetailsBean.StoriesBean> list;

    public MyDetailsAdapter(Context context, List<DetailsBean.StoriesBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_details, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(inflate);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DetailsBean.StoriesBean storiesBean = list.get(position);
        List<String> images = storiesBean.getImages();

        for (int i = 0;i<images.size();i++){
            if(images.size() == 0){
                holder.iv.setVisibility(View.VISIBLE);
            }else if(TextUtils.isEmpty(images.get(i))){
                holder.iv.setVisibility(View.VISIBLE);
            }else{
                TwoImageUtils.loadImage(images.get(i),holder.iv);
                ImageLoader.getInstance().displayImage(images.get(i), holder.iv, MyApp.getDisplay());
            }
        }

        holder.tv.setText(storiesBean.getTitle());

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
            iv = itemView.findViewById(R.id.dt_iv);
            tv = itemView.findViewById(R.id.dt_tv);
        }
    }
}
