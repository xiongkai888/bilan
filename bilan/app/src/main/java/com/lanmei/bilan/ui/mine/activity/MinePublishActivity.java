package com.lanmei.bilan.ui.mine.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;

import com.lanmei.bilan.R;
import com.lanmei.bilan.adapter.MinePulishAdapter;
import com.lanmei.bilan.api.MinePublishApi;
import com.lanmei.bilan.bean.MinePublishBean;
import com.lanmei.bilan.event.PublishBiClassEvent;
import com.lanmei.bilan.ui.market.activity.MarketChangPublishActivity;
import com.xson.common.app.BaseActivity;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.SwipeRefreshController;
import com.xson.common.utils.IntentUtil;
import com.xson.common.widget.CenterTitleToolbar;
import com.xson.common.widget.DividerItemDecoration;
import com.xson.common.widget.SmartSwipeRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.InjectView;


/**
 * 我的发布
 */
public class MinePublishActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    CenterTitleToolbar mToolbar;

    @InjectView(R.id.pull_refresh_rv)
    SmartSwipeRefreshLayout smartSwipeRefreshLayout;
    MinePulishAdapter mAdapter;

    private SwipeRefreshController<NoPageListBean<MinePublishBean>> controller;

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
        actionbar.setTitle(R.string.my_publish);
        actionbar.setHomeAsUpIndicator(R.mipmap.back);

        smartSwipeRefreshLayout.initWithLinearLayout();
        smartSwipeRefreshLayout.getRecyclerView().addItemDecoration(new DividerItemDecoration(getContext()));


        MinePublishApi api = new MinePublishApi();
        api.userid = api.getUserId(this);
//        mAdapter = new HomeBiDetailsAdapter(this,bean,position);
        mAdapter = new MinePulishAdapter(this);
        smartSwipeRefreshLayout.setAdapter(mAdapter);
        controller = new SwipeRefreshController<NoPageListBean<MinePublishBean>>(this, smartSwipeRefreshLayout, api, mAdapter) {
        };
        controller.loadFirstPage();

        EventBus.getDefault().register(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_publish_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_publish_icon:
                IntentUtil.startActivity(this, MarketChangPublishActivity.class);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    //场外发布成功时调用
    @Subscribe
    public void publishBiClassEvent(PublishBiClassEvent event){
        if (controller != null){
            controller.loadFirstPage();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
