package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/1/11.
 *
 */

public class BlApi extends BticmApi{

    public String id;


    @Override
    protected String getPath() {
        return "app/apphomepage";
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
