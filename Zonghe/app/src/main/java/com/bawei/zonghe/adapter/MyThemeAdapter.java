package com.bawei.zonghe.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bawei.zonghe.R;
import com.bawei.zonghe.bean.ThemeBean;
import com.bawei.zonghe.utils.TwoImageUtils;
import com.bwei.okhttp3ps.app.MyApp;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import cn.forward.androids.views.ShapeImageView;

/**
 * Created by dell-pc on 2017/10/25.
 */

public class MyThemeAdapter extends RecyclerView.Adapter<MyThemeAdapter.MyViewHolder> {

    private Context context;
    private List<ThemeBean.OthersBean> list;

    public MyThemeAdapter(Context context, List<ThemeBean.OthersBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_theme, parent, false);
        final MyViewHolder myViewHolder = new MyViewHolder(inflate);
        inflate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRecyclerListener.onRecycle(myViewHolder.getPosition());
            }
        });
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ThemeBean.OthersBean othersBean = list.get(position);
        holder.iv.setShape(3);
        holder.iv.setBorderColor(Color.parseColor("#999999"));
        holder.iv.setBorderSize(3);
        holder.iv.setRoundRadiis(20f,20f,20f,20f);

        ImageLoader.getInstance().displayImage(othersBean.getThumbnail(),holder.iv, MyApp.getDisplay());
        holder.tv.setText(othersBean.getName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private final ShapeImageView iv;
        private final TextView tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv_th);
            tv = itemView.findViewById(R.id.tv_th);
        }
    }

    /**
     * 自定义接口回调 设置监听
     */
    private OnRecyclerListener onRecyclerListener;

    public interface OnRecyclerListener{
        void onRecycle(int position);
    }

    public void setOnRecyclerListener(OnRecyclerListener onRecyclerListener) {
        this.onRecyclerListener = onRecyclerListener;
    }
}
