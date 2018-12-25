package com.lanmei.bilan.api;

import com.xson.common.api.AbstractApi;

/**
 * Created by Administrator on 2017/5/20.
 * 资讯收藏
 */

public class CollectNewsApi extends BticmApi {

    public String postid;//资讯id
    public String uid;//

    @Override
    protected String getPath() {
        return "app/collectionpost";
    }

    @Override
    public AbstractApi.Method requestMethod() {
        return AbstractApi.Method.POST;
    }
}
