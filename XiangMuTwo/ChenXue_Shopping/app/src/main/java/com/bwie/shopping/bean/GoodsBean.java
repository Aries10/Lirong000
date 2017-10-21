package com.bwie.shopping.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 1:类的用途
 * 2：@author Dell
 * 3：@date 2017/9/13
 */

public class GoodsBean implements Serializable{
    private String goodsName;
    private String goodsPrice;
    private String cart_id;
    private String goodsPic;
    private String goodsNum;
    private String goodsTotal;
    private String shopName;
    private boolean gIscheck;

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getCart_id() {
        return cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }

    public String getGoodsPic() {
        return goodsPic;
    }

    public void setGoodsPic(String goodsPic) {
        this.goodsPic = goodsPic;
    }

    public String getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(String goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getGoodsTotal() {
        return goodsTotal;
    }

    public void setGoodsTotal(String goodsTotal) {
        this.goodsTotal = goodsTotal;
    }

    public boolean isgIscheck() {
        return gIscheck;
    }

    public void setgIscheck(boolean gIscheck) {
        this.gIscheck = gIscheck;
    }

    public GoodsBean() {

    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public GoodsBean(String goodsName, String goodsPrice, String cart_id, String goodsPic, String goodsNum, String goodsTotal, String shopName, boolean gIscheck) {
        this.goodsName = goodsName;
        this.goodsPrice = goodsPrice;
        this.cart_id = cart_id;
        this.goodsPic = goodsPic;
        this.goodsNum = goodsNum;
        this.goodsTotal = goodsTotal;
        this.shopName = shopName;
        this.gIscheck = gIscheck;
    }
}
