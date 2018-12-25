package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/2/3.
 * 区块圈轮播图
 */

public class AdPicApi extends BticmApi{

    public String classid;

    @Override
    protected String getPath() {
        return "app/adpic";
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
