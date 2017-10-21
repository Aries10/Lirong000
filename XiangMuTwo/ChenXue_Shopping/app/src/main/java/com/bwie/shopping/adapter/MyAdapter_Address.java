package com.bwie.shopping.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.bwie.shopping.R;
import com.bwie.shopping.activity.AddAddressActivity;
import com.bwie.shopping.activity.AddressActivity;
import com.bwie.shopping.bean.AddressBean;
import com.bwie.shopping.utils.MyUtils;

import java.util.List;

/**
 * 1:类的用途
 * 2：@author Dell
 * 3：@date 2017/9/14
 */

public class MyAdapter_Address extends BaseAdapter {
    private Context context;
    private List<AddressBean.DatasBean.AddressListBean> list;

    public MyAdapter_Address(Context context, List<AddressBean.DatasBean.AddressListBean> list) {
        this.context = context;
        this.list = list;
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
        ViewHolder holder;
        if (convertView == null) {
            //得到布局
            convertView = View.inflate(context, R.layout.address_item, null);
            //实例ViewHolder
            holder = new ViewHolder();
            //获取控件
            holder.address = (TextView) convertView.findViewById(R.id.address);
            holder.defult = (CheckBox) convertView.findViewById(R.id.setdefult);
            holder.name = (TextView) convertView.findViewById(R.id.username);
            holder.phone = (TextView) convertView.findViewById(R.id.userphone);
            holder.update = (TextView) convertView.findViewById(R.id.update);
            holder.delete = (TextView) convertView.findViewById(R.id.delete);
            convertView.setTag(holder);
        } else {
            //复用
            holder = (ViewHolder) convertView.getTag();
        }
        //适配
        holder.name.setText(list.get(position).getTrue_name());
        holder.phone.setText(list.get(position).getMob_phone());
        holder.address.setText(list.get(position).getAddress());
        if (list.get(position).getAddress_id().equals(MyUtils.getSharedPreferencesInstance(context).getString("address_id", ""))) {
            holder.defult.setChecked(true);
        } else if (position == 0) {
            holder.defult.setChecked(true);
            SharedPreferences.Editor edit = MyUtils.getSharedPreferencesInstance(context).edit();
            edit.putString("address_id", list.get(position).getAddress_id());
            edit.commit();
        }
        //check点击
        holder.defult.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor edit = MyUtils.getSharedPreferencesInstance(context).edit();
                edit.putString("address_id", list.get(position).getAddress_id());
                edit.commit();
                onChangeDefault.changeDefault(true);
                notifyDataSetChanged();
            }
        });
        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddAddressActivity.class);
                intent.putExtra("true_name", list.get(position).getTrue_name());
                intent.putExtra("mob_phone", list.get(position).getMob_phone());
                intent.putExtra("address", list.get(position).getAddress());
                intent.putExtra("addressInfo", list.get(position).getArea_info());
                intent.putExtra("address_id", list.get(position).getAddress_id());
                intent.putExtra("click", "bianji");
                context.startActivity(intent);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteAddress.deleteAddress(list.get(position).getAddress_id());
            }
        });
        return convertView;
    }

    class ViewHolder {
        private TextView name;
        private TextView phone;
        private TextView address;
        private TextView update;
        private TextView delete;
        private CheckBox defult;
    }

    //声明成员变量
    private onChangeDefault onChangeDefault;
    private onDeleteAddress onDeleteAddress;

    //接口传值
    public interface onChangeDefault {
        void changeDefault(boolean flag);
    }

    public interface onDeleteAddress {
        void deleteAddress(String address_id);
    }

    //提供set方法
    public void setOnChangeDefault(MyAdapter_Address.onChangeDefault onChangeDefault) {
        this.onChangeDefault = onChangeDefault;
    }

    public void setOnDeleteAddress(MyAdapter_Address.onDeleteAddress onDeleteAddress) {
        this.onDeleteAddress = onDeleteAddress;
    }
}
