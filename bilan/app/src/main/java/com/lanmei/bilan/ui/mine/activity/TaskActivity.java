package com.lanmei.bilan.ui.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;

import com.lanmei.bilan.R;
import com.lanmei.bilan.adapter.TaskAdapter;
import com.lanmei.bilan.api.SignInListApi;
import com.lanmei.bilan.api.TaskApi;
import com.lanmei.bilan.api.TaskListApi;
import com.lanmei.bilan.bean.InviteBean;
import com.lanmei.bilan.bean.TaskBean;
import com.lanmei.bilan.event.TaskEvent;
import com.lanmei.bilan.helper.ShareHelper;
import com.lanmei.bilan.ui.home.activity.PublishDynamicActivity;
import com.lanmei.bilan.utils.AKDialog;
import com.lanmei.bilan.utils.CommonUtils;
import com.lanmei.bilan.utils.SharedAccount;
import com.xson.common.app.BaseActivity;
import com.xson.common.bean.BaseBean;
import com.xson.common.bean.DataBean;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.helper.SwipeRefreshController;
import com.xson.common.utils.IntentUtil;
import com.xson.common.utils.UIHelper;
import com.xson.common.widget.CenterTitleToolbar;
import com.xson.common.widget.DividerItemDecoration;
import com.xson.common.widget.SmartSwipeRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.InjectView;

/**
 * 任务
 */
public class TaskActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    CenterTitleToolbar mToolbar;

    @InjectView(R.id.pull_refresh_rv)
    SmartSwipeRefreshLayout smartSwipeRefreshLayout;
    TaskAdapter mAdapter;
    private SwipeRefreshController<NoPageListBean<TaskBean>> controller;
    private ShareHelper mShareHelper;
    InviteBean inviteBean;
    String taskId = "";

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
        actionbar.setTitle("任务");
        actionbar.setHomeAsUpIndicator(R.mipmap.back);

        smartSwipeRefreshLayout.initWithLinearLayout();
        smartSwipeRefreshLayout.getRecyclerView().addItemDecoration(new DividerItemDecoration(getContext()));


        TaskListApi api = new TaskListApi();
        api.uid = api.getUserId(this);
        mAdapter = new TaskAdapter(this);
        smartSwipeRefreshLayout.setAdapter(mAdapter);
        controller = new SwipeRefreshController<NoPageListBean<TaskBean>>(this, smartSwipeRefreshLayout, api, mAdapter) {
            @Override
            public boolean onSuccessResponse(NoPageListBean<TaskBean> response) {
                mAdapter.setData(response.data);
                return true;
            }
        };
        controller.loadFirstPage();
        mAdapter.setOnButtonClickListener(new TaskAdapter.OnButtonClickListener() {
            @Override
            public void onClick(final TaskBean bean) {
                switch (bean.getId()) {
                    case "1":
                        taskId = bean.getId();
                        loadList();
                        break;
                    case "2":
                        taskId = bean.getId();
                        if (inviteBean != null) {
                            mShareHelper.setShareUrl(inviteBean.getShare_url());
                            mShareHelper.showShareDialog();
                        } else {
                            CommonUtils.loadSystem(getContext(), new CommonUtils.LoadSystemListener() {
                                @Override
                                public void loadSystem(InviteBean invitebean) {
                                    inviteBean = invitebean;
                                    mShareHelper.setShareUrl(inviteBean.getShare_url());
                                    mShareHelper.showShareDialog();
                                }
                            });
                        }
                        break;
                    case "3":
                        taskId = bean.getId();
                        IntentUtil.startActivity(getContext(), PublishDynamicActivity.class);
                        break;
                    case "4":
                        if (bean.getStatus() == 0){
                            UIHelper.ToastMessage(getContext(),"未完成");
                        }
                        break;
                    case "5":
                        UIHelper.ToastMessage(getContext(), R.string.developing);
                        break;
                }
            }
        });

        //分享帮助初始化
        mShareHelper = new ShareHelper(this);
        inviteBean = SharedAccount.getInstance(this).getInviteBean();

        EventBus.getDefault().register(this);
    }


    private void loadList() {
        SignInListApi api = new SignInListApi();
        api.uid = api.getUserId(this);
        HttpClient.newInstance(this).loadingRequest(api, new BeanRequest.SuccessListener<DataBean<TaskBean>>() {
            @Override
            public void onResponse(DataBean<TaskBean> response) {
                if (isFinishing()) {
                    return;
                }
                AKDialog.showSignDialog(getContext(), response.data,new AKDialog.DoSignInListener() {
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
                controller.loadFirstPage();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mShareHelper.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 结果返回
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mShareHelper.onActivityResult(requestCode, resultCode, data);
    }

    //分享成功、发布成功后调用
    @Subscribe
    public void taskEvent(TaskEvent event) {
        doTask(taskId);
    }

}
