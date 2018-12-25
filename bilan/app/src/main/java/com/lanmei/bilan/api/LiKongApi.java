package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/6/25.
 */

public class LiKongApi extends BticmApi{

    public String uid;//
    public String posts_id;//快讯数据ID

    @Override
    protected String getPath() {
        return "app/postunlike";
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
