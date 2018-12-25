package com.lanmei.bilan.ui.home;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.lanmei.bilan.R;
import com.lanmei.bilan.adapter.HomeTabAdapter;
import com.lanmei.bilan.ui.home.activity.PublishDynamicActivity;
import com.lanmei.bilan.utils.CommonUtils;
import com.xson.common.app.BaseFragment;
import com.xson.common.utils.IntentUtil;

import butterknife.InjectView;
import butterknife.OnClick;


/**
 * Created by xkai on 2018/1/3.
 * 首页
 */

public class HomeFragment extends BaseFragment {

//    @InjectView(R.id.toolbar)
//    CenterTitleToolbar mToolbar;

    @InjectView(R.id.tabLayout)
    TabLayout mTabLayout;
    @InjectView(R.id.viewPager)
    ViewPager mViewPager;
    @InjectView(R.id.imge_iv)
    ImageView imgeIv;

    HomeTabAdapter mTabAdapter;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_home_new;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
//        mToolbar.setTitle(R.string.app_name);
//        mToolbar.getMenu().clear();
//        mToolbar.inflateMenu(R.menu.menu_publish_icon);
//        mToolbar.setOnMenuItemClickListener(this);
//        mToolbar.setNavigationIcon(R.mipmap.home_search);
//        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!CommonUtils.isLogin(context)){
//                    return ;
//                }
//                IntentUtil.startActivity(context, SearchUserActivity.class);
//            }
//        });

        imgeIv.setVisibility(View.VISIBLE);
        mTabAdapter = new HomeTabAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mTabAdapter);
//        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewPager);
//        mTabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
    }


    @OnClick(R.id.imge_iv)
    public void onViewClicked() {
        if (!CommonUtils.isLogin(context)) {
            return;
        }
        IntentUtil.startActivity(context, PublishDynamicActivity.class);
    }

//    @Override
//    public boolean onMenuItemClick(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.action_publish_icon:
//                if (!CommonUtils.isLogin(context)){
//                    return true;
//                }
//                IntentUtil.startActivity(context, PublishDynamicActivity.class);
//                break;
//        }
//        return true;
//    }
}
