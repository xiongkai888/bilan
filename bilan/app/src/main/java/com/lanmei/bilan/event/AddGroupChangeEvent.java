package com.lanmei.bilan.event;

/**
 * Created by xkai on 2018/1/17.
 * 群通知事件（）
 */

public class  AddGroupChangeEvent {

    private String groupId;//
    private String groupName;//群名
    private String applyer;//申请者
    private String decliner;//拒绝者
    private String reason;//原因
    private String inviter;//邀请者
    private String invitee;//被邀请者
    private String accepter;//接受者

    public AddGroupChangeEvent(int type){
        this.type = type;
    }

    public void setAccepter(String accepter) {
        this.accepter = accepter;
    }

    public String getAccepter() {
        return accepter;
    }

    private int type;//群变化类型  1、用户申请加入群

    public int getType() {
        return type;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setApplyer(String applyer) {
        this.applyer = applyer;
    }

    public void setDecliner(String decliner) {
        this.decliner = decliner;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setInviter(String inviter) {
        this.inviter = inviter;
    }

    public void setInvitee(String invitee) {
        this.invitee = invitee;
    }


    public String getGroupId() {
        return groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getApplyer() {
        return applyer;
    }

    public String getDecliner() {
        return decliner;
    }

    public String getReason() {
        return reason;
    }

    public String getInviter() {
        return inviter;
    }

    public String getInvitee() {
        return invitee;
    }

}
