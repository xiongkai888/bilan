package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/1/10.
 * 币种分类
 */

public class BiClassApi extends BticmApi{
    @Override
    protected String getPath() {
        return "app/getclass3";
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
