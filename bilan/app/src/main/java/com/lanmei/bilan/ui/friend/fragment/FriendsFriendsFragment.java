package com.lanmei.bilan.ui.friend.fragment;

import android.os.Bundle;

import com.lanmei.bilan.R;
import com.lanmei.bilan.adapter.FriendsFriendsAdapter;
import com.lanmei.bilan.api.FriendsApi;
import com.lanmei.bilan.bean.FriendsBean;
import com.xson.common.app.BaseFragment;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.SwipeRefreshController;
import com.xson.common.widget.DividerItemDecoration;
import com.xson.common.widget.SmartSwipeRefreshLayout;

import butterknife.InjectView;

/**
 * Created by xkai on 2018/1/15.
 * 好友的好友列表
 */

public class FriendsFriendsFragment extends BaseFragment {

    @InjectView(R.id.pull_refresh_rv)
    SmartSwipeRefreshLayout smartSwipeRefreshLayout;
    FriendsFriendsAdapter mAdapter;
    private SwipeRefreshController<NoPageListBean<FriendsBean>> controller;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_single_listview;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        initSwipeRefreshLayout();
    }

    String uid;

    private void initSwipeRefreshLayout() {
        smartSwipeRefreshLayout.initWithLinearLayout();
        smartSwipeRefreshLayout.getRecyclerView().addItemDecoration(new DividerItemDecoration(context));

        Bundle bundle = getArguments();
        if (bundle != null){
            uid = bundle.getString("uid");
        }
        FriendsApi api = new FriendsApi();
        api.uid = uid;
        mAdapter = new FriendsFriendsAdapter(context);
        smartSwipeRefreshLayout.setAdapter(mAdapter);
        controller = new SwipeRefreshController<NoPageListBean<FriendsBean>>(context, smartSwipeRefreshLayout, api, mAdapter) {
        };
        controller.loadFirstPage();
    }


}
