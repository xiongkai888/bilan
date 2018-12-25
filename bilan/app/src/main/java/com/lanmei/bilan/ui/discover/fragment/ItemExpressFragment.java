package com.lanmei.bilan.ui.discover.fragment;

import android.os.Bundle;

import com.lanmei.bilan.R;
import com.lanmei.bilan.adapter.ItemExpressSubAdapter;
import com.lanmei.bilan.api.DiscoverSubListApi;
import com.lanmei.bilan.bean.DiscoverSubListBean;
import com.xson.common.app.BaseFragment;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.SwipeRefreshController;
import com.xson.common.widget.DividerItemDecoration;
import com.xson.common.widget.SmartSwipeRefreshLayout;

import butterknife.InjectView;

/**
 * Created by xkai on 2018/1/3.
 *
 */

public class ItemExpressFragment extends BaseFragment {

    @InjectView(R.id.pull_refresh_rv)
    SmartSwipeRefreshLayout refreshLayout;
    ItemExpressSubAdapter mAdapter;
    private SwipeRefreshController<NoPageListBean<DiscoverSubListBean>> controller;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_single_listview;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        initSwipeRefreshLayout();
    }



    private void initSwipeRefreshLayout() {
        refreshLayout.initWithLinearLayout();
        refreshLayout.getRecyclerView().addItemDecoration(new DividerItemDecoration(context));


        DiscoverSubListApi api = new DiscoverSubListApi();
        api.uid = api.getUserId(context);
        api.brand_id = getArguments().getString("id");
        mAdapter = new ItemExpressSubAdapter(context);
        refreshLayout.setAdapter(mAdapter);
        controller = new SwipeRefreshController<NoPageListBean<DiscoverSubListBean>>(context, refreshLayout, api, mAdapter) {
        };
        controller.loadFirstPage();
    }



}
