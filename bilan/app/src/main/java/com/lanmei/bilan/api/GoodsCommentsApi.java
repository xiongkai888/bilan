package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/1/18.
 * 商品评论列表
 */

public class GoodsCommentsApi extends BticmApi {


    public String proid;//产品ID

    @Override
    protected String getPath() {
        return "app/pro_comments";
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
