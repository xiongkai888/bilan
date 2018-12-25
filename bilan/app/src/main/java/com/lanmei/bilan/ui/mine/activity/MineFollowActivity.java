package com.lanmei.bilan.ui.mine.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;

import com.lanmei.bilan.R;
import com.lanmei.bilan.adapter.MineFollowAdapter;
import com.lanmei.bilan.api.MineFollowApi;
import com.lanmei.bilan.bean.MineFollowBean;
import com.xson.common.app.BaseActivity;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.SwipeRefreshController;
import com.xson.common.widget.CenterTitleToolbar;
import com.xson.common.widget.DividerItemDecoration;
import com.xson.common.widget.SmartSwipeRefreshLayout;

import butterknife.InjectView;


/**
 * 关注列表
 */
public class MineFollowActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    CenterTitleToolbar mToolbar;

    @InjectView(R.id.pull_refresh_rv)
    SmartSwipeRefreshLayout smartSwipeRefreshLayout;
    MineFollowAdapter mAdapter;

    private SwipeRefreshController<NoPageListBean<MineFollowBean>> controller;

    @Override
    public int getContentViewId() {
        return R.layout.activity_single_listview;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        setSupportActionBar(mToolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle(R.string.my_follow);
        actionbar.setHomeAsUpIndicator(R.mipmap.back);

        smartSwipeRefreshLayout.initWithLinearLayout();
        smartSwipeRefreshLayout.getRecyclerView().addItemDecoration(new DividerItemDecoration(getContext()));


        MineFollowApi api = new MineFollowApi();
        api.uid = api.getUserId(this);
//        api.type = "0";//0|1|2 =>普通会员|名人堂|公众号
        mAdapter = new MineFollowAdapter(this);
        smartSwipeRefreshLayout.setAdapter(mAdapter);
        controller = new SwipeRefreshController<NoPageListBean<MineFollowBean>>(this, smartSwipeRefreshLayout, api, mAdapter) {
        };
        controller.loadFirstPage();
    }

}
