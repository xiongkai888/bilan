package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/1/3.
 * 首页 区块圈详情 评论列表
 */

public class HomeQuDetailsCommListApi extends BticmApi{

    public String posts_id;//区块圈ID

    @Override
    protected String getPath() {
        return "PostsReviews/index";
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
