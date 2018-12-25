package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/1/3.
 * 行情 （币种信息分类）
 */

public class MarketBiCategoryApi extends BticmApi{

    public String state2;//

    @Override
    protected String getPath() {
        return "app/getclass3";
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
