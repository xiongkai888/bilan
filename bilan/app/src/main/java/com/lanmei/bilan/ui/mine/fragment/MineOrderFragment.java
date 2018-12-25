package com.lanmei.bilan.ui.mine.fragment;

import android.os.Bundle;

import com.lanmei.bilan.R;
import com.lanmei.bilan.adapter.MineOrderSubAdapter;
import com.lanmei.bilan.api.MineOrderApi;
import com.lanmei.bilan.api.OrderAlterStatusApi;
import com.lanmei.bilan.bean.MineOrderBean;
import com.lanmei.bilan.event.OrderDetailsEvent;
import com.xson.common.app.BaseFragment;
import com.xson.common.bean.BaseBean;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.helper.SwipeRefreshController;
import com.xson.common.utils.StringUtils;
import com.xson.common.utils.UIHelper;
import com.xson.common.widget.SmartSwipeRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.InjectView;

/**
 * Created by xkai on 2018/1/15.
 * 我的订单  全部
 */

public class MineOrderFragment extends BaseFragment {

    @InjectView(R.id.pull_refresh_rv)
    SmartSwipeRefreshLayout smartSwipeRefreshLayout;
    MineOrderSubAdapter mAdapter;
    private SwipeRefreshController<NoPageListBean<MineOrderBean>> controller;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_single_listview;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        initSwipeRefreshLayout();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }


    private void initSwipeRefreshLayout() {
        smartSwipeRefreshLayout.initWithLinearLayout();

        Bundle bundle = getArguments();
        int status = bundle.getInt("status");
        MineOrderApi api = new MineOrderApi();
        api.uid = api.getUserId(context);
        api.status = status;
        mAdapter = new MineOrderSubAdapter(context);
        smartSwipeRefreshLayout.setAdapter(mAdapter);
        controller = new SwipeRefreshController<NoPageListBean<MineOrderBean>>(context, smartSwipeRefreshLayout, api, mAdapter) {
        };
        controller.loadFirstPage();
        mAdapter.setOrderAlterListener(new MineOrderSubAdapter.OrderAlterListener() {
            @Override
            public void affirm(String status, String oid) {
                orderAffirm(status,oid);
            }
        });
    }


    private void orderAffirm(String status, String oid){
        OrderAlterStatusApi api = new OrderAlterStatusApi();
        api.oid = oid;
        api.uid = api.getUserId(context);
        if (StringUtils.isSame(status,"4")){
            api.if_del = "1";
        }else {
            api.status = status;
        }
        HttpClient.newInstance(context).loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                int code = response.getCode();
                switch (code){//1|10|100|404 成功|缺失参数|更改错误|未找到订单
                    case 1:
                        if (controller != null){
                            controller.loadFirstPage();
                        }
                        UIHelper.ToastMessage(context,"操作成功");
                        break;
                    case 10:
                        UIHelper.ToastMessage(context,"缺失参数");
                        break;
                    case 100:
                        UIHelper.ToastMessage(context,"更改错误");
                        break;
                    case 404:
                        UIHelper.ToastMessage(context,"未找到订单");
                        break;
                }
            }
        });
    }

    //订单详情的所有操作完成后调用
    @Subscribe
    public void orderDetailsEvent(OrderDetailsEvent event){
        if (controller != null){
            controller.loadFirstPage();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
