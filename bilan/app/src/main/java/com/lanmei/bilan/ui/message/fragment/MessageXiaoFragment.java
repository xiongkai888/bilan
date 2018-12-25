package com.lanmei.bilan.ui.message.fragment;

import android.os.Bundle;

import com.lanmei.bilan.R;
import com.lanmei.bilan.adapter.MessageXiaoAdapter;
import com.lanmei.bilan.api.HomeQuApi;
import com.lanmei.bilan.bean.HomeQuBean;
import com.xson.common.app.BaseFragment;
import com.xson.common.bean.ListBean;
import com.xson.common.helper.SwipeRefreshController;
import com.xson.common.widget.DividerItemDecoration;
import com.xson.common.widget.SmartSwipeRefreshLayout;

import butterknife.InjectView;

/**
 * Created by xkai on 2018/1/3.
 * 消息-中的-消息
 */

public class MessageXiaoFragment extends BaseFragment {

    @InjectView(R.id.pull_refresh_rv)
    SmartSwipeRefreshLayout smartSwipeRefreshLayout;
    MessageXiaoAdapter mAdapter;
    private SwipeRefreshController<ListBean<HomeQuBean>> controller;

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
        mAdapter = new MessageXiaoAdapter(context);
        smartSwipeRefreshLayout.setAdapter(mAdapter);
        controller = new SwipeRefreshController<ListBean<HomeQuBean>>(context, smartSwipeRefreshLayout, api, mAdapter) {
        };
//        controller.loadFirstPage();
        mAdapter.notifyDataSetChanged();
    }


}
