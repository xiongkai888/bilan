package com.lanmei.bilan.bean;

import java.util.List;

/**
 * Created by xkai on 2018/1/16.
 *
 */

public class DiscoverSubListBean {

    /**
     * id : 268
     * name : A5达世大师矿机
     * content : <p>
     <img src="http://btcim.img-cn-shenzhen.aliyuncs.com/180110/10110346_60443.jpg" alt="" />
     </p>
     <p>
     <img src="http://btcim.img-cn-shenzhen.aliyuncs.com/180110/10110356_60102.jpg" alt="" />
     </p>
     <p>
     <p style="font-size:14px;color:#656D78;font-family:&quot;background-color:#FFFFFF;">
     <span>A5达世大师矿机</span><span style="font-size:medium;">参数：</span><span style="font-size:medium;"><br />
     </span>
     </p>
     <p style="font-size:14px;color:#656D78;font-family:&quot;background-color:#FFFFFF;">
     <span>产品型号：	INNOSILICON A5 DashMaster</span>
     </p>
     <p style="font-size:14px;color:#656D78;font-family:&quot;background-color:#FFFFFF;">
     <span>算力：	30.2GH/s（+/-8%）正常模式</span>
     </p>
     <p style="font-size:14px;color:#656D78;font-family:&quot;background-color:#FFFFFF;">
     <span>功耗：	750W +/-8%（正常模式，墙上功耗，电源93%的效率，25℃环境温度）</span>
     </p>
     <p style="font-size:14px;color:#656D78;font-family:&quot;background-color:#FFFFFF;">
     <span>芯片类型：	A5 DashMaster ASIC</span>
     </p>
     <p style="font-size:14px;color:#656D78;font-family:&quot;background-color:#FFFFFF;">
     <span>大小：	单筒，400毫米(长) x 135毫米(宽) x 158毫米(高)</span>
     </p>
     <p style="font-size:14px;color:#656D78;font-family:&quot;background-color:#FFFFFF;">
     <span>整机重量：	净重4.0KG（不带电源）</span>
     </p>
     <p style="font-size:14px;color:#656D78;font-family:&quot;background-color:#FFFFFF;">
     <span>工作温度：	0°C—85°C (器件结温）</span>
     </p>
     <p style="font-size:14px;color:#656D78;font-family:&quot;background-color:#FFFFFF;">
     <span>网络类型：	以太网</span>
     </p>
     </p>
     * img : http://btcim.img-cn-shenzhen.aliyuncs.com/180110/5a558285695c1.jpg
     * sell_price : 2000.00
     * sale : 0
     * spec_array : []
     * imgs : [{"imgurl":"http://btcim.img-cn-shenzhen.aliyuncs.com/180110/5a558285695c1.jpg"},{"imgurl":"http://btcim.img-cn-shenzhen.aliyuncs.com/180110/5a558287bf7bc.jpg"},{"imgurl":"http://btcim.img-cn-shenzhen.aliyuncs.com/180110/5a558289d5958.jpg"}]
     * favorite : 0
     */

    private String id;
    private String name;
    private String content;
    private String img;
    private String sell_price;
    private String sale;
    private int favorite;
    private List<String> spec_array;
    private List<ImgsBean> imgs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSell_price() {
        return sell_price;
    }

    public void setSell_price(String sell_price) {
        this.sell_price = sell_price;
    }

    public String getSale() {
        return sale;
    }

    public void setSale(String sale) {
        this.sale = sale;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public List<?> getSpec_array() {
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

    public static class ImgsBean {
        /**
         * imgurl : http://btcim.img-cn-shenzhen.aliyuncs.com/180110/5a558285695c1.jpg
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
