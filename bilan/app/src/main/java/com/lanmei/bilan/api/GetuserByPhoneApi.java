package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/2/23.
 * 通过手机获取用户
 */

public class GetuserByPhoneApi extends BticmApi {

    public String phone;

    @Override
    protected String getPath() {
        return "app/phone_getuser";
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
