package com.bawei.zonghe.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bawei.zonghe.R;
import com.bawei.zonghe.bean.NewBean;
import com.bawei.zonghe.utils.BannerImageLoader;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell-pc on 2017/10/25.
 */

public class MyNewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<NewBean.StoriesBean> storiesBeanList;
    private List<NewBean.TopStoriesBean> topStoriesBeanList;

    public MyNewAdapter(Context context, List<NewBean.StoriesBean> storiesBeanList, List<NewBean.TopStoriesBean> topStoriesBeanList) {
        this.context = context;
        this.storiesBeanList = storiesBeanList;
        this.topStoriesBeanList = topStoriesBeanList;
    }

    /**
     * 设置多条目布局
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return 0;
        }else if(position == 1){
            return 1;
        }
        return 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == 0){
            View view = LayoutInflater.from(context).inflate(R.layout.item_new_banner, parent, false);
            BannerViewHolder bannerViewHolder = new BannerViewHolder(view);
            return bannerViewHolder;
        }else{
            View view1 = LayoutInflater.from(context).inflate(R.layout.item_new_rv, parent, false);
            RecycleViewHolder recycleViewHolder = new RecycleViewHolder(view1);
            return recycleViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof BannerViewHolder){
            BannerViewHolder holder1 = (BannerViewHolder) holder;
            holder1.banner.setImageLoader(new BannerImageLoader());
            List<String> bannerList = new ArrayList<>(); //定义一个存放banner图片的集合
            for(int i = 0;i<topStoriesBeanList.size();i++){
                bannerList.add(topStoriesBeanList.get(i).getImage());
            }
            holder1.banner.setImages(bannerList);
            holder1.banner.setDelayTime(3000);
            holder1.banner.start();
        }else if(holder instanceof RecycleViewHolder){
            RecycleViewHolder holder2 = (RecycleViewHolder) holder;
            holder2.rv.setLayoutManager(new LinearLayoutManager(context));
            MyRecyNewAdapter myRecyNewAdapter = new MyRecyNewAdapter(context, storiesBeanList);
            holder2.rv.setAdapter(myRecyNewAdapter);
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public void loadMore(List<NewBean.StoriesBean> s){
        for (NewBean.StoriesBean str : s){
            this.storiesBeanList.add(str);
        }
        //更新界面
        notifyDataSetChanged();

    }
    public void refreshMore(List<NewBean.StoriesBean> s){
        for (NewBean.StoriesBean str : s){
            this.storiesBeanList.add(0,str);
        }
        //更新界面
        notifyDataSetChanged();
    }

    public class RecycleViewHolder extends RecyclerView.ViewHolder{

        private final RecyclerView rv;

        public RecycleViewHolder(View itemView) {
            super(itemView);
            rv = itemView.findViewById(R.id.rv_new_item);
        }
    }

    public class BannerViewHolder extends RecyclerView.ViewHolder{

        private final Banner banner;

        public BannerViewHolder(View itemView) {
            super(itemView);
            banner = itemView.findViewById(R.id.banner);
        }
    }

}
