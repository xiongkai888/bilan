package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/1/26.
 * 订单评论
 */

public class OrderCommentsApi extends BticmApi {

    public String uid;//
    public String orderid;//订单ID

    public String content;//评论内容
    public String proid;// 产品ID  多个产品之间用英文逗号(,)隔开

    public int point;//  评价星级

    @Override
    protected String getPath() {
        return "app/order_comments";
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
