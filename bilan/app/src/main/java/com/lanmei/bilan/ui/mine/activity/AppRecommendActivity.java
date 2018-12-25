package com.lanmei.bilan.ui.mine.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.lanmei.bilan.R;
import com.lanmei.bilan.adapter.AppRecommendAdapter;
import com.lanmei.bilan.api.AppRecommendApi;
import com.lanmei.bilan.bean.HomeLianClassBean;
import com.xson.common.app.BaseActivity;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.SwipeRefreshController;
import com.xson.common.widget.DividerItemDecoration;
import com.xson.common.widget.SmartSwipeRefreshLayout;

import butterknife.InjectView;

/**
 * 应用推荐
 */
public class AppRecommendActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.pull_refresh_rv)
    SmartSwipeRefreshLayout smartSwipeRefreshLayout;
    AppRecommendAdapter mAdapter;
    private SwipeRefreshController<NoPageListBean<HomeLianClassBean>> controller;

    @Override
    public int getContentViewId() {
        return R.layout.activity_single_listview;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        setSupportActionBar(mToolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle(R.string.app_recommend);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.mipmap.back);

        smartSwipeRefreshLayout.initWithLinearLayout();
        smartSwipeRefreshLayout.getRecyclerView().addItemDecoration(new DividerItemDecoration(this));

        AppRecommendApi api = new AppRecommendApi();
        mAdapter = new AppRecommendAdapter(this);
        smartSwipeRefreshLayout.setAdapter(mAdapter);
        controller = new SwipeRefreshController<NoPageListBean<HomeLianClassBean>>(this, smartSwipeRefreshLayout, api, mAdapter) {
        };
        controller.loadFirstPage();
    }

}
