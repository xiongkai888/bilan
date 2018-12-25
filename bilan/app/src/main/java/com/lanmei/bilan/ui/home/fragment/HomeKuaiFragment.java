package com.lanmei.bilan.ui.home.fragment;

import android.os.Bundle;

import com.lanmei.bilan.R;
import com.lanmei.bilan.adapter.HomeKuaiAdapter;
import com.lanmei.bilan.api.HomeBiApi;
import com.lanmei.bilan.bean.HomeBiBean;
import com.xson.common.app.BaseFragment;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.SwipeRefreshController;
import com.xson.common.widget.DividerItemDecoration;
import com.xson.common.widget.SmartSwipeRefreshLayout;

import butterknife.InjectView;

/**
 * Created by xkai on 2018/1/3.
 * 快讯
 */

public class HomeKuaiFragment extends BaseFragment {

    @InjectView(R.id.pull_refresh_rv)
    SmartSwipeRefreshLayout bi;
    HomeKuaiAdapter mAdapter;
    private SwipeRefreshController<NoPageListBean<HomeBiBean>> controller;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_single_listview;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        initSwipeRefreshLayout();
//        if (!EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().register(this);
//        }
    }


    private void initSwipeRefreshLayout() {
        bi.initWithLinearLayout();
        bi.getRecyclerView().addItemDecoration(new DividerItemDecoration(context));

        HomeBiApi api = new HomeBiApi();
        api.cid = "1";
//        api.uid = api.getUserId(context);
        mAdapter = new HomeKuaiAdapter(context);
        bi.setAdapter(mAdapter);
        controller = new SwipeRefreshController<NoPageListBean<HomeBiBean>>(context, bi, api, mAdapter) {
        };
        mAdapter.notifyDataSetChanged();
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
    }

}
