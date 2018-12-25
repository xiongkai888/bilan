package com.lanmei.bilan.ui.mine.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.lanmei.bilan.R;
import com.lanmei.bilan.adapter.PropertyAdapter;
import com.xson.common.app.BaseActivity;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 资产账本
 */
public class PropertyActivity extends BaseActivity {

    TextView[] textViewsArr;//
    @InjectView(R.id.message_tv)
    TextView mingTv;//消费明细
    @InjectView(R.id.community_tv)
    TextView chongTv;//充值记录
    @InjectView(R.id.viewPager)
    ViewPager mViewPager;
    PropertyAdapter mAdapter;

    @Override
    public int getContentViewId() {
        return R.layout.activity_property;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        mingTv.setText("消费明细");
        chongTv.setText("充值记录");
        textViewsArr = new TextView[]{mingTv, chongTv};
        selectTab(textViewsArr[0].getId());

        mAdapter = new PropertyAdapter(getSupportFragmentManager());
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

    @OnClick({R.id.back_iv, R.id.message_tv, R.id.community_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                onBackPressed();
                break;
            case R.id.message_tv:
                mViewPager.setCurrentItem(0);
//                IntentUtil.startActivity(this,WithdrawDepositActivity.class);
                break;
            case R.id.community_tv:
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
