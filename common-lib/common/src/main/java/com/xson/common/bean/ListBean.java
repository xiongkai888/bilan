package com.xson.common.bean;

import java.util.List;

/**
 * @author Milk <249828165@qq.com>
 *带翻页列表结果格式的bean
{
"status":0,
"msg":"成功"，
"data":{
"pageNumber":1
"pageSize":10
"totalPage":20
"totalRow":1000,
"list":{  // 这里是放置泛型T的地方
}
}}
 *
 */
public class ListBean<T> extends AbsListBean {

    public List<T> data;

    @Override
    public List<T> getDataList() {
        return data;
    }
}
