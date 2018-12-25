package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/1/18.
 * 收藏产品列表
 */

public class CollectGoodsListApi extends BticmApi {

    public String uid;//用户ID

    @Override
    protected String getPath() {
        return "app/favoriteall";
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
