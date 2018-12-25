package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/1/10.
 * 我的粉丝
 */

public class MineFansApi extends BticmApi {

    public String uid;//用户ID

    @Override
    protected String getPath() {
        return "MemberFollow/fans";
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
