package com.lanmei.bilan.bean;

/**
 * Created by xkai on 2018/2/9.
 */

public class InviteBean {

    /**
     * withdrawal_fee : 1
     * withdrawal_fee_max : 50
     * withdrawal_min : 100
     * holidays : 05-01,10-01
     * cancel_order_time : 240
     * cancel_order_time1 : 240
     * platform_service : 55
     * share_url : https://www.pgyer.com/KQhR
     * share_qr : http://btcim.img-cn-shenzhen.aliyuncs.com/180209/5a7d03fa8c2d6.png
     */

    private String withdrawal_fee;
    private String withdrawal_fee_max;
    private String withdrawal_min;
    private String holidays;
    private String cancel_order_time;
    private String cancel_order_time1;
    private String platform_service;
    private String share_url;
    private String share_qr;

    public String getWithdrawal_fee() {
        return withdrawal_fee;
    }

    public void setWithdrawal_fee(String withdrawal_fee) {
        this.withdrawal_fee = withdrawal_fee;
    }

    public String getWithdrawal_fee_max() {
        return withdrawal_fee_max;
    }

    public void setWithdrawal_fee_max(String withdrawal_fee_max) {
        this.withdrawal_fee_max = withdrawal_fee_max;
    }

    public String getWithdrawal_min() {
        return withdrawal_min;
    }

    public void setWithdrawal_min(String withdrawal_min) {
        this.withdrawal_min = withdrawal_min;
    }

    public String getHolidays() {
        return holidays;
    }

    public void setHolidays(String holidays) {
        this.holidays = holidays;
    }

    public String getCancel_order_time() {
        return cancel_order_time;
    }

    public void setCancel_order_time(String cancel_order_time) {
        this.cancel_order_time = cancel_order_time;
    }

    public String getCancel_order_time1() {
        return cancel_order_time1;
    }

    public void setCancel_order_time1(String cancel_order_time1) {
        this.cancel_order_time1 = cancel_order_time1;
    }

    public String getPlatform_service() {
        return platform_service;
    }

    public void setPlatform_service(String platform_service) {
        this.platform_service = platform_service;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public String getShare_qr() {
        return share_qr;
    }

    public void setShare_qr(String share_qr) {
        this.share_qr = share_qr;
    }
}
