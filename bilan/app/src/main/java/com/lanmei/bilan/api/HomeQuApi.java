package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/1/3.
 * 首页 区块圈 (我的动态)
 */

public class HomeQuApi extends BticmApi{

    public String uid;//用户ID
    public String token;//用户ID
    public String type;//贴子类型：1、文字2、图片3、视频'

    @Override
    protected String getPath() {
        return "posts/index";
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
