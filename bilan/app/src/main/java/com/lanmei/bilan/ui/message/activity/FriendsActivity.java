package com.lanmei.bilan.ui.message.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;

import com.lanmei.bilan.R;
import com.lanmei.bilan.adapter.FriendsAdapter;
import com.lanmei.bilan.api.FriendsApi;
import com.lanmei.bilan.bean.FriendsBean;
import com.lanmei.bilan.event.AddFriendsEvent;
import com.lanmei.bilan.event.AttentionFriendEvent;
import com.xson.common.app.BaseActivity;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.SwipeRefreshController;
import com.xson.common.utils.StringUtils;
import com.xson.common.widget.CenterTitleToolbar;
import com.xson.common.widget.DividerItemDecoration;
import com.xson.common.widget.SmartSwipeRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.InjectView;


/**
 * 消息 好友
 */
public class FriendsActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    CenterTitleToolbar mToolbar;

    @InjectView(R.id.pull_refresh_rv)
    SmartSwipeRefreshLayout smartSwipeRefreshLayout;
    FriendsAdapter mAdapter;

    private SwipeRefreshController<NoPageListBean<FriendsBean>> controller;

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
        actionbar.setTitle(R.string.friends);
        actionbar.setHomeAsUpIndicator(R.mipmap.back);

        smartSwipeRefreshLayout.initWithLinearLayout();
        smartSwipeRefreshLayout.getRecyclerView().addItemDecoration(new DividerItemDecoration(getContext()));


        FriendsApi api = new FriendsApi();
        api.uid = api.getUserId(this);
//        api.type = "0";//0|1|2 =>普通会员|名人堂|公众号friend/index
        mAdapter = new FriendsAdapter(this);
        smartSwipeRefreshLayout.setAdapter(mAdapter);
        controller = new SwipeRefreshController<NoPageListBean<FriendsBean>>(this, smartSwipeRefreshLayout, api, mAdapter) {
        };
        controller.loadFirstPage();
        EventBus.getDefault().register(this);
    }

    //加好友成功时调用
    @Subscribe
    public void addFriendsEvent(AddFriendsEvent event) {
        if (controller != null) {
            controller.loadFirstPage();
        }
    }

    //新的朋友点击关注时候调用
    @Subscribe
    public void setFollowByUidEvent(AttentionFriendEvent event) {
        if (StringUtils.isEmpty(mAdapter)){
            return;
        }
        List<FriendsBean> list = mAdapter.getData();
        if (StringUtils.isEmpty(list)) {
            return;
        }
        String id = event.getUid();
        for (FriendsBean bean : list) {
            if (!StringUtils.isEmpty(bean) && StringUtils.isSame(bean.getId(),id)) {
                bean.setFollowed(event.getFollowed());
                mAdapter.notifyDataSetChanged();
                return;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
