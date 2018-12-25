package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/1/10.
 * 我的好友
 */

public class FriendsApi extends BticmApi {

    public String uid;//用户id,
    public String type;//1、新朋友 2、感兴趣的  不放为好友

    @Override
    protected String getPath() {
        return "friend/index";
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
