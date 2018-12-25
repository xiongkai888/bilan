package com.lanmei.bilan.ui.mine.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.TextView;

import com.lanmei.bilan.R;
import com.lanmei.bilan.adapter.MineOrderAdapter;
import com.xson.common.app.BaseActivity;
import com.xson.common.widget.CenterTitleToolbar;

import butterknife.InjectView;
import butterknife.OnClick;


/**
 * 我的订单
 */
public class MineOrderActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    CenterTitleToolbar mToolbar;

    @InjectView(R.id.viewPager)
    ViewPager viewPager;
    TextView[] textViewsArr;//
    MineOrderAdapter adapter;
    @InjectView(R.id.all_tv)
    TextView allTv;
    @InjectView(R.id.no_pay_tv)
    TextView noPayTv;
    @InjectView(R.id.wait_harvest_tv)
    TextView waitHarvestTv;
    @InjectView(R.id.done_tv)
    TextView doneTv;

    @Override
    public int getContentViewId() {
        return R.layout.activity_mine_order;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        setSupportActionBar(mToolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle(R.string.my_order);
        actionbar.setHomeAsUpIndicator(R.mipmap.back);

        textViewsArr = new TextView[]{allTv, noPayTv, waitHarvestTv,doneTv};
        selectTab(textViewsArr[0].getId());//优质被选中
        adapter = new MineOrderAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

    int viewId = 0;

    // 选中改变tab字体、背景颜色
    protected void selectTab(int id) {
        viewId = id;
        for (TextView tv : textViewsArr) {
            if (tv.getId() == id) {
                tv.setSelected(true);
            } else {
                tv.setSelected(false);
            }
        }
    }


    @OnClick({R.id.all_tv, R.id.no_pay_tv, R.id.wait_harvest_tv, R.id.done_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.all_tv:
                viewPager.setCurrentItem(0);
                break;
            case R.id.no_pay_tv:
                viewPager.setCurrentItem(1);
                break;
            case R.id.wait_harvest_tv:
                viewPager.setCurrentItem(2);
                break;
            case R.id.done_tv:
                viewPager.setCurrentItem(3);
                break;
        }
    }
}
