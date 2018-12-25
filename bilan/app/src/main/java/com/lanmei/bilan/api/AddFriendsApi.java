package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/1/11.
 * 加为好友
 */

public class AddFriendsApi extends BticmApi {

    public String mid;
    public String uiserid;
    public String uid;
    public String token;

    @Override
    protected String getPath() {
        return "friend/add";
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
