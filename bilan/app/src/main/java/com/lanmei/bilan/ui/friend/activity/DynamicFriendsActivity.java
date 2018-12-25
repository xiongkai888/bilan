package com.lanmei.bilan.ui.friend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.TextView;

import com.hyphenate.chatuidemo.DemoHelper;
import com.lanmei.bilan.BtcimApp;
import com.lanmei.bilan.R;
import com.lanmei.bilan.adapter.DynamicFriendsAdapter;
import com.lanmei.bilan.bean.UserBean;
import com.xson.common.app.BaseActivity;
import com.xson.common.helper.ImageHelper;
import com.xson.common.utils.IntentUtil;
import com.xson.common.utils.StringUtils;
import com.xson.common.widget.CenterTitleToolbar;
import com.xson.common.widget.CircleImageView;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 点击区块款列表的头像进入
 */
public class DynamicFriendsActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    CenterTitleToolbar mToolbar;
    @InjectView(R.id.tabLayout)
    TabLayout mTabLayout;
    @InjectView(R.id.viewPager)
    ViewPager mViewPager;

    DynamicFriendsAdapter mAdapter;
    @InjectView(R.id.head_iv)
    CircleImageView headIv;
    @InjectView(R.id.name_tv)
    TextView nameTv;
    @InjectView(R.id.grade_tv)
    TextView gradeTv;
    @InjectView(R.id.signature_tv)
    TextView signatureTv;
    @InjectView(R.id.follow_num_tv)
    TextView followNumTv;
    @InjectView(R.id.fans_num_tv)
    TextView fansNumTv;
    @InjectView(R.id.friend_num_tv)
    TextView friendNumTv;
    String uid;

    @Override
    public int getContentViewId() {
        return R.layout.activity_dynamic_friends;
    }

    @Override
    public void initIntent(Intent intent) {
        super.initIntent(intent);
        Bundle bundle = intent.getBundleExtra("bundle");
        uid = bundle.getString("uid");
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        setSupportActionBar(mToolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle("");
        actionbar.setHomeAsUpIndicator(R.mipmap.back_w);

        if (StringUtils.isEmpty(uid)) {
            return;
        }

        DemoHelper.getInstance().getUserBean(BtcimApp.HX_USER_Head + uid, true, new DemoHelper.UserInfoListener() {
            @Override
            public void succeed(UserBean bean) {
                if (isFinishing()) {
                    return;
                }
                setData(bean);
            }
        });
    }

    private void setData(final UserBean bean) {
        if (StringUtils.isEmpty(bean)){
            return;
        }
        ImageHelper.load(this,bean.getPic(),headIv,null,true,R.mipmap.default_pic,R.mipmap.default_pic);
        nameTv.setText(bean.getNickname());
        gradeTv.setText(bean.getLevname());
        signatureTv.setText(bean.getSignature());
        followNumTv.setText(bean.getFollow());
        fansNumTv.setText(bean.getFans());
        friendNumTv.setText(bean.getFriend());

        mAdapter = new DynamicFriendsAdapter(getSupportFragmentManager(),bean.getId());
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        headIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("bean",bean);
                IntentUtil.startActivity(DynamicFriendsActivity.this,MingDataActivity.class,bundle);
            }
        });
    }

    @OnClick({R.id.ll_attention, R.id.ll_fans, R.id.ll_good_friends})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_attention:
//                UIHelper.ToastMessage(this,R.string.developing);
                break;
            case R.id.ll_fans:
//                UIHelper.ToastMessage(this,R.string.developing);
                break;
            case R.id.ll_good_friends:
//                UIHelper.ToastMessage(this,R.string.developing);
                break;
        }
    }
}
