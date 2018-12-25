package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/1/9.
 * 应用推荐分类
 */

public class AppRecommendSubApi extends BticmApi {


    public String classid;// 类别数据ID


    @Override
    protected String getPath() {
        return "app/getApprecommend";
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
