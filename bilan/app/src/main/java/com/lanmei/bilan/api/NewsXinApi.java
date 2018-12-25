package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/1/3.
 * 资讯 新闻
 */

public class NewsXinApi extends BticmApi{

    public String uid;

    @Override
    protected String getPath() {
        return "app/newspost";
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
