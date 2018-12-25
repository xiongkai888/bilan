package com.lanmei.bilan.event;

/**
 * Created by xkai on 2018/1/10.
 * 币头条评论事件
 */

public class HomeBiCommEvent {

    private String commNum;//评论数量
    private String id;

    public String getCommNum() {
        return commNum;
    }

    public String getId() {
        return id;
    }


    public HomeBiCommEvent(String commNum, String id){
        this.commNum = commNum;
        this.id = id;
    }
}
