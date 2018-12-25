package com.lanmei.bilan.api;

/**
 * Created by Administrator on 2017/5/24.
 * 文章删除(删除资讯)
 */

public class DeleteArticleApi extends BticmApi {

    public String postid;//贴子id
    public String uid;//

    @Override
    protected String getPath() {
        return "app/delmypost";
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
