package com.xson.common.bean;

import java.util.List;

/**
 * @author Milk
 * 不翻页
 */
public class NoPageList2Bean<T> extends BaseBean {

    public List<T> data;

    public List<T> getDataList(){
        return data;
    }
}