package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/1/11.
 * 用户信息获取
 */

public class GetUserInfoApi extends BticmApi {

    public String  uid;//用户数据ID，多个用户用,号隔开
    public String  gold;//积分

    @Override
    protected String getPath() {
        return "Member/index";
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
