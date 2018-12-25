package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/1/11.
 * 区块圈即动态 发布|更新
 */

public class AddDynamicApi extends BticmApi {

    public String id;//区块圈ID，*更新时为必填

    public String title;//标题，*更新时选填

    public String content;//内容，*更新时选填

    public String[] file;//图片集，数组形式*更新时选填

    public String type;//*贴子类型：1、文字2、图片3、视频'

    public String uid;//用户数据ID

    public String city;//地址


    @Override
    protected String getPath() {
        return "posts/add";
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
