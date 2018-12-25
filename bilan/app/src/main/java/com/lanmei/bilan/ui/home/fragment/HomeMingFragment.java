package com.lanmei.bilan.ui.home.fragment;

import android.os.Bundle;

import com.lanmei.bilan.R;
import com.lanmei.bilan.adapter.HomeMingAdapter;
import com.lanmei.bilan.api.HomeMingApi;
import com.lanmei.bilan.bean.HomeMingBean;
import com.lanmei.bilan.event.AttentionFriendEvent;
import com.xson.common.app.BaseFragment;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.SwipeRefreshController;
import com.xson.common.utils.StringUtils;
import com.xson.common.widget.DividerItemDecoration;
import com.xson.common.widget.SmartSwipeRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.InjectView;

/**
 * Created by xkai on 2018/1/3.
 * 名人堂
 */

public class HomeMingFragment extends BaseFragment {

    @InjectView(R.id.pull_refresh_rv)
    SmartSwipeRefreshLayout ming;
    HomeMingAdapter mAdapter;
    private SwipeRefreshController<NoPageListBean<HomeMingBean>> controller;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_single_listview;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        initSwipeRefreshLayout();
    }


    private void initSwipeRefreshLayout() {
        ming.initWithLinearLayout();
        ming.getRecyclerView().addItemDecoration(new DividerItemDecoration(context));

        HomeMingApi api = new HomeMingApi();
        api.uid = api.getUserId(context);
        api.user_type = "1";//0|1|2 => 普通会员|名人堂|公众号
        mAdapter = new HomeMingAdapter(context);
        ming.setAdapter(mAdapter);
        controller = new SwipeRefreshController<NoPageListBean<HomeMingBean>>(context, ming, api, mAdapter) {
        };
        controller.loadFirstPage();
//        mAdapter.notifyDataSetChanged();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }


    //关注或取消关注该好友时调用
    @Subscribe
    public void attentionFriendEvent(AttentionFriendEvent event) {
        if (mAdapter != null) {
            List<HomeMingBean> list = mAdapter.getData();
            if (StringUtils.isEmpty(list)) {
                return;
            }
            String uid = event.getUid();
            String followed = event.getFollowed();
            int size = list.size();
            for (int i = 0; i < size; i++) {
                HomeMingBean bean = list.get(i);
                if (StringUtils.isSame(uid, bean.getId())) {
                    bean.setJudge(Integer.parseInt(followed));
                }
            }
            mAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
