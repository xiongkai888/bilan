package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/1/8.
 * 链世界
 */

public class HomeLianApi extends BticmApi {

    public String classid;//类别数据ID

    @Override
    protected String getPath() {
        return "app/getlinkworld";
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
