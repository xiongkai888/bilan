package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/1/11.
 * 修改资讯
 */

public class CompileArticleApi extends BticmApi{

    public String uid;
    public String title;
    public String content;
    public String imgs;
    public String postid;


    @Override
    protected String getPath() {
        return "app/editmypost";
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
