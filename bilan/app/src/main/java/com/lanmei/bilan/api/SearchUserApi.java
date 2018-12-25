package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/1/16.
 * 搜索用户好友
 */

public class SearchUserApi extends BticmApi {
    public String keyword;//关键字
    public String token;
    public String uid;//用户id

    @Override
    protected String getPath() {
        return "member/search";
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
