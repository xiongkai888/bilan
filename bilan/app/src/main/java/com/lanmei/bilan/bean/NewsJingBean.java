package com.lanmei.bilan.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xkai on 2018/6/28.
 * 资讯-精选
 */

public class NewsJingBean implements Serializable{

    /**
     * id : 791
     * uid : 1
     * cid : 20
     * type : 2
     * title : 后台测试
     * content : 后台测试
     * file :
     * view : 0
     * favour : 1
     * reviews : 1
     * like : 0
     * unlike : 0
     * addtime : 1529982000
     * status : 1
     * del : 0
     * device_id : 0
     * aid : 0
     * top : 1
     * essential : 1
     * hot : 0
     * recommend : 1
     * area :
     * price : 0.00
     * sub_title : 后台测试
     * site :
     * label :
     * tel :
     * video :
     * author :
     * stime : 0
     * etime : 0
     * pic_size :
     * rglike : 0
     * likesum : 0
     * rgunlike : 0
     * unlikesum : 0
     * favoured : 0
     * liked : 0
     * unliked : 0
     * focuson : 0
     * cname : 资讯
     */

    private String id;
    private String uid;
    private String cid;
    private String type;
    private String title;
    private String content;
    private List<String> file;
    private String view;
    private String favour;
    private String reviews;
    private String like;
    private String unlike;
    private String addtime;
    private String status;
    private String del;
    private String device_id;
    private String aid;
    private String top;//置顶（0|1）
    private String essential;//精华（0|1）
    private String hot;
    private String recommend;//推荐（0|1）
    private String area;
    private String price;
    private String sub_title;
    private String site;
    private String label;
    private String tel;
    private String video;
    private String author;
    private String stime;
    private String etime;
    private String pic_size;
    private String rglike;
    private String likesum;
    private String rgunlike;
    private String unlikesum;
    private int favoured;//是否收藏（1|0）
    private int liked;//是否点赞（1|0）
    private int unliked;//是否点踩（1|0）
    private int focuson;//是否关注（1|0）
    private String cname;
    private String advertising;//是否广告（1|0）
    private UserBean user;

    public void setAdvertising(String advertising) {
        this.advertising = advertising;
    }

    public String getAdvertising() {
        return advertising;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public UserBean getUser() {
        return user;
    }

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

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getFile() {
        return file;
    }

    public void setFile(List<String> file) {
        this.file = file;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getFavour() {
        return favour;
    }

    public void setFavour(String favour) {
        this.favour = favour;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getUnlike() {
        return unlike;
    }

    public void setUnlike(String unlike) {
        this.unlike = unlike;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDel() {
        return del;
    }

    public void setDel(String del) {
        this.del = del;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }

    public String getEssential() {
        return essential;
    }

    public void setEssential(String essential) {
        this.essential = essential;
    }

    public String getHot() {
        return hot;
    }

    public void setHot(String hot) {
        this.hot = hot;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSub_title() {
        return sub_title;
    }

    public void setSub_title(String sub_title) {
        this.sub_title = sub_title;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public String getEtime() {
        return etime;
    }

    public void setEtime(String etime) {
        this.etime = etime;
    }

    public String getPic_size() {
        return pic_size;
    }

    public void setPic_size(String pic_size) {
        this.pic_size = pic_size;
    }

    public String getRglike() {
        return rglike;
    }

    public void setRglike(String rglike) {
        this.rglike = rglike;
    }

    public String getLikesum() {
        return likesum;
    }

    public void setLikesum(String likesum) {
        this.likesum = likesum;
    }

    public String getRgunlike() {
        return rgunlike;
    }

    public void setRgunlike(String rgunlike) {
        this.rgunlike = rgunlike;
    }

    public String getUnlikesum() {
        return unlikesum;
    }

    public void setUnlikesum(String unlikesum) {
        this.unlikesum = unlikesum;
    }

    public int getFavoured() {
        return favoured;
    }

    public void setFavoured(int favoured) {
        this.favoured = favoured;
    }

    public int getLiked() {
        return liked;
    }

    public void setLiked(int liked) {
        this.liked = liked;
    }

    public int getUnliked() {
        return unliked;
    }

    public void setUnliked(int unliked) {
        this.unliked = unliked;
    }

    public int getFocuson() {
        return focuson;
    }

    public void setFocuson(int focuson) {
        this.focuson = focuson;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }
}
