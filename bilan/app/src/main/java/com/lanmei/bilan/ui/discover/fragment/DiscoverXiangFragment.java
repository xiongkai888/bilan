package com.lanmei.bilan.ui.discover.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lanmei.bilan.R;
import com.lanmei.bilan.adapter.ItemExpressSubAdapter;
import com.lanmei.bilan.api.DiscoverSubListApi;
import com.lanmei.bilan.bean.DiscoverSubListBean;
import com.xson.common.app.BaseFragment;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.SwipeRefreshController;
import com.xson.common.widget.DividerItemDecoration;
import com.xson.common.widget.SmartSwipeRefreshLayout;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xkai on 2018/1/31.
 * 项目速递
 */

public class DiscoverXiangFragment extends BaseFragment {


    @InjectView(R.id.express_tv)
    TextView expressTv;////速递
    @InjectView(R.id.dream_tv)
    TextView dreamTv;//梦想
    TextView[] textViewsArr;//速递、梦想
    //    @InjectView(R.id.viewPager)
//    NoScrollViewPager mViewPager;
//    ItemExpressAdapter mAdapter;
    @InjectView(R.id.pull_refresh_rv)
    SmartSwipeRefreshLayout refreshLayout;
    ItemExpressSubAdapter mAdapter;
    private SwipeRefreshController<NoPageListBean<DiscoverSubListBean>> controller;

    DiscoverSubListApi api;
    @Override
    public int getContentViewId() {
        return R.layout.fragment_discover_xiang;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        textViewsArr = new TextView[]{expressTv, dreamTv};
//        selectTab(textViewsArr[0].getId());

        refreshLayout.initWithLinearLayout();
        refreshLayout.getRecyclerView().addItemDecoration(new DividerItemDecoration(context));


        api = new DiscoverSubListApi();
        api.uid = api.getUserId(context);
        api.brand_id = getArguments().getString("id");
        mAdapter = new ItemExpressSubAdapter(context);
        refreshLayout.setAdapter(mAdapter);
        controller = new SwipeRefreshController<NoPageListBean<DiscoverSubListBean>>(context, refreshLayout, api, mAdapter) {
        };
        controller.loadFirstPage();

//        mViewPager.setNoScroll(true);
//        mAdapter = new ItemExpressAdapter(getChildFragmentManager(), getArguments().getString("id"));
//        mViewPager.setAdapter(mAdapter);
//        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                selectTab(textViewsArr[position].getId());//接单被选中
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
    }


    int viewId = 0;
    @OnClick({R.id.express_tv, R.id.dream_tv})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == viewId){
            return;
        }
        switch (view.getId()) {
            case R.id.express_tv://速递
                api.brand_id = "134";
                selectTab(textViewsArr[0].getId());//
                break;
            case R.id.dream_tv://梦想
                api.brand_id = "135";
                selectTab(textViewsArr[1].getId());//
                break;
        }
        if (controller != null){
            controller.loadFirstPage();
        }
        viewId = id;
    }

    // 选中改变tab字体、背景颜色
    protected void selectTab(int id) {
        for (TextView tv : textViewsArr) {
            if (tv.getId() == id) {
                tv.setSelected(true);
            } else {
                tv.setSelected(false);
            }
        }
    }

}
