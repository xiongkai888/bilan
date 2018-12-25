package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/1/10.
 * 币种发布
 */

public class BiClassPublishApi extends BticmApi{

    public String classid;//币种ID

    public String cudo;//1|0=>进|出

    public String num;//数量

    public String price;//价格

    public String userid;//用户ID


    @Override
    protected String getPath() {
        return "app/addotc";
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
