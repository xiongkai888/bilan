package com.lanmei.bilan.ui.home.fragment;

import android.os.Bundle;

import com.lanmei.bilan.R;
import com.lanmei.bilan.adapter.HomeLianAdapter;
import com.lanmei.bilan.api.HomeLianClassApi;
import com.lanmei.bilan.bean.HomeLianClassBean;
import com.xson.common.app.BaseFragment;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.SwipeRefreshController;
import com.xson.common.widget.DividerItemDecoration;
import com.xson.common.widget.SmartSwipeRefreshLayout;

import butterknife.InjectView;

/**
 * Created by xkai on 2018/1/3.
 * 链世界
 */

public class HomeLianFragment extends BaseFragment {

    @InjectView(R.id.pull_refresh_rv)
    SmartSwipeRefreshLayout lian;
    HomeLianAdapter mAdapter;
    private SwipeRefreshController<NoPageListBean<HomeLianClassBean>> controller;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_single_listview;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        initSwipeRefreshLayout();
    }


    private void initSwipeRefreshLayout() {
        lian.initWithLinearLayout();
        lian.getRecyclerView().addItemDecoration(new DividerItemDecoration(context));

        HomeLianClassApi api = new HomeLianClassApi();
        mAdapter = new HomeLianAdapter(context);
        lian.setAdapter(mAdapter);
        controller = new SwipeRefreshController<NoPageListBean<HomeLianClassBean>>(context, lian, api, mAdapter) {
        };
        controller.loadFirstPage();
    }


}
