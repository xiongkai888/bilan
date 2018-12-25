package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/1/3.
 * 首页 币头条详情
 */

public class HomeBiDetailsApi extends BticmApi{

    public String postid;//币头条数据ID
    public String uid;//币头条数据ID

    @Override
    protected String getPath() {
        return "app/postdetails";
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
