package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/1/11.
 * 任务列表
 */

public class TaskListApi extends BticmApi{

    public String uid;


    @Override
    protected String getPath() {
        return "app/task";
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
