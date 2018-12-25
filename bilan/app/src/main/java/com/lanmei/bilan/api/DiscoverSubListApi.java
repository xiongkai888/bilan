package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/1/10.
 * 发现 产品列表
 */

public class DiscoverSubListApi extends BticmApi {

    public String uid;//用户id用来确定是否收藏
    public String id;//*数据ID
    public String name;//*产品名称
    public String content;//产品描述
    public String sell_price;//产品价格
    public String brand_id;//分类ID  130项目速递126发烧设备125区块宝库134速递135梦想

    @Override
    protected String getPath() {
        return "app/brand2";
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
