package com.lanmei.bilan.ui.market.fragment;

import android.os.Bundle;

import com.lanmei.bilan.R;
import com.lanmei.bilan.adapter.MarketChangAdapter;
import com.lanmei.bilan.api.MarketChangApi;
import com.lanmei.bilan.bean.MarketChangBean;
import com.lanmei.bilan.event.PublishBiClassEvent;
import com.lanmei.bilan.ui.market.activity.MarketChangPublishActivity;
import com.xson.common.app.BaseFragment;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.SwipeRefreshController;
import com.xson.common.utils.IntentUtil;
import com.xson.common.widget.DividerItemDecoration;
import com.xson.common.widget.SmartSwipeRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xkai on 2018/1/3.
 * 行情——场外发布
 */

public class MarketChangFragment extends BaseFragment {

    @InjectView(R.id.pull_refresh_rv)
    SmartSwipeRefreshLayout smartSwipeRefreshLayout;
    MarketChangAdapter mAdapter;
    private SwipeRefreshController<NoPageListBean<MarketChangBean>> controller;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_market_chang;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        initSwipeRefreshLayout();
    }


    private void initSwipeRefreshLayout() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        smartSwipeRefreshLayout.initWithLinearLayout();
        smartSwipeRefreshLayout.getRecyclerView().addItemDecoration(new DividerItemDecoration(context));

        MarketChangApi api = new MarketChangApi();
        mAdapter = new MarketChangAdapter(context);
        smartSwipeRefreshLayout.setAdapter(mAdapter);
        controller = new SwipeRefreshController<NoPageListBean<MarketChangBean>>(context, smartSwipeRefreshLayout, api, mAdapter) {
        };
        controller.loadFirstPage();
    }
    //场外发布成功时调用
    @Subscribe
    public void publishBiClassEvent(PublishBiClassEvent event){
        if (controller != null){
            controller.loadFirstPage();
        }
    }

    //发布
    @OnClick(R.id.publish_tv)
    public void onViewClicked() {
        IntentUtil.startActivity(context, MarketChangPublishActivity.class);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
