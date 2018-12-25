package com.lanmei.bilan.event;

/**
 * Created by xkai on 2018/1/8.
 * 注册事件
 */

public class RegisterEvent {

    private String phone;

    public String getPhone() {
        return phone;
    }

    public RegisterEvent(String phone){
        this.phone = phone;
    }
}
