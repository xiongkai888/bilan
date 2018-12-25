package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/1/3.
 * 首页 区块圈详情 评论
 */

public class HomeQuDetailsCommApi extends BticmApi{

    public String posts_id;//区块圈ID
    public String content;//评论内容
    public String uid;//
    public String at_uid;//@的uid

    @Override
    protected String getPath() {
        return "PostsReviews/add";
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
