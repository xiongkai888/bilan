package com.lanmei.bilan.ui.message.fragment;

import android.os.Bundle;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.exceptions.HyphenateException;
import com.lanmei.bilan.R;
import com.lanmei.bilan.adapter.MessageSheAdapter;
import com.lanmei.bilan.api.SheQuApi;
import com.lanmei.bilan.bean.HomeLianClassBean;
import com.lanmei.bilan.event.JoinGroupEvent;
import com.lanmei.bilan.monitor.JoinGroupListener;
import com.lanmei.bilan.ui.home.activity.SearchUserActivity;
import com.lanmei.bilan.utils.CommonUtils;
import com.xson.common.app.BaseFragment;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.SwipeRefreshController;
import com.xson.common.utils.IntentUtil;
import com.xson.common.utils.L;
import com.xson.common.utils.UIHelper;
import com.xson.common.widget.DividerItemDecoration;
import com.xson.common.widget.SmartSwipeRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xkai on 2018/1/3.
 * 消息-中的-社区
 */

public class MessageSheFragment extends BaseFragment {

    @InjectView(R.id.pull_refresh_rv)
    SmartSwipeRefreshLayout smartSwipeRefreshLayout;
    MessageSheAdapter mAdapter;
    private SwipeRefreshController<NoPageListBean<HomeLianClassBean>> controller;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_message_she;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        initSwipeRefreshLayout();
    }


    private void initSwipeRefreshLayout() {
        smartSwipeRefreshLayout.initWithLinearLayout();
        smartSwipeRefreshLayout.getRecyclerView().addItemDecoration(new DividerItemDecoration(context));

        SheQuApi api = new SheQuApi();
        mAdapter = new MessageSheAdapter(context);
        smartSwipeRefreshLayout.setAdapter(mAdapter);
        controller = new SwipeRefreshController<NoPageListBean<HomeLianClassBean>>(context, smartSwipeRefreshLayout, api, mAdapter) {
        };
        controller.loadFirstPage();
        mAdapter.setJoinGroupListener(new JoinGroupListener() {
            @Override
            public void joinGroup(final String groupId) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        addGroup(groupId);
                    }
                }).start();
            }
        });
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    private void addGroup(final String groupid) {
        try {
            EMGroup group = EMClient.getInstance().groupManager().getGroupFromServer(groupid);
            boolean join = group.isMembersOnly();
            L.d("EMGroup", "join:" + join);
            if (!join) {//如果群开群是自由加入的，即group.isMembersOnly()为false，直接join
                EMClient.getInstance().groupManager().asyncJoinGroup(group.getGroupId(), new EMCallBack() {

                    @Override
                    public void onSuccess() {
                        CommonUtils.startChatActivity(context, groupid, true);
//                    UIHelper.ToastMessage(context, "请求加入群成功");
                        EventBus.getDefault().post(new JoinGroupEvent(getString(R.string.join_succeed)));
                        L.d("EMGroup", "请求加入群成功");
                    }

                    @Override
                    public void onError(int i, String s) {
//                    UIHelper.ToastMessage(context, s);
                        EventBus.getDefault().post(new JoinGroupEvent(s));
                        L.d("EMGroup", s);
                    }

                    @Override
                    public void onProgress(int i, String s) {
                        L.d("EMGroup", "i:" + i + ", s:" + s);
                    }
                });
//                                EMClient.getInstance().groupManager().joinGroup(groupid);//需异步处理
            } else {//需要申请和验证才能加入的，即group.isMembersOnly()为true，调用下面方法
                EMClient.getInstance().groupManager().asyncApplyJoinToGroup(group.getGroupId(), "求加入", new EMCallBack() {
                    @Override
                    public void onSuccess() {
//                        CommonUtils.startChatActivity(context, groupid, true);
//                    UIHelper.ToastMessage(context, "请求加入群成功");
                        EventBus.getDefault().post(new JoinGroupEvent(getString(R.string.wait_verify)));
                        L.d("EMGroup", "已申请添加，等待群主验证");
                    }

                    @Override
                    public void onError(int i, String s) {
//                    UIHelper.ToastMessage(context, s);
                        EventBus.getDefault().post(new JoinGroupEvent(s));
                        L.d("EMGroup", s);
                    }

                    @Override
                    public void onProgress(int i, String s) {
                        L.d("EMGroup", "i:" + i + ", s:" + s);
                    }
                });
//                                EMClient.getInstance().groupManager().applyJoinToGroup(groupid, "求加入");//需异步处理
            }
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
    }

    //加入群监听
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void joinGroupEvent(JoinGroupEvent event) {
        UIHelper.ToastMessage(context, event.getEvnet());
    }

    @OnClick(R.id.ll_search)
    public void onViewClicked() {
        IntentUtil.startActivity(context,1,SearchUserActivity.class);
    }
}
