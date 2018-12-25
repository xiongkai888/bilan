package com.lanmei.bilan.ui.discover.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.lanmei.bilan.R;
import com.lanmei.bilan.adapter.DiscoverMallAdapter;
import com.xson.common.app.BaseFragment;
import com.xson.common.widget.NoScrollViewPager;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xkai on 2018/1/31.
 * 比特商城
 */

public class DiscoverMallFragment extends BaseFragment {

    @InjectView(R.id.express_tv)
    TextView expressTv;//
    @InjectView(R.id.dream_tv)
    TextView dreamTv;//
    TextView[] textViewsArr;//
    @InjectView(R.id.viewPager)
    NoScrollViewPager mViewPager;
    DiscoverMallAdapter mAdapter;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_discover_mall;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        expressTv.setText("发烧设备");
        dreamTv.setText("区块宝库");
        textViewsArr = new TextView[]{expressTv, dreamTv};
        selectTab(textViewsArr[0].getId());

//        mViewPager.setNoScroll(true);
        mAdapter = new DiscoverMallAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectTab(textViewsArr[position].getId());//接单被选中
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick({R.id.express_tv, R.id.dream_tv})
    public void onViewClicked(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.express_tv://速递
                mViewPager.setCurrentItem(0);
                break;
            case R.id.dream_tv://梦想
                mViewPager.setCurrentItem(1);
                break;
        }
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
