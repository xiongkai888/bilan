package com.lanmei.bilan.ui.market;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.lanmei.bilan.R;
import com.lanmei.bilan.adapter.MarketTabNewAdapter;
import com.lanmei.bilan.api.MarketClassifyApi;
import com.lanmei.bilan.bean.MarketClassifyBean;
import com.xson.common.app.BaseFragment;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.utils.StringUtils;
import com.xson.common.widget.CenterTitleToolbar;

import java.util.List;

import butterknife.InjectView;


/**
 * Created by xkai on 2018/1/3.
 * 行情
 */

public class MarketFragment extends BaseFragment {

    @InjectView(R.id.toolbar)
    CenterTitleToolbar mToolbar;
    @InjectView(R.id.tabLayout)
    TabLayout mTabLayout;
    @InjectView(R.id.viewPager)
    ViewPager mViewPager;

    MarketTabNewAdapter mTabAdapter;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        mToolbar.setTitle("行情");

        initClassify();
    }

    private void initClassify() {
        MarketClassifyApi api = new MarketClassifyApi();
        HttpClient.newInstance(context).request(api, new BeanRequest.SuccessListener<NoPageListBean<MarketClassifyBean>>() {
            @Override
            public void onResponse(NoPageListBean<MarketClassifyBean> response) {
                if (context == null) {
                    return;
                }
                initTabLayout(response.data);
            }
        });
    }

    private void initTabLayout(List<MarketClassifyBean> list) {
        if (StringUtils.isEmpty(list)) {
            return;
        }
        mTabAdapter = new MarketTabNewAdapter(getChildFragmentManager(),list);
        mViewPager.setAdapter(mTabAdapter);
//        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }
}
