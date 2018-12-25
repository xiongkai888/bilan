package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/1/3.
 * 首页 币头条
 */

public class HomeBiApi extends BticmApi{

    public String cid;//分类ID（1 =》 币头条）
    public String uid;//分类ID（1 =》 币头条）

    @Override
    protected String getPath() {
        return "Post/index";
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
