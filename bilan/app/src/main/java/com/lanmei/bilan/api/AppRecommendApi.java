package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/1/9.
 *应用推荐分类
 */

public class AppRecommendApi extends BticmApi{



    @Override
    protected String getPath() {
        return "app/getclass4";
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
