package com.lanmei.bilan.api;

/**
 * Created by xkai on 2017/7/4.
 * 好友排名
 */

public class FriendRankApi extends BticmApi {


    public String uid;//用户id,
    public String token;

    @Override
    protected String getPath() {
        return "friend/rank";
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
