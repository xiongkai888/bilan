package com.lanmei.bilan.api;

/**
 * Created by Administrator on 2017/5/20.
 * 资讯  关注
 */

public class NewsAttentionApi extends BticmApi {

    public String postid;//资讯id
    public String uid;//

    @Override
    protected String getPath() {
        return "app/focuson";
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
