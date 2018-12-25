package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/1/10.
 * 我发布的资讯(文章列表)列表
 */

public class MineArticleApi extends BticmApi {

    public String uid;// 发布人ID

    @Override
    protected String getPath() {
        return "app/mypost";
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
