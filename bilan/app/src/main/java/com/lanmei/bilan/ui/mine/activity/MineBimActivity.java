package com.lanmei.bilan.ui.mine.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;

import com.lanmei.bilan.R;
import com.lanmei.bilan.adapter.JiListAdapter;
import com.lanmei.bilan.api.JiListApi;
import com.lanmei.bilan.bean.JiListBean;
import com.lanmei.bilan.event.TransferEvent;
import com.xson.common.app.BaseActivity;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.SwipeRefreshController;
import com.xson.common.utils.IntentUtil;
import com.xson.common.utils.UIHelper;
import com.xson.common.widget.CenterTitleToolbar;
import com.xson.common.widget.DividerItemDecoration;
import com.xson.common.widget.SmartSwipeRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.InjectView;
import butterknife.OnClick;


/**
 * 我的Bim
 */
public class MineBimActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    CenterTitleToolbar mToolbar;

    @InjectView(R.id.pull_refresh_rv)
    SmartSwipeRefreshLayout smartSwipeRefreshLayout;
    JiListAdapter mAdapter;

    private SwipeRefreshController<NoPageListBean<JiListBean>> controller;

    @Override
    public int getContentViewId() {
        return R.layout.activity_mine_bim;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        setSupportActionBar(mToolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle("糖果宝盒");
        actionbar.setHomeAsUpIndicator(R.mipmap.back);

        smartSwipeRefreshLayout.initWithLinearLayout();
        smartSwipeRefreshLayout.getRecyclerView().addItemDecoration(new DividerItemDecoration(getContext()));


        JiListApi api = new JiListApi();
        api.uid = api.getUserId(this);
//        mAdapte = new HomeBiDetailsAdapter(this,bean,position);
        mAdapter = new JiListAdapter(this);
        smartSwipeRefreshLayout.setAdapter(mAdapter);
        controller = new SwipeRefreshController<NoPageListBean<JiListBean>>(this, smartSwipeRefreshLayout, api, mAdapter) {
        };
        controller.loadFirstPage();

        EventBus.getDefault().register(this);
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_transfer, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_transfer:
//                IntentUtil.startActivity(this,TransferActivity.class);
//                break;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    //转让积分后调用
    @Subscribe
    public void transferEvent(TransferEvent event) {
        if (controller != null) {
            controller.loadFirstPage();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @OnClick({R.id.exchange_tv, R.id.give_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.exchange_tv:
                UIHelper.ToastMessage(this,R.string.developing);
                break;
            case R.id.give_tv:
                IntentUtil.startActivity(this,TransferActivity.class);
                break;
        }
    }
}
