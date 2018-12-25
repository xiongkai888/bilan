package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/3/1.
 * 名人堂简介
 */

public class BriefApi extends BticmApi {

    public String uid;
    public String fid;//

    @Override
    protected String getPath() {
        return "app/seeyou";
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
