package com.lanmei.bilan.ui.news;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.lanmei.bilan.R;
import com.lanmei.bilan.adapter.NewsTabAdapter;
import com.lanmei.bilan.bean.UserBean;
import com.lanmei.bilan.ui.mine.activity.PublishArticleActivity;
import com.lanmei.bilan.utils.CommonUtils;
import com.xson.common.app.BaseFragment;
import com.xson.common.utils.IntentUtil;
import com.xson.common.utils.StringUtils;

import butterknife.InjectView;
import butterknife.OnClick;


/**
 * Created by xkai on 2018/1/3.
 * 资讯
 */

public class NewsFragment extends BaseFragment {

    @InjectView(R.id.tabLayout)
    TabLayout mTabLayout;
    @InjectView(R.id.viewPager)
    ViewPager mViewPager;
    @InjectView(R.id.imge_iv)
    ImageView imgeIv;

    NewsTabAdapter mTabAdapter;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_home_new;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        UserBean userBean = CommonUtils.getUserBean(context);
        if (userBean != null && StringUtils.isSame(userBean.getInformation(), CommonUtils.isOne)) {
            imgeIv.setVisibility(View.VISIBLE);
        } else {
            imgeIv.setVisibility(View.GONE);
        }


        mTabAdapter = new NewsTabAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mTabAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewPager);

        mViewPager.setCurrentItem(1);
    }

    @OnClick(R.id.imge_iv)
    public void onViewClicked() {
        if (!CommonUtils.isLogin(context)) {
            return;
        }
        IntentUtil.startActivity(context, PublishArticleActivity.class);
//        IntentUtil.startActivity(context, PublishDynamicActivity.class);
    }
}
