package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/1/24.
 * 我的银行卡, 包括添加，删除，列表(没分页)
 */

public class BurseApi extends BticmApi {

    public String uid;//查看指定人的，比如自己的
    public String del;//1、表示删除操作
    public String id;//如果有表示更新
    public String token;//只传token表示列表
    public String banks_name;
    public String realname;
    public String banks_no;

    @Override
    protected String getPath() {
        return "member/bank_card";
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
