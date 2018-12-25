package com.lanmei.bilan.bean;

/**
 * Created by xkai on 2018/6/21.
 * 任务
 */

public class TaskBean {

    /**
     * id : 1
     * addtime : 1529561463
     * uptime : 1529561463
     * state : 1
     * title : 签到奖励
     * subtitle : 签到有奖 每日限一次
     * bl : 10
     * ceiling : 1
     * order_by : 10
     * status : 0
     */

    private String id;
    private String addtime;
    private String uptime;
    private String state;
    private String title;
    private String subtitle;
    private String bl;
    private String ceiling;
    private String order_by;
    private int status;

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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getBl() {
        return bl;
    }

    public void setBl(String bl) {
        this.bl = bl;
    }

    public String getCeiling() {
        return ceiling;
    }

    public void setCeiling(String ceiling) {
        this.ceiling = ceiling;
    }

    public String getOrder_by() {
        return order_by;
    }

    public void setOrder_by(String order_by) {
        this.order_by = order_by;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
