package com.lanmei.bilan.ui.discover.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.lanmei.bilan.R;
import com.lanmei.bilan.adapter.ItemExpressAdapter;
import com.xson.common.app.BaseActivity;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 项目速递（速递、梦想）
 */
public class ItemExpressActivity extends BaseActivity {

    TextView[] textViewsArr;//速递、梦想
    @InjectView(R.id.message_tv)
    TextView suTv;//速递
    @InjectView(R.id.community_tv)
    TextView mengTv;//梦想
    @InjectView(R.id.viewPager)
    ViewPager mViewPager;
    ItemExpressAdapter mAdapter;

    @Override
    public int getContentViewId() {
        return R.layout.activity_item_express;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        suTv.setText("速递");
        mengTv.setText("梦想");
        textViewsArr = new TextView[]{suTv, mengTv};
        selectTab(textViewsArr[0].getId());
        mAdapter = new ItemExpressAdapter(getSupportFragmentManager(),getIntent().getStringExtra("value"));
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

    @OnClick({R.id.message_tv, R.id.community_tv, R.id.back_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.message_tv://速递
                mViewPager.setCurrentItem(0);
                break;
            case R.id.community_tv://梦想
                mViewPager.setCurrentItem(1);
                break;
            case R.id.back_iv://
               onBackPressed();
                break;
        }
    }

}
