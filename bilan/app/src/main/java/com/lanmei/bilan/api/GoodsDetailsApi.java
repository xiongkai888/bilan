package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/1/19.
 * 商品详情
 */

public class GoodsDetailsApi extends BticmApi {
    public String id;
    public String uid;
    @Override
    protected String getPath() {
        return "Shop/goods_details";
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
