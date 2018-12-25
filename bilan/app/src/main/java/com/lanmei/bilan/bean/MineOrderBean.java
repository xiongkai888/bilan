package com.lanmei.bilan.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xkai on 2018/1/23.
 * 我的订单
 */

public class MineOrderBean implements Serializable{

    /**
     * id : 16
     * addtime : 1516702098
     * uptime : 1516702098
     * endtime : 1516702098
     * state : 1
     * pay_no : 20180123B0100Tjxuq
     * pay_type : 6
     * userid : 203
     * uname : 镊20
     * uphone : 15914369666
     * uarea : 北京市北京市东城区000000
     * pro_id : 3560,
     * num : 1
     * price : 35.00
     * total_price : 35.00
     * if_del : 0
     * pay_status : 1
     * reviews : 0
     * order_num : 2
     * status : 1
     * order_amount : 35.00
     */

    private String id;
    private String addtime;
    private String uptime;
    private String endtime;
    private String state;
    private String pay_no;
    private String pay_type;
    private String userid;
    private String uname;
    private String uphone;
    private String uarea;
    private String pro_id;
    private String num;
    private String price;
    private String total_price;
    private String if_del;
    private String pay_status;
    private String reviews;
    private int order_num;
    private String status;
    private String order_amount;
    private List<ProductBean> product;

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

    public String getPay_no() {
        return pay_no;
    }

    public void setPay_no(String pay_no) {
        this.pay_no = pay_no;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUphone() {
        return uphone;
    }

    public void setUphone(String uphone) {
        this.uphone = uphone;
    }

    public String getUarea() {
        return uarea;
    }

    public void setUarea(String uarea) {
        this.uarea = uarea;
    }

    public String getPro_id() {
        return pro_id;
    }

    public void setPro_id(String pro_id) {
        this.pro_id = pro_id;
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

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getIf_del() {
        return if_del;
    }

    public void setIf_del(String if_del) {
        this.if_del = if_del;
    }

    public String getPay_status() {
        return pay_status;
    }

    public void setPay_status(String pay_status) {
        this.pay_status = pay_status;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    public int getOrder_num() {
        return order_num;
    }

    public void setOrder_num(int order_num) {
        this.order_num = order_num;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrder_amount() {
        return order_amount;
    }

    public void setOrder_amount(String order_amount) {
        this.order_amount = order_amount;
    }

    public List<ProductBean> getProduct() {
        return product;
    }

    public void setProduct(List<ProductBean> product) {
        this.product = product;
    }

    public static class ProductBean implements Serializable{
        /**
         * id : 271
         * uid : 0
         * name : 白话区块链+区块链核心算法解析+区块链开发指南+区块链技术原理及底层架构 区块链开发技术书籍 区块链
         * goods_no : 
         * model_id : 0
         * sell_price : 35.00
         * market_price : 0.00
         * cost_price : null
         * up_time : null
         * down_time : null
         * create_time : 2018-01-10 11:14:55
         * store_nums : 0
         * img : http://btcim.img-cn-shenzhen.aliyuncs.com/180110/5a5584c5509b6.jpg
         * img_server : p1.WEB_DOMAIN
         * is_del : 0
         * content : 作译者<br />
         * search_words : null
         * weight : 0.00
         * point : 0
         * unit : null
         * brand_id : 5
         * rel_goods_id : 
         * visit : 0
         * favorite : 1
         * sort : 99
         * spec_array : []
         * exp : 0
         * comments : 0
         * sale : 0
         * grade : 0
         * offline_mer : 
         * area : 
         * rebate : 1
         * featured : 0
         * hot : 0
         * recommend : 0
         * service : &lt;span&gt;本店支持7天无理由退换，购买后如有任何疑问，请联系我们为您解决&lt;/span&gt;
         * express_fee : 0
         * status : 1
         * mounting_cost : 0.00
         * vgoods : 0
         * spot : 1
         * associated_goods : 
         * point_goods : 0
         * original_id : 0
         * state : null
         * commission : 0.00
         * fee : 0.00
         * imgs : [{"imgurl":"http://btcim.img-cn-shenzhen.aliyuncs.com/180110/5a5584c5509b6.jpg"},{"imgurl":"http://btcim.img-cn-shenzhen.aliyuncs.com/180110/5a5584f536e39.jpg"}]
         * goods_nums : 1
         * goods_id : 3560
         */

        private String id;
        private String uid;
        private String name;
        private String goods_no;
        private String model_id;
        private String sell_price;
        private String market_price;
        private String cost_price;
        private String up_time;
        private String down_time;
        private String create_time;
        private String store_nums;
        private String img;
        private String img_server;
        private String is_del;
        private String content;
        private String keywords;
        private String description;
        private String search_words;
        private String weight;
        private String point;
        private String unit;
        private String brand_id;
        private String rel_goods_id;
        private String visit;
        private int favorite;
        private String sort;
        private String exp;
        private String comments;
        private String sale;
        private String grade;
        private String offline_mer;
        private String area;
        private String rebate;
        private String featured;
        private String hot;
        private String recommend;
        private String service;
        private String express_fee;
        private String status;
        private String mounting_cost;
        private String vgoods;
        private String spot;
        private String associated_goods;
        private String point_goods;
        private String original_id;
        private String state;
        private String commission;
        private String fee;
        private String goods_nums;
        private String goods_id;
        private List<String> spec_array;
        private List<ImgsBean> imgs;

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGoods_no() {
            return goods_no;
        }

        public void setGoods_no(String goods_no) {
            this.goods_no = goods_no;
        }

        public String getModel_id() {
            return model_id;
        }

        public void setModel_id(String model_id) {
            this.model_id = model_id;
        }

        public String getSell_price() {
            return sell_price;
        }

        public void setSell_price(String sell_price) {
            this.sell_price = sell_price;
        }

        public String getMarket_price() {
            return market_price;
        }

        public void setMarket_price(String market_price) {
            this.market_price = market_price;
        }

        public String getCost_price() {
            return cost_price;
        }

        public void setCost_price(String cost_price) {
            this.cost_price = cost_price;
        }

        public String getUp_time() {
            return up_time;
        }

        public void setUp_time(String up_time) {
            this.up_time = up_time;
        }

        public String getDown_time() {
            return down_time;
        }

        public void setDown_time(String down_time) {
            this.down_time = down_time;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getStore_nums() {
            return store_nums;
        }

        public void setStore_nums(String store_nums) {
            this.store_nums = store_nums;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getImg_server() {
            return img_server;
        }

        public void setImg_server(String img_server) {
            this.img_server = img_server;
        }

        public String getIs_del() {
            return is_del;
        }

        public void setIs_del(String is_del) {
            this.is_del = is_del;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getSearch_words() {
            return search_words;
        }

        public void setSearch_words(String search_words) {
            this.search_words = search_words;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getPoint() {
            return point;
        }

        public void setPoint(String point) {
            this.point = point;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getBrand_id() {
            return brand_id;
        }

        public void setBrand_id(String brand_id) {
            this.brand_id = brand_id;
        }

        public String getRel_goods_id() {
            return rel_goods_id;
        }

        public void setRel_goods_id(String rel_goods_id) {
            this.rel_goods_id = rel_goods_id;
        }

        public String getVisit() {
            return visit;
        }

        public void setVisit(String visit) {
            this.visit = visit;
        }

        public int getFavorite() {
            return favorite;
        }

        public void setFavorite(int favorite) {
            this.favorite = favorite;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getExp() {
            return exp;
        }

        public void setExp(String exp) {
            this.exp = exp;
        }

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments = comments;
        }

        public String getSale() {
            return sale;
        }

        public void setSale(String sale) {
            this.sale = sale;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public String getOffline_mer() {
            return offline_mer;
        }

        public void setOffline_mer(String offline_mer) {
            this.offline_mer = offline_mer;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getRebate() {
            return rebate;
        }

        public void setRebate(String rebate) {
            this.rebate = rebate;
        }

        public String getFeatured() {
            return featured;
        }

        public void setFeatured(String featured) {
            this.featured = featured;
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

        public String getService() {
            return service;
        }

        public void setService(String service) {
            this.service = service;
        }

        public String getExpress_fee() {
            return express_fee;
        }

        public void setExpress_fee(String express_fee) {
            this.express_fee = express_fee;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMounting_cost() {
            return mounting_cost;
        }

        public void setMounting_cost(String mounting_cost) {
            this.mounting_cost = mounting_cost;
        }

        public String getVgoods() {
            return vgoods;
        }

        public void setVgoods(String vgoods) {
            this.vgoods = vgoods;
        }

        public String getSpot() {
            return spot;
        }

        public void setSpot(String spot) {
            this.spot = spot;
        }

        public String getAssociated_goods() {
            return associated_goods;
        }

        public void setAssociated_goods(String associated_goods) {
            this.associated_goods = associated_goods;
        }

        public String getPoint_goods() {
            return point_goods;
        }

        public void setPoint_goods(String point_goods) {
            this.point_goods = point_goods;
        }

        public String getOriginal_id() {
            return original_id;
        }

        public void setOriginal_id(String original_id) {
            this.original_id = original_id;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCommission() {
            return commission;
        }

        public void setCommission(String commission) {
            this.commission = commission;
        }

        public String getFee() {
            return fee;
        }

        public void setFee(String fee) {
            this.fee = fee;
        }

        public String getGoods_nums() {
            return goods_nums;
        }

        public void setGoods_nums(String goods_nums) {
            this.goods_nums = goods_nums;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public List<String> getSpec_array() {
            return spec_array;
        }

        public void setSpec_array(List<String> spec_array) {
            this.spec_array = spec_array;
        }

        public List<ImgsBean> getImgs() {
            return imgs;
        }

        public void setImgs(List<ImgsBean> imgs) {
            this.imgs = imgs;
        }

        public static class ImgsBean implements Serializable{
            /**
             * imgurl : http://btcim.img-cn-shenzhen.aliyuncs.com/180110/5a5584c5509b6.jpg
             */

            private String imgurl;

            public String getImgurl() {
                return imgurl;
            }

            public void setImgurl(String imgurl) {
                this.imgurl = imgurl;
            }
        }
    }
}