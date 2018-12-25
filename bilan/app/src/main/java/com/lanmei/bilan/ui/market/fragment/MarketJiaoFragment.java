package com.lanmei.bilan.ui.market.fragment;

import android.os.Bundle;

import com.lanmei.bilan.R;
import com.lanmei.bilan.adapter.MarketJiaoAdapter;
import com.lanmei.bilan.api.HomeQuApi;
import com.lanmei.bilan.bean.HomeQuBean;
import com.xson.common.app.BaseFragment;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.SwipeRefreshController;
import com.xson.common.widget.DividerItemDecoration;
import com.xson.common.widget.SmartSwipeRefreshLayout;

import butterknife.InjectView;

/**
 * Created by xkai on 2018/1/3.
 * 行情——交易所
 */

public class MarketJiaoFragment extends BaseFragment {

    @InjectView(R.id.pull_refresh_rv)
    SmartSwipeRefreshLayout smartSwipeRefreshLayout;
    MarketJiaoAdapter mAdapter;
    private SwipeRefreshController<NoPageListBean<HomeQuBean>> controller;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_single_listview;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        initSwipeRefreshLayout();
    }


    private void initSwipeRefreshLayout() {
        smartSwipeRefreshLayout.initWithLinearLayout();
        smartSwipeRefreshLayout.getRecyclerView().addItemDecoration(new DividerItemDecoration(context));

        HomeQuApi api = new HomeQuApi();
        mAdapter = new MarketJiaoAdapter(context);
        smartSwipeRefreshLayout.setAdapter(mAdapter);
        controller = new SwipeRefreshController<NoPageListBean<HomeQuBean>>(context, smartSwipeRefreshLayout, api, mAdapter) {
        };
//        controller.loadFirstPage();
        mAdapter.notifyDataSetChanged();
    }


}
