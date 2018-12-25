package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/1/3.
 * 首页 币头条详情 评论
 */

public class HomeBiDetailsCommApi extends BticmApi{

    public String id;//币头条数据ID
    public String content;//评论内容
    public String uid;//评论人ID

    @Override
    protected String getPath() {
        return "Post/do_reviews";
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
