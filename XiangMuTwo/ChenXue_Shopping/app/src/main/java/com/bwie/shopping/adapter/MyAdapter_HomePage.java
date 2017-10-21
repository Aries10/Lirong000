package com.bwie.shopping.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bwie.shopping.R;
import com.bwie.shopping.activity.BannerConnectActivity;
import com.bwie.shopping.banner.BannerImageLoader;
import com.bwie.shopping.bean.HomeBean;
import com.bwie.shopping.view.MyGallery2;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import cn.iwgang.countdownview.CountdownView;

/**
 * 1:类的用途
 * 2：@author Dell
 * 3：@date 2017/9/5
 */

public class MyAdapter_HomePage extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private HomeBean.DataBean dataBean;
    private Context context;

    private int preSelImgIndex = 0;

    private int TYPE_BANNER = 0;
    private int TYPE_GRID = 1;
    private int TYPE_GALLERY = 2;
    private int TYPE_PIC1 = 3;
    private int TYPE_HLIST = 4;
    private int TYPE_PBL = 7;
    private int TYPE_PIC2 = 5;
    private int TYPE_HLIST2 = 6;

    public MyAdapter_HomePage(HomeBean.DataBean dataBean, Context context) {
        this.dataBean = dataBean;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_BANNER) {
            View ban = LayoutInflater.from(parent.getContext()).inflate(R.layout.rvhead_banner, parent, false);
            return new BannerView(ban);
        } else if (viewType == TYPE_PBL) {
            View pbl = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_pbl, parent, false);
            return new PblView(pbl);
        } else if (viewType == TYPE_GRID) {
            View gridView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_gridview, parent, false);
            return new GridViewHolder(gridView);
        } else if (viewType == TYPE_PIC1) {
            View pic1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.pic1_item, parent, false);
            PicViewHolder picViewHolder = new PicViewHolder(pic1);
            return picViewHolder;
        } else if (viewType == TYPE_HLIST) {
            View hListView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_hlistview, parent, false);
            HListViewHolder hListViewHolder = new HListViewHolder(hListView);
            return hListViewHolder;
        }else if (viewType == TYPE_PIC2){
            View pic1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.pic2_item, parent, false);
            PicViewHolder picViewHolder = new PicViewHolder(pic1);
            return picViewHolder;
        }else if (viewType == TYPE_HLIST2){
            View hListView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_hlistview, parent, false);
            HListViewHolder hListViewHolder = new HListViewHolder(hListView);
            return hListViewHolder;
        }else if (viewType == TYPE_GALLERY){
            View gallery = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_main, parent, false);
            GalleryViewHolder galleryViewHolder = new GalleryViewHolder(gallery);
            return galleryViewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        int type = getItemViewType(position);
        switch (type) {
            case 0:
                BannerView bannerView = new BannerView(holder.itemView);
                List<String> bannerList = new ArrayList<>();
                for (int i = 0; i < dataBean.getAd1().size(); i++) {
                    bannerList.add(dataBean.getAd1().get(i).getImage());
                }
                bannerView.myBanner.setImageLoader(new BannerImageLoader());
                bannerView.myBanner.setImages(bannerList);
                //bannerView.myBanner.setBannerAnimation(CubeInTransformer.class);
                bannerView.myBanner.start();
                //banner监听
                bannerView.myBanner.setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        Intent intent = new Intent(context, BannerConnectActivity.class);
                        intent.putExtra("bannerUrl", dataBean.getAd1().get(position).getAd_type_dynamic_data());
                        intent.putExtra("bannerTitle", dataBean.getAd1().get(position).getTitle());
                        context.startActivity(intent);
                    }
                });
                break;
            case 1:
                GridViewHolder gridViewHolder = new GridViewHolder(holder.itemView);
                gridViewHolder.gridview_rv.setLayoutManager(new GridLayoutManager(context, 4));
                MyAdapter_gridview myAdapter_gridview = new MyAdapter_gridview(context, dataBean.getAd5());
                //GridViewitem项点击监听
                myAdapter_gridview.setRecycleViewGItemListener(new MyAdapter_gridview.OnRecycleViewGItemListener() {
                    @Override
                    public void recycleViewGItemListener(int position) {
                        if (position == 3) {
                            //Toast.makeText(context, "1234", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, BannerConnectActivity.class);
                            intent.putExtra("bannerUrl", dataBean.getAd5().get(position).getAd_type_dynamic_data());
                            intent.putExtra("bannerTitle", "御泥坊产品—真伪查询");
                            context.startActivity(intent);
                        }
                    }
                });
                //GridViewitem项长按监听
                myAdapter_gridview.setRecycleViewGItemLongListener(new MyAdapter_gridview.OnRecycleViewGItemLongListener() {
                    @Override
                    public void recycleViewGItemLongListener(int position) {

                    }
                });
                //添加适配器
                gridViewHolder.gridview_rv.setAdapter(myAdapter_gridview);
                break;
            case 2:
                final GalleryViewHolder galleryViewHolder = new GalleryViewHolder(holder.itemView);
                galleryViewHolder.countdownView.start(123456789);

                InitFocusIndicatorContainer(galleryViewHolder.ll_focus_indicator_container);
                galleryViewHolder.gallery.setAdapter(new MyAdapter_ImageAdapter(context,dataBean.getActivityInfo().getActivityInfoList()));
                galleryViewHolder.gallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int selIndex, long arg3) {
                        // 修改上一次选中项的背景
                        selIndex = selIndex % dataBean.getActivityInfo().getActivityInfoList().size();

                        ImageView preSelImg = (ImageView) galleryViewHolder.ll_focus_indicator_container
                                .findViewById(preSelImgIndex);
                        preSelImg.setImageDrawable(context.getResources()
                                .getDrawable(R.mipmap.ic_launcher));
                        // 修改当前选中项的背景
                        ImageView curSelImg = (ImageView) galleryViewHolder.ll_focus_indicator_container
                                .findViewById(selIndex);
                        curSelImg.setImageDrawable(context.getResources()
                                .getDrawable(R.mipmap.ic_launcher));
                        preSelImgIndex = selIndex;
                    }

                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                });
                break;
            case 3:
                PicViewHolder picViewHolder = new PicViewHolder(holder.itemView);
                //picViewHolder.image.setScaleType(ImageView.ScaleType.FIT_XY);
                Picasso.with(context).load(dataBean.getSubjects().get(0).getImage()).into(picViewHolder.image);
                break;
            case 4:
                HListViewHolder hListViewHolder = new HListViewHolder(holder.itemView);
                //得到布局管理器  listView
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                //设置listView滑动方向
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                //RecyclerView添加布局管理器
                hListViewHolder.rv_hListView.setLayoutManager(linearLayoutManager);
                //RecyclerView添加适配器
                MyAdapter_HListView myAdapter_hListView = new MyAdapter_HListView(context, dataBean.getSubjects(),0);
                hListViewHolder.rv_hListView.setAdapter(myAdapter_hListView);
                break;
            case 5:
                PicViewHolder picViewHolder2 = new PicViewHolder(holder.itemView);
                //picViewHolder2.image.setScaleType(ImageView.ScaleType.FIT_XY);
                Picasso.with(context).load(dataBean.getSubjects().get(1).getImage()).into(picViewHolder2.image);
                break;
            case 6:
                HListViewHolder hListViewHolder2 = new HListViewHolder(holder.itemView);
                //得到布局管理器  listView
                LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(context);
                //设置listView滑动方向
                linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
                //RecyclerView添加布局管理器
                hListViewHolder2.rv_hListView.setLayoutManager(linearLayoutManager2);
                //RecyclerView添加适配器
                MyAdapter_HListView myAdapter_hListView2 = new MyAdapter_HListView(context, dataBean.getSubjects(),1);
                hListViewHolder2.rv_hListView.setAdapter(myAdapter_hListView2);
                break;
            case 7:
                PblView pblView = new PblView(holder.itemView);
                pblView.pbl_rv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                MyAdapter_pbl myAdapter_pbl = new MyAdapter_pbl(dataBean.getDefaultGoodsList(), context);
                //瀑布流item项点击监听
                myAdapter_pbl.setRecycleViewPItemListener(new MyAdapter_pbl.OnRecycleViewPItemListener() {
                    @Override
                    public void recycleViewPItemListener(int position) {

                    }
                });
                //瀑布流item项长按监听
                myAdapter_pbl.setRecycleViewItemLongListener(new MyAdapter_pbl.OnRecycleViewItemLongListener() {
                    @Override
                    public void recycleViewItemLongListener(int position) {

                    }
                });
                //添加适配器
                pblView.pbl_rv.setAdapter(myAdapter_pbl);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 8;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_BANNER;
        } else if (position == 1) {
            return TYPE_GRID;
        } else if (position == 7) {
            return TYPE_PBL;
        } else if (position == 3) {
            return TYPE_PIC1;
        } else if (position == 4) {
            return TYPE_HLIST;
        }else if (position == 5){
            return TYPE_PIC2;
        }else if (position == 6){
            return TYPE_HLIST2;
        }else if (position == 2){
            return TYPE_GALLERY;
        }
        return TYPE_PBL;
    }

    public class PblView extends RecyclerView.ViewHolder {
        RecyclerView pbl_rv;

        public PblView(View itemView) {
            super(itemView);
            pbl_rv = (RecyclerView) itemView.findViewById(R.id.pbl_rv);
        }
    }

    public class BannerView extends RecyclerView.ViewHolder {
        private Banner myBanner;

        public BannerView(View itemView) {
            super(itemView);
            myBanner = (Banner) itemView.findViewById(R.id.mybanner);
        }
    }

    public class GridViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView gridview_rv;

        public GridViewHolder(View itemView) {
            super(itemView);
            gridview_rv = (RecyclerView) itemView.findViewById(R.id.gridview_rv);
        }
    }

    public class HListViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView rv_hListView;

        public HListViewHolder(View itemView) {
            super(itemView);
            rv_hListView = (RecyclerView) itemView.findViewById(R.id.lv_rv);
        }
    }

    public class PicViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;

        public PicViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.pic1);
        }
    }

    public class GalleryViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout ll_focus_indicator_container = null;
        private MyGallery2 gallery = null;
        private CountdownView countdownView;
        public GalleryViewHolder(View itemView) {
            super(itemView);
            ll_focus_indicator_container = (LinearLayout) itemView.findViewById(R.id.ll_focus_indicator_container);
            gallery = (MyGallery2) itemView.findViewById(R.id.banner_gallery);
            countdownView = (CountdownView) itemView.findViewById(R.id.countdownView);
        }
    }


    private void InitFocusIndicatorContainer(LinearLayout ll_focus_indicator_container) {
        for (int i = 0; i < dataBean.getActivityInfo().getActivityInfoList().size(); i++) {
            ImageView localImageView = new ImageView(context);
            localImageView.setId(i);
            ImageView.ScaleType localScaleType = ImageView.ScaleType.FIT_XY;
            localImageView.setScaleType(localScaleType);
            LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(
                    30, 30);
            localImageView.setLayoutParams(localLayoutParams);
            localImageView.setPadding(5, 5, 5, 5);
            ll_focus_indicator_container.addView(localImageView);
        }
    }
}
