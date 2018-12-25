package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/1/26.
 */

public class OrderDetailsApi extends BticmApi {

    public String oid;//订单ID
    public String uid;//订单ID

    @Override
    protected String getPath() {
        return "api/app/order_info";
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
