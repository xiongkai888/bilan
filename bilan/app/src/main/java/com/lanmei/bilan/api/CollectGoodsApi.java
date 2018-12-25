package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/1/18.
 * 收藏、取消收藏产品
 */

public class CollectGoodsApi extends BticmApi {

    public String uid;//用户ID
    public String pro_id;//商品ID


    @Override
    protected String getPath() {
        return "app/favorite";
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
