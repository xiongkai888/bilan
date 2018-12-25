package com.lanmei.bilan.api;

/**
 * Created by Administrator on 2017/5/24.
 * 帖子删除
 */

public class DeleteDynamicApi extends BticmApi {

    public String id;//贴子id
    public String uid;//
    public String token;

    @Override
    protected String getPath() {
        return "posts/del";
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
