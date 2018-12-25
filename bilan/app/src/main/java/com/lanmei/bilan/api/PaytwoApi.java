package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/1/26.
 * 余额 二次 支付
 */

public class PaytwoApi extends BticmApi {
    public String oid;//订单数据ID
    public String uid;//
    public String pay_type = "3";//固定值


    @Override
    protected String getPath() {
        return "app/paytwo";
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
