package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/1/9.
 *首页 链世界 分类
 */

public class HomeLianClassApi extends BticmApi{



    @Override
    protected String getPath() {
        return "app/getclass";
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
