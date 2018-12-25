package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/1/3.
 * 资讯 精选
 */

public class NewsJingApi extends BticmApi{

    public String uid;

    @Override
    protected String getPath() {
        return "app/siftpost";
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
