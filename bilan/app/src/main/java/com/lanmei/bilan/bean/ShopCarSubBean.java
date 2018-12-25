package com.lanmei.bilan.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/22.
 */

public class ShopCarSubBean {
    private int storeId;
    private String storeName;
    private List<ShopCarBean> goodsList;
    private Double money;
    private double freight;
    private int distribution;
    private int goodsNum;
    private boolean isSelect;

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public List<ShopCarBean> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<ShopCarBean> goodsList) {
        this.goodsList = goodsList;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public int getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(int goodsNum) {
        this.goodsNum = goodsNum;
    }

    public double getFreight() {
        return freight;
    }

    public void setFreight(double freight) {
        this.freight = freight;
    }



    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getDistribution() {
        return distribution;
    }

    public void setDistribution(int distribution) {
        this.distribution = distribution;
    }
}
