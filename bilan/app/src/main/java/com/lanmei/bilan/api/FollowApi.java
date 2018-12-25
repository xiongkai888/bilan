package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/1/10.
 * 关注|取消关注
 */

public class FollowApi extends BticmApi{

    public String uid;//用户ID
    public String mid;//关注的人的数据ID
    public String type;//0|1|2 =>普通会员|名人堂|公众号

    @Override
    protected String getPath() {
        return "MemberFollow/follow";
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
