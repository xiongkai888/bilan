package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/1/10.
 * 我的关注
 */

public class MineFollowApi extends BticmApi {

    public String uid;//用户ID
    public String followed;//是否关注
    public String is_friend;//是否好友
    public String type;//0|1|2 =>普通会员|名人堂|公众号

    @Override
    protected String getPath() {
        return "MemberFollow/index";
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
