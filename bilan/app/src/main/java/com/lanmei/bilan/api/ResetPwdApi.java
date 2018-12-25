package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/2/23.
 */

public class ResetPwdApi extends BticmApi {

    public String phone;
    public String password;

    @Override
    protected String getPath() {
        return "app/changePassword";
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
