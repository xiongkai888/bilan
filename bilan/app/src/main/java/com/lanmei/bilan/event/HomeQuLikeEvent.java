package com.lanmei.bilan.event;

/**
 * Created by xkai on 2018/1/10.
 * 区块圈点赞事件或评论
 */

public class HomeQuLikeEvent {

    private String like;//点赞数量
    private String liked;//是否点赞过
    private String commNum;//评论数量
    private String id;

    public String getId() {
        return id;
    }
    public HomeQuLikeEvent(String like, String liked, String commNum,String id){
        this.like = like;
        this.liked = liked;
        this.commNum = commNum;
        this.id = id;
    }

    public String getLike() {
        return like;
    }
    public String getLiked() {
        return liked;
    }

    public String getCommNum() {
        return commNum;
    }

}
