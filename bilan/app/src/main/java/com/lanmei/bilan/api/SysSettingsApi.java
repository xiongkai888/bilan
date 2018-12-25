package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/2/9.
 * 分享链接和二维码图片
 */

public class SysSettingsApi extends BticmApi {

    public String uid;

    @Override
    protected String getPath() {
        return "public/sys_settings";
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
