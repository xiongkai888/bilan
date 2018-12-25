package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/1/8.
 * 登录
 */

public class LoginApi extends BticmApi{

    public String phone;
    public String password;

    @Override
    protected String getPath() {
        return "app/login";
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
