package com.lanmei.bilan.bean;

/**
 * Created by xkai on 2018/3/2.
 * 我的粉丝
 */

public class FansBean {

    /**
     * id : 50
     * uid : 203
     * mid : 202
     * addtime : 1516973505
     * type : 0
     * nickname : 镊子
     * pic : http://btcim.oss-cn-shenzhen.aliyuncs.com/btcim/bite/img/head2010418214.jpg.tmp
     * user_type : 0
     * followed : 0
     * is_friend : 1
     */

    private String id;
    private String uid;
    private String mid;
    private String addtime;
    private String type;
    private String nickname;
    private String pic;
    private String user_type;
    private String followed;
    private int is_friend;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getFollowed() {
        return followed;
    }

    public void setFollowed(String followed) {
        this.followed = followed;
    }

    public int getIs_friend() {
        return is_friend;
    }

    public void setIs_friend(int is_friend) {
        this.is_friend = is_friend;
    }
}
