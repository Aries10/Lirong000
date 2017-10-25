package com.bawei.xiangmutwo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.okhttplibrary.app.MyApp;
import com.bawei.xiangmutwo.R;
import com.bawei.xiangmutwo.bean.SearchBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by dell-pc on 2017/10/18.
 */

public class MyAdapter_SearchShow extends RecyclerView.Adapter<MyAdapter_SearchShow.MyViewHolder> {

    private Context context;
    private List<SearchBean.DatasBean.GoodsListBean> list;

    public MyAdapter_SearchShow(Context context, List<SearchBean.DatasBean.GoodsListBean> list) {
        this.context = context;
        this.list = list;
    }

    //上下拉刷新加载数据
    public void loadMord(List<SearchBean.DatasBean.GoodsListBean> goodsListBeen,boolean flag){
        for (int i = 0;i<goodsListBeen.size();i++){
            if(flag == true){
                this.list.addAll(0,goodsListBeen);
            }else{
                this.list.addAll(goodsListBeen);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //加载布局
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_searchshow, parent, false);
        final MyViewHolder myViewHolder = new MyViewHolder(inflate);
        inflate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecycler.onrecyclerview(myViewHolder.getPosition());
            }
        });
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //获取数据
        SearchBean.DatasBean.GoodsListBean goodsListBean = list.get(position);
        //展示数据
        holder.goods_name.setText(goodsListBean.getGoods_name());
        holder.goods_price.setText("￥"+goodsListBean.getGoods_price());
        holder.store_name.setText(goodsListBean.getStore_name());
        ImageLoader.getInstance().displayImage(goodsListBean.getGoods_image_url(),holder.goods_image_url, MyApp.getDisplay());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final ImageView goods_image_url;
        private final TextView goods_price,store_name,goods_name;

        public MyViewHolder(View itemView) {
            super(itemView);
            //获取资源id
            goods_image_url = (ImageView) itemView.findViewById(R.id.goods_image_url);
            goods_name = (TextView) itemView.findViewById(R.id.goods_name);
            goods_price = (TextView) itemView.findViewById(R.id.goods_price);
            store_name = (TextView) itemView.findViewById(R.id.store_name);
        }
    }

    //接口回调设置监听事件
    private OnRecycler onRecycler;
    public interface OnRecycler{
        void onrecyclerview(int position);
    }
    public void setOnRecycler(OnRecycler onRecycler) {
        this.onRecycler = onRecycler;
    }
}
