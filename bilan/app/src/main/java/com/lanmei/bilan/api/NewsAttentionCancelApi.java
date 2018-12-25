package com.lanmei.bilan.api;

/**
 * Created by Administrator on 2017/5/20.
 * 资讯  关注
 */

public class NewsAttentionCancelApi extends BticmApi {

    public String userid;//作者ID
    public String uid;//

    @Override
    protected String getPath() {
        return "app/focuscancel";
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
