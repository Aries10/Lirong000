package com.bwie.shopping.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.bwie.shopping.R;
import com.bwie.shopping.api.API;
import com.bwie.shopping.bean.TypeSon2Bean;
import com.bwie.shopping.bean.TypeSonBean;
import com.bwie.shopping.utils.GetServerData;
import com.google.gson.Gson;

import java.util.List;

/**
 * 1:类的用途
 * 2：@author Dell
 * 3：@date 2017/9/8
 */

public class MyAdapter_TypeSon extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private List<TypeSonBean.DatasBean.ClassListBean> list;

    public MyAdapter_TypeSon(Context context, List<TypeSonBean.DatasBean.ClassListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.typeson_item, parent, false);
        final MyLeftViewHolder leftViewHolder = new MyLeftViewHolder(view);
        return leftViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //设置种类标题
        final MyLeftViewHolder myHolder = new MyLeftViewHolder(holder.itemView);
        //设置标题
        myHolder.tv_left_type.setText(list.get(position).getGc_name());
        //第三次请求网络 获取第三级数据
        GetServerData.getServerData(context, API.TYPE_PATH + "&gc_id=" + list.get(position).getGc_id(), new GetServerData.OnGetServerDateLisnter() {
            @Override
            public void getData(String string) {
                Gson gson = new Gson();
                TypeSon2Bean son2Bean = gson.fromJson(string, TypeSon2Bean.class);
                myHolder.gv.setAdapter(new MyAdapter_TypeGridView(context,son2Bean.getDatas().getClass_list()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyLeftViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_left_type;
        private GridView gv;
        public MyLeftViewHolder(View itemView) {
            super(itemView);
            tv_left_type = (TextView) itemView.findViewById(R.id.tv_type);
            gv = (GridView) itemView.findViewById(R.id.type_son);
        }
    }

    //声明成员变量
    public OnRecycleViewItemClickListener recycleViewItemClickListener;

    //定义点击接口
    public interface OnRecycleViewItemClickListener{
        void recycleViewItemClickListener(int position, View view, RecyclerView.ViewHolder viewHolder);
    }

    //提供set方法
    public void setRecycleViewItemClickListener(OnRecycleViewItemClickListener recycleViewItemClickListener) {
        this.recycleViewItemClickListener = recycleViewItemClickListener;
    }
}
