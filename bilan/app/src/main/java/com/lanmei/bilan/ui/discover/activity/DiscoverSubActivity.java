package com.lanmei.bilan.ui.discover.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;

import com.lanmei.bilan.R;
import com.lanmei.bilan.adapter.DiscoverSubAdapter;
import com.lanmei.bilan.api.DiscoverSubListApi;
import com.lanmei.bilan.bean.DiscoverSubListBean;
import com.xson.common.app.BaseActivity;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.SwipeRefreshController;
import com.xson.common.utils.StringUtils;
import com.xson.common.widget.CenterTitleToolbar;
import com.xson.common.widget.SmartSwipeRefreshLayout;

import butterknife.InjectView;

/**
 * Created by xkai on 2018/1/13.
 * 发现点击item进去的界面 区块宝库、发烧设备等
 */

public class DiscoverSubActivity extends BaseActivity{

    @InjectView(R.id.toolbar)
    CenterTitleToolbar mToolbar;
    @InjectView(R.id.pull_refresh_rv)
    SmartSwipeRefreshLayout smartSwipeRefreshLayout;
    DiscoverSubAdapter mAdapter;
    private SwipeRefreshController<NoPageListBean<DiscoverSubListBean>> controller;
    String name;
    String brandId;

    @Override
    public int getContentViewId() {
        return R.layout.activity_single_listview_on_divider;
    }

    @Override
    public void initIntent(Intent intent) {
        super.initIntent(intent);
        Bundle bundle = intent.getBundleExtra("bundle");
        if (StringUtils.isEmpty(bundle)){
            return;
        }
        name = bundle.getString("name");
        brandId = bundle.getString("brandId");
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        setSupportActionBar(mToolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle(name);
        actionbar.setHomeAsUpIndicator(R.mipmap.back);

        smartSwipeRefreshLayout.initGridLinearLayout(2);
//        smartSwipeRefreshLayout.getRecyclerView().addItemDecoration(new DividerItemDecoration(this));

        DiscoverSubListApi api = new DiscoverSubListApi();
        api.uid = api.getUserId(this);
        api.brand_id = brandId;
        mAdapter = new DiscoverSubAdapter(this);
        smartSwipeRefreshLayout.setAdapter(mAdapter);
        controller = new SwipeRefreshController<NoPageListBean<DiscoverSubListBean>>(this, smartSwipeRefreshLayout, api, mAdapter) {
        };
//        mAdapter.notifyDataSetChanged();
        controller.loadFirstPage();
    }
}
