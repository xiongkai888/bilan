package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/6/25.
 */

public class SignInListApi extends BticmApi {

    public String uid;

    @Override
    protected String getPath() {
        return "app/signinlist";
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
