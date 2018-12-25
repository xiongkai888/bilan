package com.lanmei.bilan.ui.mine.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lanmei.bilan.R;
import com.lanmei.bilan.adapter.RechargeRecordAdapter;
import com.lanmei.bilan.api.RechargeRecordApi;
import com.lanmei.bilan.bean.RechargeRecordBean;
import com.lanmei.bilan.bean.UserBean;
import com.lanmei.bilan.ui.mine.activity.RechargeActivity;
import com.lanmei.bilan.utils.CommonUtils;
import com.xson.common.app.BaseFragment;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.SwipeRefreshController;
import com.xson.common.utils.IntentUtil;
import com.xson.common.utils.StringUtils;
import com.xson.common.widget.DividerItemDecoration;
import com.xson.common.widget.SmartSwipeRefreshLayout;

import butterknife.InjectView;
import butterknife.OnClick;


/**
 * Created by Administrator on 2018/1/24.
 * 消费明细或充值记录
 */

public class RechargeRecordFragment extends BaseFragment {


    @InjectView(R.id.ll_recharge)
    LinearLayout llRecharge;

    @InjectView(R.id.pull_refresh_rv)
    SmartSwipeRefreshLayout smartSwipeRefreshLayout;

    RechargeRecordAdapter mAdapter;
    @InjectView(R.id.money_tv)
    TextView moneyTv;
    private SwipeRefreshController<NoPageListBean<RechargeRecordBean>> controller;//recharge

    @Override
    public int getContentViewId() {
        return R.layout.fragment_recharge_record;
    }

    public static RechargeRecordFragment newInstance() {
        Bundle args = new Bundle();
        RechargeRecordFragment fragment = new RechargeRecordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        initSwipeRefreshLayout();
    }

    private void initSwipeRefreshLayout() {
        smartSwipeRefreshLayout.initWithLinearLayout();
        smartSwipeRefreshLayout.getRecyclerView().addItemDecoration(new DividerItemDecoration(context));

        int type = getArguments().getInt("type");
        if (type == 1) {
            llRecharge.setVisibility(View.GONE);
        }else {
            CommonUtils.loadUserInfo(context, new CommonUtils.LoadUserInfoListener() {
                @Override
                public void succeed(UserBean bean) {
                    if (StringUtils.isEmpty(bean)){
                        return;
                    }
                    moneyTv.setText("当前余额："+bean.getBalance());
                }
            });
        }
        RechargeRecordApi api = new RechargeRecordApi();
        api.uid = api.getUserId(context);
        api.type = type;
        mAdapter = new RechargeRecordAdapter(context);
        smartSwipeRefreshLayout.setAdapter(mAdapter);
        controller = new SwipeRefreshController<NoPageListBean<RechargeRecordBean>>(context, smartSwipeRefreshLayout, api, mAdapter) {
        };
        controller.loadFirstPage();
    }

    @OnClick(R.id.recharge_tv)
    public void onViewClicked() {
        IntentUtil.startActivity(context, RechargeActivity.class);
    }
}
