package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/1/16.
 */

public class HuanXiQunApi extends BticmApi {

    public String classid;//社区分类ID

    @Override
    protected String getPath() {
        return "app/chat_room";
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
