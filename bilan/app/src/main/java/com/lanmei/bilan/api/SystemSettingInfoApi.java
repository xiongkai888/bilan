package com.lanmei.bilan.api;

/**
 * Created by xkai on 2017/7/18.
 * 系统基本设置信息
 */

public class SystemSettingInfoApi extends BticmApi {

    @Override
    protected String getPath() {
        return "app/customer_service";
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
