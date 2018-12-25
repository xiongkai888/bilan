package com.lanmei.bilan.bean;

/**
 * Created by xkai on 2018/1/11.
 * 我的关注
 */

public class MineFollowBean {


    /**
     * mid : 1230
     * nickname : b_1885628****
     * pic : null
     * user_type : 0
     * is_friend : 0
     * followed : 1
     * follow : 3
     */

    private String mid;
    private String nickname;
    private String pic;
    private String user_type;
    private int is_friend;
    private int followed;
    private int follow;

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
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

    public int getIs_friend() {
        return is_friend;
    }

    public void setIs_friend(int is_friend) {
        this.is_friend = is_friend;
    }

    public int getFollowed() {
        return followed;
    }

    public void setFollowed(int followed) {
        this.followed = followed;
    }

    public int getFollow() {
        return follow;
    }

    public void setFollow(int follow) {
        this.follow = follow;
    }
}
