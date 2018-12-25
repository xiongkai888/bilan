package com.lanmei.bilan.ui.home.fragment;

import android.os.Bundle;

import com.lanmei.bilan.R;
import com.lanmei.bilan.adapter.HomeGongAdapter;
import com.lanmei.bilan.api.HomeMingApi;
import com.lanmei.bilan.bean.HomeGongBean;
import com.xson.common.app.BaseFragment;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.SwipeRefreshController;
import com.xson.common.widget.DividerItemDecoration;
import com.xson.common.widget.SmartSwipeRefreshLayout;

import butterknife.InjectView;

/**
 * Created by xkai on 2018/1/3.
 * 公众号
 */

public class HomeGongFragment extends BaseFragment {

    @InjectView(R.id.pull_refresh_rv)
    SmartSwipeRefreshLayout gong;
    HomeGongAdapter mAdapter;
    private SwipeRefreshController<NoPageListBean<HomeGongBean>> controller;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_single_listview;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        initSwipeRefreshLayout();
    }


    private void initSwipeRefreshLayout() {
        gong.initWithLinearLayout();
        gong.getRecyclerView().addItemDecoration(new DividerItemDecoration(context));

        HomeMingApi api = new HomeMingApi();
        api.uid = api.getUserId(context);
        api.user_type = "2";//0|1|2 => 普通会员|名人堂|公众号
        mAdapter = new HomeGongAdapter(context);
        gong.setAdapter(mAdapter);
        controller = new SwipeRefreshController<NoPageListBean<HomeGongBean>>(context, gong, api, mAdapter) {
        };
        controller.loadFirstPage();
    }


}
