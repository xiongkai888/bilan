package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/1/10.
 * 区块圈 点赞
 */

public class HomeQuLikeApi extends BticmApi {

    public String id;//区块圈ID
    public String uid;//用户数据ID

    @Override
    protected String getPath() {
        return "posts/like";
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
