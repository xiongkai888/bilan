package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/6/25.
 * 任务（执行）
 *
 */

public class TaskApi extends BticmApi {

    public String uid;
    public String task;//任务ID

    @Override
    protected String getPath() {
        return "app/tosignin";
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}