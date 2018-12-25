package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/1/11.
 * 关于我们
 */

public class AboutApi extends BticmApi{

    public String title;


    @Override
    protected String getPath() {
        return "Index/News";
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
