package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/1/10.
 * 行情  场外发布
 */

public class MarketChangApi extends BticmApi{

    @Override
    protected String getPath() {
        return "app/otcpublish";
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
