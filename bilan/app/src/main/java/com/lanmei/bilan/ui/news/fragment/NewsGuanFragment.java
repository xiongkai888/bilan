package com.lanmei.bilan.ui.news.fragment;

import android.os.Bundle;

import com.lanmei.bilan.R;
import com.lanmei.bilan.adapter.NewsGuanAdapter;
import com.lanmei.bilan.api.NewsGuanApi;
import com.lanmei.bilan.bean.NewsJingBean;
import com.lanmei.bilan.event.AttentionEvent;
import com.lanmei.bilan.event.DeleteArticleEvent;
import com.lanmei.bilan.event.HomeBiCommEvent;
import com.lanmei.bilan.utils.CommonUtils;
import com.xson.common.app.BaseFragment;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.SwipeRefreshController;
import com.xson.common.utils.StringUtils;
import com.xson.common.widget.SmartSwipeRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.InjectView;

/**
 * Created by xkai on 2018/1/3.
 * 资讯-关注
 */

public class NewsGuanFragment extends BaseFragment {

    @InjectView(R.id.pull_refresh_rv)
    SmartSwipeRefreshLayout bi;
    NewsGuanAdapter mAdapter;
    private SwipeRefreshController<NoPageListBean<NewsJingBean>> controller;

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
        bi.initWithLinearLayout();

        NewsGuanApi api = new NewsGuanApi();
        api.uid = StringUtils.isEmpty(api.getUserId(context))? CommonUtils.isZero:api.getUserId(context);
        mAdapter = new NewsGuanAdapter(context);
        bi.setAdapter(mAdapter);
        controller = new SwipeRefreshController<NoPageListBean<NewsJingBean>>(context, bi, api, mAdapter) {
        };
        controller.loadFirstPage();
    }

    @Subscribe
    public void commEvent(HomeBiCommEvent event) {
        if (mAdapter != null) {
            String commNum = event.getCommNum();
            String id = event.getId();
            List<NewsJingBean> list = mAdapter.getData();
            int size = list.size();
            for (int i = 0; i < size; i++) {
                NewsJingBean bean = list.get(i);
                if (StringUtils.isSame(bean.getId(),id)){
                    bean.setReviews(commNum);
                    mAdapter.notifyDataSetChanged();
                    return;
                }
            }
        }
    }
    //关注、取消关注事件
    @Subscribe
    public void attentionEvent(AttentionEvent event) {
        controller.loadFirstPage();
    }

    //先写着，到时候再改咯
    @Subscribe
    public void deleteArticleEvent(DeleteArticleEvent event) {
        controller.loadFirstPage();
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
