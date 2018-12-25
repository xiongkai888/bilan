package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/2/9.
 * 系统基本配置
 */

public class SiteinfoApi extends BticmApi {

    @Override
    protected String getPath() {
        return "index/siteinfo";
    }

    @Override
    public Method requestMethod() {
        return Method.POST  ;
    }
}
