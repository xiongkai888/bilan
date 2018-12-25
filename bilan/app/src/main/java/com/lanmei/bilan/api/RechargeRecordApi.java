package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/1/24.
 * 消费明细、充值记录
 */

public class RechargeRecordApi extends BticmApi{

    public String uid;//用户ID

    public int type;//0|1 =>充值|消费


    @Override
    protected String getPath() {
            return "app/user_money";
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
