package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/1/8.
 * 身份  0|1|2 => 普通会员|名人堂|公众号
 */

public class HomeMingApi extends BticmApi{

    public String uid;//用户ID
    public String user_type;//0|1|2 =>普通会员|名人堂|公众号

    @Override
    protected String getPath() {
        return "app/getuser2";
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
