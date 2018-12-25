package com.lanmei.bilan.ui.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.lanmei.bilan.R;
import com.lanmei.bilan.adapter.HomeLianSubAdapter;
import com.lanmei.bilan.api.HomeLianContentApi;
import com.lanmei.bilan.bean.HomeLianClassBean;
import com.lanmei.bilan.bean.HomeLianContentBean;
import com.xson.common.app.BaseActivity;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.widget.CenterTitleToolbar;
import com.xson.common.widget.EmptyRecyclerView;

import butterknife.InjectView;

/**
 * 首页链世界 （更多）
 */
public class HomeLianSubActivity extends BaseActivity {


    @InjectView(R.id.toolbar)
    CenterTitleToolbar mToolbar;
    @InjectView(R.id.empty_view)
    View mEmptyView;
    @InjectView(R.id.recyclerView)
    EmptyRecyclerView mRecyclerView;
    HomeLianClassBean bean;

    @Override
    public int getContentViewId() {
        return R.layout.activity_single_recyclerview;
    }

    @Override
    public void initIntent(Intent intent) {
        super.initIntent(intent);
        Bundle bundle = intent.getBundleExtra("bundle");
        bean = (HomeLianClassBean)bundle.getSerializable("bean");
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        setSupportActionBar(mToolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.mipmap.back);
        actionbar.setTitle(bean.getClassname());
        mRecyclerView.setEmptyView(mEmptyView);

        HomeLianContentApi api = new HomeLianContentApi();
        api.classid = bean.getId();
        HttpClient.newInstance(this).loadingRequest(api, new BeanRequest.SuccessListener<NoPageListBean<HomeLianContentBean>>() {
            @Override
            public void onResponse(NoPageListBean<HomeLianContentBean> response) {
                HomeLianSubAdapter adapter = new HomeLianSubAdapter(HomeLianSubActivity.this);
                adapter.setData(response.data);
                mRecyclerView.setLayoutManager(new GridLayoutManager(HomeLianSubActivity.this, 3));
                mRecyclerView.setAdapter(adapter);
            }
        });
    }
}
