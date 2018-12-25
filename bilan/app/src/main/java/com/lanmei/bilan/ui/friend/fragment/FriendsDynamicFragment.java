package com.lanmei.bilan.ui.friend.fragment;

import android.os.Bundle;

import com.lanmei.bilan.R;
import com.lanmei.bilan.adapter.MineDynamicAdapter;
import com.lanmei.bilan.api.HomeQuApi;
import com.lanmei.bilan.bean.HomeQuBean;
import com.lanmei.bilan.event.HomeQuLikeEvent;
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
 * Created by xkai on 2018/1/15.
 * 好友的动态列表
 */

public class FriendsDynamicFragment extends BaseFragment {

    @InjectView(R.id.pull_refresh_rv)
    SmartSwipeRefreshLayout smartSwipeRefreshLayout;
    MineDynamicAdapter mAdapter;
    private SwipeRefreshController<NoPageListBean<HomeQuBean>> controller;

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

    String uid;

    private void initSwipeRefreshLayout() {
        smartSwipeRefreshLayout.initWithLinearLayout();
        smartSwipeRefreshLayout.getRecyclerView().addItemDecoration(new DividerItemDecoration(context));

        Bundle bundle = getArguments();
        if (bundle != null){
            uid = bundle.getString("uid");
        }
        HomeQuApi api = new HomeQuApi();
        api.uid = uid;
        api.token = api.getUserId(context);
        mAdapter = new MineDynamicAdapter(context,3);
        smartSwipeRefreshLayout.setAdapter(mAdapter);
        controller = new SwipeRefreshController<NoPageListBean<HomeQuBean>>(context, smartSwipeRefreshLayout, api, mAdapter) {
        };
        controller.loadFirstPage();
    }


    //评论详情时或点赞时调用
    @Subscribe
    public void commEvent(HomeQuLikeEvent event){
//        int who = event.getWho();
        if (mAdapter != null){
            List<HomeQuBean> list = mAdapter.getData();
            if (StringUtils.isEmpty(list)){
                return;
            }
            String commNum = event.getCommNum();
            String like = event.getLike();
            String liked = event.getLiked();
            String id = event.getId();
            int size = list.size();
            for (int i = 0;i< size;i++){
                HomeQuBean bean = list.get(i);
                if (StringUtils.isSame(id,bean.getId())){
                    bean.setLike(like);
                    bean.setLiked(liked);
                    bean.setReviews(commNum);
                    mAdapter.notifyDataSetChanged();
                    return;
                }
            }

        }
    }

//    //发布动态或删除动态时调用
//    @Subscribe
//    public void addDynamicEvent(AddDynamicEvent event){
//        if (controller != null){
//            controller.loadFirstPage();
//        }
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
