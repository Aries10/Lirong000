package com.bwie.shopping.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.shopping.R;
import com.bwie.shopping.activity.CartPayActivity;
import com.bwie.shopping.activity.LogingActivity;
import com.bwie.shopping.adapter.MyAdapter_Cart;
import com.bwie.shopping.api.API;
import com.bwie.shopping.bean.CartBean;
import com.bwie.shopping.bean.GoodsBean;
import com.bwie.shopping.bean.ShopBean;
import com.bwie.shopping.utils.MyUtils;
import com.bwie.shopping.view.CustomExpandableListView;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 1:类的用途
 * 2：@author Dell
 * 3：@date 2017/8/31
 */

public class Fragment_Cart extends Fragment {
    private List<ShopBean> shopList;
    private List<GoodsBean> goodsList;
    private CustomExpandableListView elistview;
    private TextView cartcount, total_price;
    private CheckBox allcheck;
    private Button jiesuancount;
    private MyAdapter_Cart myAdapter_cart;
    private int carnum = 0;
    private Button jiesun;
    private List<GoodsBean> list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        //获取控件
        initView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        elistview.setGroupIndicator(null);
        //判断是否登录
        if (!MyUtils.getSharedPreferencesInstance(getActivity()).getBoolean("islogin", false)) {
            Intent intent = new Intent(getActivity(), LogingActivity.class);
            intent.putExtra("where", "carts");
            startActivity(intent);
        } else {
            postServerData();
            allcheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //得到全选状态
                    boolean checked = allcheck.isChecked();
                    if (checked) {//全选，设置商店和商品为全选
                        int num = 0;
                        float price = 0;
                        for (int i = 0; i < shopList.size(); i++) {
                            shopList.get(i).setsIscheck(true);
                            for (int j = 0; j < shopList.get(i).getGoodsBeanList().size(); j++) {
                                shopList.get(i).getGoodsBeanList().get(j).setgIscheck(true);
                                num += Integer.parseInt(shopList.get(i).getGoodsBeanList().get(j).getGoodsNum());
                                price += Float.parseFloat(shopList.get(i).getGoodsBeanList().get(j).getGoodsTotal());
                            }
                        }
                        total_price.setText("总计:" + price + "");
                        jiesuancount.setText("结算:" + num + "");
                    } else {//全不选，设置商店和商品为全不选
                        for (int i = 0; i < shopList.size(); i++) {
                            shopList.get(i).setsIscheck(false);
                            for (int j = 0; j < shopList.get(i).getGoodsBeanList().size(); j++) {
                                shopList.get(i).getGoodsBeanList().get(j).setgIscheck(false);
                            }
                        }
                        total_price.setText("总计:" + 0 + "");
                        jiesuancount.setText("结算:" + 0 + "");
                    }
                    //刷新适配器
                    myAdapter_cart.notifyDataSetChanged();
                }
            });
        }

        //结算点击监听
        jiesun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list = new ArrayList<GoodsBean>();
                //遍历所以子数据   把选中的添加到 集合
                for (int i = 0; i < shopList.size(); i++) {
                    shopList.get(i).setsIscheck(false);
                    for (int j = 0; j < shopList.get(i).getGoodsBeanList().size(); j++) {
                        if (shopList.get(i).getGoodsBeanList().get(j).isgIscheck()) {
                            list.add(shopList.get(i).getGoodsBeanList().get(j));
                        }
                    }
                }
                //传值
                Intent intent = new Intent(getActivity(), CartPayActivity.class);
                intent.putExtra("goodslist", (Serializable) list);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void initView(View v) {
        elistview = (CustomExpandableListView) v.findViewById(R.id.elv);
        cartcount = (TextView) v.findViewById(R.id.cartcount);
        allcheck = (CheckBox) v.findViewById(R.id.allcheck);
        jiesuancount = (Button) v.findViewById(R.id.jiesuancount);
        total_price = (TextView) v.findViewById(R.id.total_price);
        jiesun = (Button) v.findViewById(R.id.jiesuancount);
    }

    //请求购物车数据
    public void postServerData() {
        RequestParams params = new RequestParams(API.CART_PATH);
        params.addBodyParameter("key", MyUtils.getSharedPreferencesInstance(getActivity()).getString("key", ""));
        params.setMultipart(true);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //解析得到的数据
                Gson gson = new Gson();
                CartBean cartBean = gson.fromJson(result, CartBean.class);
                //实例集合
                shopList = new ArrayList<ShopBean>();
                for (int i = 0; i < cartBean.getDatas().getCart_list().size(); i++) {
                    goodsList = new ArrayList<GoodsBean>();
                    for (int j = 0; j < cartBean.getDatas().getCart_list().get(i).getGoods().size(); j++) {
                        goodsList.add(new GoodsBean(cartBean.getDatas().getCart_list().get(i).getGoods().get(j).getGoods_name(), cartBean.getDatas().getCart_list().get(i).getGoods().get(j).getGoods_price(),
                                cartBean.getDatas().getCart_list().get(i).getGoods().get(j).getCart_id(), cartBean.getDatas().getCart_list().get(i).getGoods().get(j).getGoods_image_url(),
                                cartBean.getDatas().getCart_list().get(i).getGoods().get(j).getGoods_num(), cartBean.getDatas().getCart_list().get(i).getGoods().get(j).getGoods_total(),
                                cartBean.getDatas().getCart_list().get(i).getGoods().get(j).getStore_name(), false));
                        //统计购物车所有商品数量
                        carnum += Integer.parseInt(cartBean.getDatas().getCart_list().get(i).getGoods().get(j).getGoods_num());
                    }
                    shopList.add(new ShopBean(cartBean.getDatas().getCart_list().get(i).getStore_name(), false, goodsList));
                }
                //赋值
                cartcount.setText("购物车(" + carnum + ")");
                //添加适配器
                myAdapter_cart = new MyAdapter_Cart(getActivity(), shopList);
                elistview.setAdapter(myAdapter_cart);
                for (int i = 0; i < myAdapter_cart.getGroupCount(); i++) {
                    elistview.expandGroup(i);
                }
                //根据组和子的选择状态控制全选框
                myAdapter_cart.setOnChecked(new MyAdapter_Cart.OnChecked() {
                    @Override
                    public void checked(boolean tag) {
                        allcheck.setChecked(tag);
                    }
                });
                //根据组和子的选择状态设置总计
                myAdapter_cart.setOnPriceTotal(new MyAdapter_Cart.OnPriceTotal() {
                    @Override
                    public void priceTotal(float price) {
                        total_price.setText("总计：" + price + "");
                    }
                });
                //根据组和子的选择状态设置结算个数
                myAdapter_cart.setOnCountTotal(new MyAdapter_Cart.OnCountTotal() {
                    @Override
                    public void countTotal(int count) {
                        jiesuancount.setText("结算" + count + "");
                    }
                });
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
