package com.lanmei.bilan.event;

/**
 * Created by xkai on 2018/1/17.
 * 加入群组事件
 */

public class JoinGroupEvent {

    String evnet;

    public String getEvnet() {
        return evnet;
    }

    public JoinGroupEvent(String evnet){
        this.evnet = evnet;
    }
}
