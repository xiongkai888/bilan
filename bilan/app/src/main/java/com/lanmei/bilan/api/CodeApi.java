package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/1/9.
 * 获取验证码
 */

public class CodeApi  extends BticmApi{

    public String phone;
    public String code;

    @Override
    protected String getPath() {
        return "app/send";
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
