package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/1/11.
 * 发布文章
 */

public class PublishArticleApi extends BticmApi {

    public String title;//标题，*更新时选填

    public String content;//内容，*更新时选填

    public String imgs;//图片集合，（多个图片用英文（,）逗号隔开）

    public String uid;//用户数据ID

    @Override
    protected String getPath() {
        return "app/issuepost";
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
