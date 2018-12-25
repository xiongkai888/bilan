package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/1/3.
 * 行情 （币种信息分类）
 */

public class MarketBiCategory2Api extends BticmApi {

    public int limit = 20;//
//    public String convert="EUR";//

    @Override
    protected String getPath() {
        return "app/hqcontent";
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
