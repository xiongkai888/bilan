package com.lanmei.bilan.ui.message;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.hyphenate.chatuidemo.ui.NewFriendsMsgActivity;
import com.lanmei.bilan.R;
import com.lanmei.bilan.adapter.MessageAdapter;
import com.lanmei.bilan.ui.message.activity.FriendsActivity;
import com.lanmei.bilan.utils.CommonUtils;
import com.xson.common.app.BaseFragment;
import com.xson.common.utils.IntentUtil;

import butterknife.InjectView;
import butterknife.OnClick;


/**
 * Created by xkai on 2018/1/3.
 * 行情
 */

public class MessageFragment extends BaseFragment implements Toolbar.OnMenuItemClickListener, TabLayout.OnTabSelectedListener {

    //    TextView[] textViewsArr;//消息、社区
//    @InjectView(R.id.message_tv)
//    TextView messageTv;//社区
//    @InjectView(R.id.community_tv)
//    TextView communityTv;//消息
    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.tabLayout)
    TabLayout mTabLayout;
    @InjectView(R.id.viewPager)
    ViewPager mViewPager;
    MessageAdapter mAdapter;


    @Override
    public int getContentViewId() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

        mToolbar.getMenu().clear();
        mToolbar.inflateMenu(R.menu.menu_friend);
        mToolbar.setOnMenuItemClickListener(this);

//        textViewsArr = new TextView[]{messageTv, communityTv};
//        selectTab(textViewsArr[0].getId());//消息
        mAdapter = new MessageAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mTabLayout.addOnTabSelectedListener(this);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
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

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();
        mViewPager.setCurrentItem(position);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_friend:
                if (!CommonUtils.isLogin(context)) {
                    break;
                }
                IntentUtil.startActivity(context, FriendsActivity.class);
                break;
        }
        return true;
    }



    @OnClick(R.id.notify_tv)
    public void onViewClicked() {
        if (!CommonUtils.isLogin(context)) {
            return;
        }
        IntentUtil.startActivity(context, NewFriendsMsgActivity.class);
    }

//    // 选中改变tab字体、背景颜色
//    protected void selectTab(int id) {
//        for (TextView tv : textViewsArr) {
//            if (tv.getId() == id) {
//                tv.setSelected(true);
//            } else {
//                tv.setSelected(false);
//            }
//        }
//    }


//    @OnClick({R.id.message_tv, R.id.community_tv, R.id.friends_tv,R.id.notify_tv})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.message_tv://消息
//                mViewPager.setCurrentItem(0);
//                break;
//            case R.id.community_tv://社区
//                mViewPager.setCurrentItem(1);
//                break;
//            case R.id.notify_tv://通知
//                if (!CommonUtils.isLogin(context)) {
//                    return;
//                }
//                IntentUtil.startActivity(context, NewFriendsMsgActivity.class);
//                break;
//            case R.id.friends_tv://好友
//                if (!CommonUtils.isLogin(context)) {
//                    return;
//                }
//                IntentUtil.startActivity(context, FriendsActivity.class);
//                break;
//        }
//    }
}
