package com.lanmei.bilan.ui.market.fragment;

import android.os.Bundle;

import com.lanmei.bilan.R;
import com.lanmei.bilan.adapter.MarketXinAdapter;
import com.lanmei.bilan.api.HomeQuApi;
import com.lanmei.bilan.bean.HomeQuBean;
import com.xson.common.app.BaseFragment;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.SwipeRefreshController;
import com.xson.common.utils.L;
import com.xson.common.widget.SmartSwipeRefreshLayout;

import butterknife.InjectView;

/**
 * Created by xkai on 2018/1/3.
 * 行情——新币上线
 */

public class MarketXinFragment extends BaseFragment {

    @InjectView(R.id.pull_refresh_rv)
    SmartSwipeRefreshLayout smartSwipeRefreshLayout;
    MarketXinAdapter mAdapter;
    private SwipeRefreshController<NoPageListBean<HomeQuBean>> controller;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_single_listview;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        initSwipeRefreshLayout();
    }


    private void initSwipeRefreshLayout() {

        Bundle bundle = getArguments();
        String cid = bundle.getString("cid");
        L.d("BaseAppCompatActivity",cid);
        smartSwipeRefreshLayout.initWithLinearLayout();

        HomeQuApi api = new HomeQuApi();
        mAdapter = new MarketXinAdapter(context);
        smartSwipeRefreshLayout.setAdapter(mAdapter);
        controller = new SwipeRefreshController<NoPageListBean<HomeQuBean>>(context, smartSwipeRefreshLayout, api, mAdapter) {
        };
//        controller.loadFirstPage();
        mAdapter.notifyDataSetChanged();
    }


}
