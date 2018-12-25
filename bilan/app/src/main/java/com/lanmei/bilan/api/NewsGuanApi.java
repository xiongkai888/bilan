package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/1/3.
 * 资讯关注
 */

public class NewsGuanApi extends BticmApi{

    public String uid;

    @Override
    protected String getPath() {
        return "app/myfocusonpost";
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
