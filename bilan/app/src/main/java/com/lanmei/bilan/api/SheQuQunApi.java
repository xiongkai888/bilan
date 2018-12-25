package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/1/9.
 *消息 社区 群组列表
 */

public class SheQuQunApi extends BticmApi{


    public String classid;// 社区分类ID

    @Override
    protected String getPath() {
        return "app/chat_room";
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
