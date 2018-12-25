package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/1/10.
 * 我的发布
 */

public class MinePublishApi extends BticmApi {

    public String userid;// 发布人ID

    @Override
    protected String getPath() {
        return "app/otcpublish";
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
