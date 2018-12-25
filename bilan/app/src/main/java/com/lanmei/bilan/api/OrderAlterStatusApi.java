package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/1/26.
 * 修改订单
 */

public class OrderAlterStatusApi extends BticmApi {

    public String uid;//用户ID

    public String oid;//订单ID
    public String if_del;//

    public String status;//状态值 1|2|3|4|5|6 =>生成订单|确认订单|取消订单|作废订单|完成订单|申请退款

    @Override
    protected String getPath() {
        return "app/uporder";
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
