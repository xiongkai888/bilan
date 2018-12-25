package com.lanmei.bilan.ui.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

import com.lanmei.bilan.R;
import com.lanmei.bilan.adapter.MineDynamicAdapter;
import com.lanmei.bilan.api.HomeQuApi;
import com.lanmei.bilan.bean.HomeQuBean;
import com.lanmei.bilan.event.AddDynamicEvent;
import com.lanmei.bilan.event.HomeQuLikeEvent;
import com.lanmei.bilan.helper.ShareHelper;
import com.lanmei.bilan.helper.ShareListener;
import com.lanmei.bilan.ui.home.activity.PublishDynamicActivity;
import com.lanmei.bilan.widget.ShareNewsView;
import com.xson.common.app.BaseActivity;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.SwipeRefreshController;
import com.xson.common.utils.IntentUtil;
import com.xson.common.utils.StringUtils;
import com.xson.common.widget.DividerItemDecoration;
import com.xson.common.widget.SmartSwipeRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.InjectView;

/**
 * 我的动态
 */
public class MineDynamicActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.pull_refresh_rv)
    SmartSwipeRefreshLayout smartSwipeRefreshLayout;
    MineDynamicAdapter mAdapter;
    private SwipeRefreshController<NoPageListBean<HomeQuBean>> controller;

    private ShareHelper mShareHelper;

    @Override
    public int getContentViewId() {
        return R.layout.activity_single_listview;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        setSupportActionBar(mToolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle(R.string.my_dynamic);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.mipmap.back);

        //分享帮助初始化
        mShareHelper = new ShareHelper(this);

        smartSwipeRefreshLayout.initWithLinearLayout();
        smartSwipeRefreshLayout.getRecyclerView().addItemDecoration(new DividerItemDecoration(this));

        HomeQuApi api = new HomeQuApi();
        api.uid = api.getUserId(this);
        mAdapter = new MineDynamicAdapter(this,2);
        smartSwipeRefreshLayout.setAdapter(mAdapter);
        controller = new SwipeRefreshController<NoPageListBean<HomeQuBean>>(this, smartSwipeRefreshLayout, api, mAdapter) {
        };
        controller.loadFirstPage();

        mAdapter.setShareListener(new ShareListener() {
            @Override
            public void share(String content, String time, List<String> list, String type) {
                ShareNewsView view = (ShareNewsView) LayoutInflater.from(getContext()).inflate(R.layout.view_capture, null);
                view.share(content,time,list,type);
                mShareHelper.share(view);
            }
        });

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
                IntentUtil.startActivity(this, PublishDynamicActivity.class);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 结果返回
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mShareHelper.onActivityResult(requestCode, resultCode, data);
    }

    //评论详情时或点赞时调用
    @Subscribe
    public void commEvent(HomeQuLikeEvent event){
        if (mAdapter != null){
            List<HomeQuBean> list = mAdapter.getData();
            if (StringUtils.isEmpty(list)){
                return;
            }
            String commNum = event.getCommNum();
            String like = event.getLike();
            String liked = event.getLiked();
            String id = event.getId();
            int size = list.size();
            for (int i = 0;i< size;i++){
                HomeQuBean bean = list.get(i);
                if (StringUtils.isSame(id,bean.getId())){
                    bean.setLike(like);
                    bean.setLiked(liked);
                    bean.setReviews(commNum);
                    mAdapter.notifyDataSetChanged();
                    return;
                }
            }
        }
    }
    //发布动态是调用
    @Subscribe
    public void addDynamicEvent(AddDynamicEvent event){
        if (controller != null){
            controller.loadFirstPage();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        mShareHelper.onDestroy();
    }

}
