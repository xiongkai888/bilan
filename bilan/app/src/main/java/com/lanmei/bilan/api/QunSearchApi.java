package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/2/29.
 * 群搜索
 */

public class QunSearchApi extends BticmApi{

    public String name;

    @Override
    protected String getPath() {
        return "app/cr";
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
