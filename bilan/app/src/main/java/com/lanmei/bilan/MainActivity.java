package com.lanmei.bilan;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;

import com.hyphenate.EMGroupChangeListener;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMMucSharedFile;
import com.hyphenate.chatuidemo.ui.NewFriendsMsgActivity;
import com.lanmei.bilan.adapter.MainPagerAdapter;
import com.lanmei.bilan.api.SignInListApi;
import com.lanmei.bilan.api.SystemSettingInfoApi;
import com.lanmei.bilan.api.TaskApi;
import com.lanmei.bilan.api.TaskListApi;
import com.lanmei.bilan.bean.TaskBean;
import com.lanmei.bilan.bean.UserBean;
import com.lanmei.bilan.event.AddGroupChangeEvent;
import com.lanmei.bilan.event.ExitLoginEvent;
import com.lanmei.bilan.event.LoginEMClientEvent;
import com.lanmei.bilan.event.ToMallEvent;
import com.lanmei.bilan.helper.ShareHelper;
import com.lanmei.bilan.helper.TabHelper;
import com.lanmei.bilan.helper.UserHelper;
import com.lanmei.bilan.ui.login.LoginActivity;
import com.lanmei.bilan.utils.AKDialog;
import com.lanmei.bilan.utils.CommonUtils;
import com.lanmei.bilan.utils.SharedAccount;
import com.xson.common.app.BaseActivity;
import com.xson.common.bean.BaseBean;
import com.xson.common.bean.DataBean;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.utils.IntentUtil;
import com.xson.common.utils.L;
import com.xson.common.utils.StringUtils;
import com.xson.common.utils.UIHelper;
import com.xson.common.widget.NoScrollViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * 首页
 */
public class MainActivity extends BaseActivity implements TabLayout.OnTabSelectedListener {

    @InjectView(R.id.viewPager)
    NoScrollViewPager mViewPager;
    @InjectView(R.id.tabLayout)
    TabLayout mTabLayout;
    private ShareHelper mShareHelper;

    private MainPagerAdapter mMainPagerAdapter;

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        int[] imageArray = {R.mipmap.main_home_on, R.mipmap.main_home_off, R.mipmap.main_message_on, R.mipmap.main_message_off, R.mipmap.main_market_on, R.mipmap.main_market_off, R.mipmap.main_discover_on, R.mipmap.main_discover_off, R.mipmap.main_mine_on, R.mipmap.main_mine_off};
        new TabHelper(this, getTitleList(), imageArray, mTabLayout, R.color.red);
        initViewPager();
        EventBus.getDefault().register(this);
        mViewPager.setNoScroll(true);

        if (!UserHelper.getInstance(this).hasLogin()) {
            return;
        }

        CommonUtils.loadSystem(this, null);
        //分享帮助初始化
        mShareHelper = new ShareHelper(this);
        initSign();
    }

    public ShareHelper getShareHelper() {
        return mShareHelper;
    }

    private void initSign() {
        TaskListApi api = new TaskListApi();
        api.uid = api.getUserId(this);
        HttpClient.newInstance(this).request(api, new BeanRequest.SuccessListener<NoPageListBean<TaskBean>>() {
            @Override
            public void onResponse(NoPageListBean<TaskBean> response) {
                if (isFinishing()) {
                    return;
                }
                initSignDialog(response.data);
            }
        });
    }

    private void initSignDialog(List<TaskBean> list) {
        if (StringUtils.isEmpty(list)) {
            return;
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            TaskBean bean = list.get(i);
            if (StringUtils.isSame(bean.getId(), CommonUtils.isOne) && bean.getStatus() == 0) {//未签到
                loadList(bean.getId());
                return;
            }
        }
    }


    private void loadList(final String taskId) {
        SignInListApi api = new SignInListApi();
        api.uid = api.getUserId(this);
        HttpClient.newInstance(this).request(api, new BeanRequest.SuccessListener<DataBean<TaskBean>>() {
            @Override
            public void onResponse(DataBean<TaskBean> response) {
                if (isFinishing()) {
                    return;
                }
                AKDialog.showSignDialog(getContext(), response.data, new AKDialog.DoSignInListener() {
                    @Override
                    public void sign() {
                        doTask(taskId);
                    }
                });
            }
        });
    }

    private void doTask(String id) {
        TaskApi api = new TaskApi();
        api.uid = api.getUserId(this);
        api.task = id;
        HttpClient.newInstance(this).loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                if (isFinishing()) {
                    return;
                }
                CommonUtils.loadUserInfo(getContext(), null);
                UIHelper.ToastMessage(getContext(), CommonUtils.getString(response));
            }
        });
    }

    public void initViewPager() {
        mMainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mMainPagerAdapter);
        mViewPager.setOffscreenPageLimit(4);
        mTabLayout.addOnTabSelectedListener(this);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        SystemSettingInfoApi api = new SystemSettingInfoApi();
        HttpClient.newInstance(this).request(api, new BeanRequest.SuccessListener<NoPageListBean<UserBean>>() {//客服信息
            @Override
            public void onResponse(NoPageListBean<UserBean> response) {
                if (isFinishing()) {
                    return;
                }
                List<UserBean> list = response.data;
                if (!StringUtils.isEmpty(list)) {
                    UserBean bean = list.get(0);
                    if (!StringUtils.isEmpty(bean)) {
                        SharedAccount.getInstance(MainActivity.this).saveServiceId(bean.getId());
                    }
                }
            }
        });
        CommonUtils.loadUserInfo(this, null);
        if (!EMClient.getInstance().isConnected()) {//是否登录的环信
            L.d("isConnected", EMClient.getInstance().isConnected() + "");
            return;
        }
        eMClientListener();
    }

    private void asyncGetJoinedGroupsFromServer() {
        EMClient.getInstance().groupManager().asyncGetJoinedGroupsFromServer(new EMValueCallBack<List<EMGroup>>() {
            @Override
            public void onSuccess(List<EMGroup> emGroups) {

            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    public static final String ACTION_SHOW_DISCOVER = "android.intent.action.SHOW_DISCOVER";
    public static final String ACTION_MAIN_FINISH = "android.intent.action.MAIN_FINISH";

    @Override
    protected void onNewIntent(Intent intent) {//应用已经运行或在后台运行时调用
        super.onNewIntent(intent);
        if (ACTION_SHOW_DISCOVER.equals(intent.getAction())) {
            mViewPager.setCurrentItem(3);
            EventBus.getDefault().post(new ToMallEvent());
        } else if (ACTION_MAIN_FINISH.equals(intent.getAction())) {
            if (!CommonUtils.isLogin(this)) {
                finish();
            }
        }
    }

    public static void showDiscover(Context context) {//至发现
        Intent intent = new Intent(ACTION_SHOW_DISCOVER);//
        intent.setClass(context, MainActivity.class);
        context.startActivity(intent);
    }

    public static void finishMainActivity(Context context) {//
        Intent intent = new Intent(ACTION_MAIN_FINISH);//
        intent.setClass(context, MainActivity.class);
        context.startActivity(intent);
    }

    public static final String ACTION_SHOW_MAINACTIVITY = "android.intent.action.SHOW_MAINACTIVITY";

    /**
     * @param context
     * @param application
     */
    public static void showMainActivity(Context context, Application application) {
        Intent intent = new Intent(ACTION_SHOW_MAINACTIVITY);
        intent.setClass(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        application.startActivity(intent);
    }

    private void eMClientListener() {
        L.d("isConnected", "登录了环信");
        asyncGetJoinedGroupsFromServer();
        EMClient.getInstance().groupManager().addGroupChangeListener(new EMGroupChangeListener() {

            @Override
            public void onInvitationReceived(String groupId, String groupName, String inviter, String reason) {
                //接收到群组加入邀请
                L.d("onRequestToJoinReceived", "接收到群组加入邀请");
            }

            @Override
            public void onRequestToJoinReceived(String groupId, String groupName, String applyer, String reason) {
                //用户申请加入群
                //                UIHelper.ToastMessage(MainActivity.this,groupId+","+groupName+","+applyer+","+applyer);
                L.d("onRequestToJoinReceived", "用户申请加入群");
                AddGroupChangeEvent event = new AddGroupChangeEvent(1);
                //                event.setGroupId(groupId);
                //                event.setGroupName(groupName);
                //                event.setApplyer(applyer);
                EventBus.getDefault().post(event);
            }

            @Override
            public void onRequestToJoinAccepted(String groupId, String groupName, String accepter) {
                //加群申请被同意
                L.d("onRequestToJoinReceived", "加群申请被同意");
                AddGroupChangeEvent event = new AddGroupChangeEvent(2);
                event.setGroupName(groupName);
                event.setAccepter(accepter);
                EventBus.getDefault().post(event);
            }

            @Override
            public void onRequestToJoinDeclined(String groupId, String groupName, String decliner, String reason) {
                //加群申请被拒绝
                L.d("onRequestToJoinReceived", "加群申请被拒绝");
            }

            @Override
            public void onInvitationAccepted(String groupId, String inviter, String reason) {
                //群组邀请被同意
                L.d("onRequestToJoinReceived", "群组邀请被同意");
            }

            @Override
            public void onInvitationDeclined(String groupId, String invitee, String reason) {
                //群组邀请被拒绝
                L.d("onRequestToJoinReceived", "群组邀请被拒绝");
            }

            @Override
            public void onUserRemoved(String s, String s1) {
                L.d("onRequestToJoinReceived", "onUserRemoved");
            }

            @Override
            public void onGroupDestroyed(String s, String s1) {
                L.d("onRequestToJoinReceived", "onGroupDestroyed");
            }

            @Override
            public void onAutoAcceptInvitationFromGroup(String groupId, String inviter, String inviteMessage) {
                //接收邀请时自动加入到群组的通知
                L.d("onRequestToJoinReceived", "接收邀请时自动加入到群组的通知");
            }

            @Override
            public void onMuteListAdded(String groupId, final List<String> mutes, final long muteExpire) {
                //成员禁言的通知
                L.d("onRequestToJoinReceived", "成员禁言的通知");
            }

            @Override
            public void onMuteListRemoved(String groupId, final List<String> mutes) {
                //成员从禁言列表里移除通知
                L.d("onRequestToJoinReceived", "成员从禁言列表里移除通知");
            }

            @Override
            public void onAdminAdded(String groupId, String administrator) {
                //增加管理员的通知
                L.d("onRequestToJoinReceived", "增加管理员的通知");
            }

            @Override
            public void onAdminRemoved(String groupId, String administrator) {
                //管理员移除的通知
                L.d("onRequestToJoinReceived", "管理员移除的通知");
            }

            @Override
            public void onOwnerChanged(String groupId, String newOwner, String oldOwner) {
                //群所有者变动通知
                L.d("onRequestToJoinReceived", "群所有者变动通知");
            }

            @Override
            public void onMemberJoined(final String groupId, final String member) {
                //群组加入新成员通知
                L.d("onRequestToJoinReceived", "群组加入新成员通知");
            }

            @Override
            public void onMemberExited(final String groupId, final String member) {
                //群成员退出通知
                L.d("onRequestToJoinReceived", "群成员退出通知");
            }

            @Override
            public void onAnnouncementChanged(String groupId, String announcement) {
                //群公告变动通知
                L.d("onRequestToJoinReceived", "群公告变动通知");
            }

            @Override
            public void onSharedFileAdded(String groupId, EMMucSharedFile sharedFile) {
                //增加共享文件的通知
                L.d("onRequestToJoinReceived", "增加共享文件的通知");
            }

            @Override
            public void onSharedFileDeleted(String groupId, String fileId) {
                //群共享文件删除通知
                L.d("onRequestToJoinReceived", "群共享文件删除通知");
            }
        });
    }

    int position;

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        final int position = tab.getPosition();
        if (position == 4 && !UserHelper.getInstance(this).hasLogin()) {
            IntentUtil.startActivity(this, LoginActivity.class);
            mTabLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mViewPager.setCurrentItem(position);
                    mViewPager.setCurrentItem(MainActivity.this.position);
                }
            }, 500);
            return;
        } else {
            this.position = position;
        }
        mViewPager.setCurrentItem(position);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    /**
     * 结果返回
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mShareHelper.onActivityResult(requestCode, resultCode, data);
    }

    private List<String> getTitleList() {
        List<String> titles = new ArrayList<>();
        titles.add("快讯");
        titles.add("资讯");
        titles.add("行情");
        titles.add("发现");
        titles.add("我的");
        return titles;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void addGroupChangeEvent(final AddGroupChangeEvent event) {
        int type = event.getType();
        switch (type) {
            case 1://用户申请加入群
                startActivity(new Intent(this, NewFriendsMsgActivity.class));
                break;
            case 2://加群申请被同意
                UIHelper.ToastMessage(this, event.getAccepter() + "同意你加入群" + event.getGroupName());
                asyncGetJoinedGroupsFromServer();
                break;
        }
    }

    @Subscribe
    public void loginEMClientEvent(LoginEMClientEvent event) {
        eMClientListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (mShareHelper != null){
            mShareHelper.onDestroy();
        }
    }

    //退出用户时
    @Subscribe
    public void exitLoginEvent(ExitLoginEvent event) {
        finish();
    }

}
