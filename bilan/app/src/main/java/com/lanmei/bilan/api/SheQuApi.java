package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/1/16.
 * 社区分类
 */

public class SheQuApi extends BticmApi {
    @Override
    protected String getPath() {
        return "app/getclass2";
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
