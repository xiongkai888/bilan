package com.lanmei.bilan.event;

/**
 * Created by xkai on 2018/1/10.
 * 区块圈点赞事件或评论
 */

public class HomeKuaiLikeEvent {

    private String like;//点赞数量
    private String liked;//是否点赞过
    private String unlike;//点踩数量
    private String unliked;//是否点踩过
    private String id;

    public String getId() {
        return id;
    }

    public HomeKuaiLikeEvent(String like, String liked, String unlike, String unliked, String id) {
        this.like = like;
        this.liked = liked;
        this.unlike = unlike;
        this.unliked = unliked;
        this.id = id;
    }

    public String getLike() {
        return like;
    }

    public String getLiked() {
        return liked;
    }

    public String getUnlike() {
        return unlike;
    }

    public String getUnliked() {
        return unliked;
    }
}
