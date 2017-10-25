package com.bawei.xiangmutwo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.okhttplibrary.utils.GsonObjectCallback;
import com.bawei.okhttplibrary.utils.OkhttpUtils;
import com.bawei.xiangmutwo.R;
import com.bawei.xiangmutwo.activity.AddAddressActivity;
import com.bawei.xiangmutwo.api.Api;
import com.bawei.xiangmutwo.bean.AddressBean;
import com.bawei.xiangmutwo.bean.UnregBean;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by dell-pc on 2017/10/21.
 */

public class MyAddressAdapter extends BaseAdapter {

    private Context context;
    private List<AddressBean.DatasBean.AddressListBean> list;

    public MyAddressAdapter(Context context, List<AddressBean.DatasBean.AddressListBean> list) {
        this.context = context;
        this.list = list;
    }

    public void delete(int position){
        this.list.remove(position);
        notifyDataSetChanged();
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
        MyViewHolder myViewHolder = null;
        if(convertView == null){
            myViewHolder = new MyViewHolder();
            convertView = View.inflate(context, R.layout.item_address,null);
            myViewHolder.item_true_name = (TextView) convertView.findViewById(R.id.item_true_name);
            myViewHolder.item_mob_phone = (TextView) convertView.findViewById(R.id.item_mob_phone);
            myViewHolder.item_address = (TextView) convertView.findViewById(R.id.item_address);
            myViewHolder.item_addressupdate = (TextView) convertView.findViewById(R.id.item_addressupdate);
            myViewHolder.item_addressdelete = (TextView) convertView.findViewById(R.id.item_addressdelete);
            convertView.setTag(myViewHolder);
        }else{
            myViewHolder = (MyViewHolder) convertView.getTag();
        }
        final AddressBean.DatasBean.AddressListBean addressListBean = list.get(position);
        myViewHolder.item_true_name.setText(addressListBean.getTrue_name());
        myViewHolder.item_mob_phone.setText(addressListBean.getMob_phone());
        myViewHolder.item_address.setText(addressListBean.getAddress());
        myViewHolder.item_addressupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddAddressActivity.class);
                intent.putExtra("true_name",addressListBean.getTrue_name());
                intent.putExtra("mob_phone",addressListBean.getMob_phone());
                intent.putExtra("address",addressListBean.getAddress());
                intent.putExtra("area_info",addressListBean.getArea_info());
                intent.putExtra("addressid",addressListBean.getAddress_id());
                intent.putExtra("click","edit");
                context.startActivity(intent);
            }
        });
        myViewHolder.item_addressdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,String> map = new HashMap<String, String>();
                map.put("key",Api.sharedPreferences.getString("key",""));
                map.put("address_id",Api.sharedPreferences.getString("address_id",""));
                OkhttpUtils.doPost(map, Api.DeleteURL, new GsonObjectCallback<UnregBean>() {
                    @Override
                    public void onUi(UnregBean unregBean) {
                        if(unregBean.getCode()==200){
                            Toast.makeText(context,"地址删除成功",Toast.LENGTH_SHORT).show();
                            delete(position);
                        }
                    }

                    @Override
                    public void onFailed(Call call, IOException e) {

                    }
                });
            }
        });
        return convertView;
    }

    class MyViewHolder{
        TextView item_true_name,item_mob_phone,item_address,item_addressupdate,item_addressdelete;
    }

}
