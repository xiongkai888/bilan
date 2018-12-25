package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/1/3.
 * 首页 币头条详情 评论列表
 */

public class HomeBiDetailsCommListApi extends BticmApi{

    public String id;//币头条数据ID
    public String last_id;//评论人ID

    @Override
    protected String getPath() {
        return "Post/reviews";
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
