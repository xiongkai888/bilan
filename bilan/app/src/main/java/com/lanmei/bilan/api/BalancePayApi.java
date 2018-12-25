package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/1/23.
 * 余额支付
 */

public class BalancePayApi extends BticmApi {

    public String pay_type = "6";//固定值

    public String userid;//用户ID

    public String pro_id;//产品ID（多件商品中间用英文逗号（,）隔开）

    public String num;//产品数量（多件商品数量中间用,（英文逗号）隔开，与商品ID对应）
    public String uname;//收获人姓名

    public String uphone;//收货人手机号

    public String uarea;//收获地址

    public String address_id;//收获地址

    @Override
    protected String getPath() {
        return "app/pay";
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
