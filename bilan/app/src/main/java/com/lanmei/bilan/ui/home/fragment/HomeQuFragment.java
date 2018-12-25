package com.lanmei.bilan.ui.home.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.lanmei.bilan.MainActivity;
import com.lanmei.bilan.R;
import com.lanmei.bilan.adapter.HomeQuAdapter;
import com.lanmei.bilan.api.AdPicApi;
import com.lanmei.bilan.api.HomeQuApi;
import com.lanmei.bilan.bean.AdBean;
import com.lanmei.bilan.bean.HomeQuBean;
import com.lanmei.bilan.event.AddDynamicEvent;
import com.lanmei.bilan.event.AttentionFriendEvent;
import com.lanmei.bilan.event.HomeQuLikeEvent;
import com.lanmei.bilan.helper.ShareHelper;
import com.lanmei.bilan.helper.ShareListener;
import com.lanmei.bilan.utils.CommonUtils;
import com.lanmei.bilan.widget.ShareNewsView;
import com.xson.common.app.BaseFragment;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.helper.SwipeRefreshController;
import com.xson.common.utils.StringUtils;
import com.xson.common.widget.SmartSwipeRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.InjectView;

/**
 * Created by xkai on 2018/1/3.
 * 区块圈(即动态)
 */

public class HomeQuFragment extends BaseFragment {

    @InjectView(R.id.pull_refresh_rv)
    SmartSwipeRefreshLayout qu;
    HomeQuAdapter mAdapter;
    private SwipeRefreshController<NoPageListBean<HomeQuBean>> controller;
    private ShareHelper mShareHelper;

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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mShareHelper = ((MainActivity)context).getShareHelper();
    }

    private void initSwipeRefreshLayout() {
        qu.initWithLinearLayout();

        HomeQuApi api = new HomeQuApi();
        api.token = api.getUserId(context);
        //        api.uid = api.getUserId(context);
        mAdapter = new HomeQuAdapter(context, 1);
        qu.setAdapter(mAdapter);
        controller = new SwipeRefreshController<NoPageListBean<HomeQuBean>>(context, qu, api, mAdapter) {
        };
        controller.loadFirstPage();
        mAdapter.setShareListener(new ShareListener() {
            @Override
            public void share(String content,String time,List<String> list,String type) {
                ShareNewsView view = (ShareNewsView) LayoutInflater.from(getContext()).inflate(R.layout.view_capture, null);
                view.share(content,time,list,type);
                mShareHelper.share(view);
            }
        });
        loadAd();
    }

    //加载轮播图
    private void loadAd() {
        AdPicApi api = new AdPicApi();
        api.classid = CommonUtils.isOne;
        HttpClient.newInstance(context).request(api, new BeanRequest.SuccessListener<NoPageListBean<AdBean>>() {
            @Override
            public void onResponse(NoPageListBean<AdBean> response) {
                if (StringUtils.isEmpty(response.data)) {
                    return;
                }
                mAdapter.setParamenter(response.data);
            }
        });
    }


    //评论详情时或点赞时调用
    @Subscribe
    public void commEvent(HomeQuLikeEvent event) {
        if (mAdapter != null) {
            List<HomeQuBean> list = mAdapter.getData();
            if (StringUtils.isEmpty(list)) {
                return;
            }
            String commNum = event.getCommNum();
            String like = event.getLike();
            String liked = event.getLiked();
            String id = event.getId();
            int size = list.size();
            for (int i = 0; i < size; i++) {
                HomeQuBean bean = list.get(i);
                if (StringUtils.isSame(id, bean.getId())) {
                    bean.setLike(like);
                    bean.setLiked(liked);
                    bean.setReviews(commNum);
                    mAdapter.notifyDataSetChanged();
                    return;
                }
            }
        }
    }

    //在好友动态详情中关注或取消关注该好友时调用
    @Subscribe
    public void attentionFriendEvent(AttentionFriendEvent event) {
        if (mAdapter != null) {
            List<HomeQuBean> list = mAdapter.getData();
            if (StringUtils.isEmpty(list)) {
                return;
            }
            String uid = event.getUid();
            String followed = event.getFollowed();
            int size = list.size();
            for (int i = 0; i < size; i++) {
                HomeQuBean bean = list.get(i);
                if (StringUtils.isSame(uid, bean.getUid())) {
                    bean.setFollowed(followed);
                }
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    //发布动态或删除动态时调用
    @Subscribe
    public void addDynamicEvent(AddDynamicEvent event) {
        if (controller != null) {
            controller.loadFirstPage();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


}
