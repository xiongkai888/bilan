package com.lanmei.bilan.bean;

/**
 * Created by xkai on 2018/2/7.
 * 币种分类
 */

public class MarketBiBean {

    /**
     * id : 14
     * addtime : 1515561704
     * uptime : 1515575691
     * tablename : Otcpublish
     * state : 1
     * classname : BCH
     * state2 : 1
     */

    private String id;
    private String addtime;
    private String uptime;
    private String tablename;
    private String state;
    private String classname;
    private String state2;
    private boolean refresh;//刷新

    public void setRefresh(boolean refresh) {
        this.refresh = refresh;
    }

    public boolean isRefresh() {
        return refresh;
    }

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

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getState2() {
        return state2;
    }

    public void setState2(String state2) {
        this.state2 = state2;
    }

    /**
     * logo : https://image.haobi8.com/v2/images/coinLogo/df757e32-903c-4741-b7b4-06906f5f4345.png
     * defaultenname : BCH
     * defaultcnname : 比特币现金
     * newclinchprice : 950.00000000
     * newclinchtype : 1
     * buyprice : 917.00000000
     * sellprice : 950.00000000
     * count24 : 8255.92000000
     * money24 : 7566295.49460000
     * range24 : 19.59
     * highprice : 965.93000000
     * lowprice : 791.01000000
     */

    private String logo;
    private String defaultenname;
    private String defaultcnname;
    private String newclinchprice;
    private int newclinchtype;
    private String buyprice;
    private String sellprice;
    private String count24;
    private String money24;
    private String range24;
    private String highprice;
    private String lowprice;

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getDefaultenname() {
        return defaultenname;
    }

    public void setDefaultenname(String defaultenname) {
        this.defaultenname = defaultenname;
    }

    public String getDefaultcnname() {
        return defaultcnname;
    }

    public void setDefaultcnname(String defaultcnname) {
        this.defaultcnname = defaultcnname;
    }

    public String getNewclinchprice() {
        return newclinchprice;
    }

    public void setNewclinchprice(String newclinchprice) {
        this.newclinchprice = newclinchprice;
    }

    public int getNewclinchtype() {
        return newclinchtype;
    }

    public void setNewclinchtype(int newclinchtype) {
        this.newclinchtype = newclinchtype;
    }

    public String getBuyprice() {
        return buyprice;
    }

    public void setBuyprice(String buyprice) {
        this.buyprice = buyprice;
    }

    public String getSellprice() {
        return sellprice;
    }

    public void setSellprice(String sellprice) {
        this.sellprice = sellprice;
    }

    public String getCount24() {
        return count24;
    }

    public void setCount24(String count24) {
        this.count24 = count24;
    }

    public String getMoney24() {
        return money24;
    }

    public void setMoney24(String money24) {
        this.money24 = money24;
    }

    public String getRange24() {
        return range24;
    }

    public void setRange24(String range24) {
        this.range24 = range24;
    }

    public String getHighprice() {
        return highprice;
    }

    public void setHighprice(String highprice) {
        this.highprice = highprice;
    }

    public String getLowprice() {
        return lowprice;
    }

    public void setLowprice(String lowprice) {
        this.lowprice = lowprice;
    }
}
