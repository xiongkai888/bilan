package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/2/6.
 * 积分转让
 */

public class JiTransferApi extends BticmApi {


    public String userid;//用户ID
    public String price;//转让积分

    public String payee;//转让用户ID

    public String content;//备注


    @Override
    protected String getPath() {
        return "app/transfer";
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
