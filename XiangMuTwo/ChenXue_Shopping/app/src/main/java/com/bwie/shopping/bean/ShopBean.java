package com.bwie.shopping.bean;

import java.util.List;

/**
 * 1:类的用途
 * 2：@author Dell
 * 3：@date 2017/9/13
 */

public class ShopBean {
    private String shopName;
    private boolean sIscheck;
    private List<GoodsBean> goodsBeanList;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public boolean issIscheck() {
        return sIscheck;
    }

    public void setsIscheck(boolean sIscheck) {
        this.sIscheck = sIscheck;
    }

    public List<GoodsBean> getGoodsBeanList() {
        return goodsBeanList;
    }

    public void setGoodsBeanList(List<GoodsBean> goodsBeanList) {
        this.goodsBeanList = goodsBeanList;
    }

    public ShopBean(String shopName) {

        this.shopName = shopName;
    }

    public ShopBean(String shopName, boolean sIscheck, List<GoodsBean> goodsBeanList) {

        this.shopName = shopName;
        this.sIscheck = sIscheck;
        this.goodsBeanList = goodsBeanList;
    }
}
