package com.lanmei.bilan.bean;

import java.io.Serializable;

/**
 * Created by xkai on 2018/1/10.
 * 行情场外发布
 */

public class MarketChangBean implements Serializable{


    /**
     * id : 13
     * addtime : 1515676270
     * uptime : 1515676270
     * classid : 13
     * cudo : 0
     * num : 2
     * price : 20000.00
     * userid : 201
     * endtime : null
     * state : 1
     * classname : LTC
     * username : b_181
     */

    private String id;
    private String addtime;
    private String uptime;
    private String classid;
    private String cudo;
    private String num;
    private String price;
    private String userid;
    private String endtime;
    private String state;
    private String classname;
    private String username;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getUptime() {
        return uptime;
    }

    public void setUptime(String uptime) {
        this.uptime = uptime;
    }

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public String getCudo() {
        return cudo;
    }

    public void setCudo(String cudo) {
        this.cudo = cudo;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
