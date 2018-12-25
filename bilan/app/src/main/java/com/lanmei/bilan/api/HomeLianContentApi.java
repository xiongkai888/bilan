package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/1/9.
 *首页 链世界 分类 内容
 */

public class HomeLianContentApi extends BticmApi{


    public String classid;// 类别数据ID


    @Override
    protected String getPath() {
        return "app/getlinkworld";
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
