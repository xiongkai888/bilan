package com.lanmei.bilan.ui.mine.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;

import com.lanmei.bilan.R;
import com.lanmei.bilan.adapter.MineArticleAdapter;
import com.lanmei.bilan.api.MineArticleApi;
import com.lanmei.bilan.bean.NewsJingBean;
import com.lanmei.bilan.event.PublishArticleEvent;
import com.xson.common.app.BaseActivity;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.SwipeRefreshController;
import com.xson.common.utils.IntentUtil;
import com.xson.common.widget.CenterTitleToolbar;
import com.xson.common.widget.SmartSwipeRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.InjectView;


/**
 * 我的文章
 */
public class MineArticleActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    CenterTitleToolbar mToolbar;

    @InjectView(R.id.pull_refresh_rv)
    SmartSwipeRefreshLayout smartSwipeRefreshLayout;
    MineArticleAdapter mAdapter;

    private SwipeRefreshController<NoPageListBean<NewsJingBean>> controller;

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
        actionbar.setTitle(R.string.my_article);
        actionbar.setHomeAsUpIndicator(R.mipmap.back);

        smartSwipeRefreshLayout.initWithLinearLayout();

        MineArticleApi api = new MineArticleApi();
        api.uid = api.getUserId(this);
        mAdapter = new MineArticleAdapter(this);
        smartSwipeRefreshLayout.setAdapter(mAdapter);
        controller = new SwipeRefreshController<NoPageListBean<NewsJingBean>>(this, smartSwipeRefreshLayout, api, mAdapter) {
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
//                UIHelper.ToastMessage(getContext(),R.string.developing);
                IntentUtil.startActivity(this, PublishArticleActivity.class);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    //文章发布成功时调用
    @Subscribe
    public void publishArticleEvent(PublishArticleEvent event){
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
