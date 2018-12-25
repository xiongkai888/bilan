package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/1/10.
 * 发现
 */

public class DiscoverApi extends BticmApi{


    @Override
    protected String getPath() {
        return "app/brand";
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
