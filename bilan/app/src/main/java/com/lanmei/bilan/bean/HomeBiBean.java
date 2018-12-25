package com.lanmei.bilan.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xkai on 2018/1/8.
 * 首页 币头条
 */

public class HomeBiBean implements Serializable{


    /**
     * id : 265
     * uid : null
     * cid : 1
     * type : 2
     * title : 【赛艇】从泰晤士河畔到昆明草海，我们聊聊百年赛事传统
     * content : <p></p>
     * file : ["http://stdrimages.img-cn-shenzhen.aliyuncs.com/171128/5a1cc250eef3c.jpg","http://stdrimages.img-cn-shenzhen.aliyuncs.com/171 01-08 19:22:44.142 28522-28672/com.lanmei.btcim D/BeanRequest: 128/5a1cc254d1984.jpg","http://stdrimages.img-cn-shenzhen.aliyuncs.com/171128/5a1cc26d5e369.jpg"]
     * view : 0
     * favour : 0
     * reviews : 0
     * like : 0
     * unlike : 0
     * addtime : 1511481600
     * status : 1
     * del : 0
     * device_id : 0
     * aid : 0
     * top : 0
     * essential : 0
     * hot : 0
     * recommend : 0
     * area : 
     * price : 0.00
     * sub_title : 【赛艇】从泰晤士河畔到昆明草海，我们聊聊百年赛事传统
     * site : 
     * label : 
     * tel : 
     * video : 
     * author : 
     * stime : 0
     * etime : 0
     * pic_size : 
     * cname : 币头条
     * lr : 0
     * favoured : 0
     * course_apply : 0
     * customize_apply : 0
     * activity_apply : 0
     * pic : null
     * nickname : null
     * area_format : 
     */

    private String id;
    private String uid;
    private String cid;
    private String type;
    private String title;
    private String content;
    private String view;
    private String favour;
    private String reviews;
    private String like;
    private String unlike;//利空
    private String unliked;//利空
    private String addtime;
    private String status;
    private String del;
    private String device_id;
    private String aid;
    private String top;
    private String essential;
    private String hot;
    private String recommend;
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
    private String cname;
    private String lr;
    private String favoured;
    private String course_apply;
    private String customize_apply;
    private String activity_apply;
    private String pic;
    private String nickname;
    private String liked;
    private boolean flag = true;//文字显示还是收缩

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setLiked(String liked) {
        this.liked = liked;
    }

    public String getLiked() {
        return liked;
    }

    private String area_format;
    private List<String> file;

    public void setUnliked(String unliked) {
        this.unliked = unliked;
    }

    public String getUnliked() {
        return unliked;
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

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getLr() {
        return lr;
    }

    public void setLr(String lr) {
        this.lr = lr;
    }

    public String getFavoured() {
        return favoured;
    }

    public void setFavoured(String favoured) {
        this.favoured = favoured;
    }

    public String getCourse_apply() {
        return course_apply;
    }

    public void setCourse_apply(String course_apply) {
        this.course_apply = course_apply;
    }

    public String getCustomize_apply() {
        return customize_apply;
    }

    public void setCustomize_apply(String customize_apply) {
        this.customize_apply = customize_apply;
    }

    public String getActivity_apply() {
        return activity_apply;
    }

    public void setActivity_apply(String activity_apply) {
        this.activity_apply = activity_apply;
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

    public String getArea_format() {
        return area_format;
    }

    public void setArea_format(String area_format) {
        this.area_format = area_format;
    }

    public List<String> getFile() {
        return file;
    }

    public void setFile(List<String> file) {
        this.file = file;
    }
}
