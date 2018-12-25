package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/1/16.
 * 我的订单
 */

public class MineOrderApi extends BticmApi{

    public String uid;//用户ID
    public int status;//0|1|2|3=>全部|未支付|待收货|已完成

    @Override
    protected String getPath() {
        return "app/ordersel";
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
