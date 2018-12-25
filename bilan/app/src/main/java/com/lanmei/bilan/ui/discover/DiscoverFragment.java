package com.lanmei.bilan.ui.discover;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.lanmei.bilan.R;
import com.lanmei.bilan.adapter.DiscoverTabAdapter;
import com.lanmei.bilan.event.ToMallEvent;
import com.xson.common.app.BaseFragment;
import com.xson.common.widget.CenterTitleToolbar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.InjectView;


/**
 * Created by xkai on 2018/1/3.
 * 发现
 */

public class DiscoverFragment extends BaseFragment {

    @InjectView(R.id.toolbar)
    CenterTitleToolbar mToolbar;
    DiscoverTabAdapter mTabAdapter;
    @InjectView(R.id.tabLayout)
    TabLayout mTabLayout;
    @InjectView(R.id.viewPager)
    ViewPager mViewPager;


    @Override
    public int getContentViewId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

        mToolbar.setTitle(getString(R.string.discover));

        mTabAdapter = new DiscoverTabAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mTabAdapter);
//        mViewPager.setOffscreenPageLimit(2);
        mTabLayout.setupWithViewPager(mViewPager);

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Subscribe
    public void toMall(ToMallEvent event){
        mViewPager.setCurrentItem(1);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
