package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/2/6.
 * 帐号 积分流水列表
 */

public class JiListApi extends BticmApi {


    public String uid;//用户ID
    public String type;//0|1 =>充值|消费


    @Override
    protected String getPath() {
        return "app/score_log";
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
