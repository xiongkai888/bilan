package com.lanmei.bilan.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xkai on 2018/1/3.
 * 去块圈Bean
 */

public class HomeQuBean implements Serializable{


    /**
     * id : 324
     * uid : 35
     * title : 台湾台北赛艇基地
     * file : ["http://stdrimages.oss-cn-shenzhen.aliyuncs.com/35/150476802255.png","http://stdrimages.oss-cn-shenzhen.aliyuncs.com/35/150476802218.png","http://stdrimages.oss-cn-shenzhen.aliyuncs.com/35/150476802290.png"]
     * like : 3
     * reviews : 0
     * addtime : 1504768027
     * lr : 3
     * city :
     * pic : http://stdrimages.oss-cn-shenzhen.aliyuncs.com/26/1498962378.png
     * nickname : Beth
     * username : u_000035
     * liked : 0
     * followed : 0
     */

    private String id;
    private String uid;
    private String title;
    private String like;
    private String reviews;
    private String addtime;
    private String lr;
    private String city;
    private String pic;
    private String nickname;
    private String username;
    private String liked;
    private String followed;
    private List<String> file;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getLr() {
        return lr;
    }

    public void setLr(String lr) {
        this.lr = lr;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLiked() {
        return liked;
    }

    public void setLiked(String liked) {
        this.liked = liked;
    }

    public String getFollowed() {
        return followed;
    }

    public void setFollowed(String followed) {
        this.followed = followed;
    }

    public List<String> getFile() {
        return file;
    }

    public void setFile(List<String> file) {
        this.file = file;
    }
}
