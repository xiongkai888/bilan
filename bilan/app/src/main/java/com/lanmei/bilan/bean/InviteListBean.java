package com.lanmei.bilan.bean;

import com.xson.common.bean.NoPageListBean;

/**
 * Created by xkai on 2018/7/2.
 */

public class InviteListBean<T> extends NoPageListBean<T>{

    public String getCount() {
        return count;
    }

    public String getUrl() {
        return url;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String count;//糖果累计

    private String url;//分享链接
}
