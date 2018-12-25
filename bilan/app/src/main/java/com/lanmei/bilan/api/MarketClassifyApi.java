package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/6/26.
 * 行情分类
 */

public class MarketClassifyApi extends BticmApi{


    @Override
    protected String getPath() {
        return "app/hq";
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
