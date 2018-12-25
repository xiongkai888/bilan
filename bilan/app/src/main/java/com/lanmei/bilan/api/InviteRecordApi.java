package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/7/2.
 */

public class InviteRecordApi extends BticmApi{

    public String uid;

    @Override
    protected String getPath() {
        return "app/recomment";
    }

    @Override
    public Method requestMethod() {
        return null;
    }
}
