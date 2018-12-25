package com.lanmei.bilan.event;

import com.lanmei.bilan.bean.GoodsCommentBean;

import java.util.List;

/**
 * Created by xkai on 2018/1/19.
 * 商品详情的评论事件（最多显示一条）
 */

public class OnlyCommentEvent {

    List<GoodsCommentBean> list;

    public List<GoodsCommentBean> getList() {
        return list;
    }

    public OnlyCommentEvent(List<GoodsCommentBean> list){
        this.list = list;
    }
}
