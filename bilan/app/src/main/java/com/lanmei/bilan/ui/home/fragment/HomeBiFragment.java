package com.lanmei.bilan.ui.home.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.lanmei.bilan.MainActivity;
import com.lanmei.bilan.R;
import com.lanmei.bilan.adapter.HomeBiAdapter;
import com.lanmei.bilan.api.HomeBiApi;
import com.lanmei.bilan.bean.HomeBiBean;
import com.lanmei.bilan.event.HomeKuaiLikeEvent;
import com.lanmei.bilan.helper.ShareHelper;
import com.lanmei.bilan.helper.ShareListener;
import com.lanmei.bilan.utils.CommonUtils;
import com.lanmei.bilan.utils.FormatTime;
import com.lanmei.bilan.widget.ShareNewsView;
import com.xson.common.app.BaseFragment;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.SwipeRefreshController;
import com.xson.common.utils.L;
import com.xson.common.utils.StringUtils;
import com.xson.common.widget.DividerItemDecoration;
import com.xson.common.widget.SmartSwipeRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.InjectView;

/**
 * Created by xkai on 2018/1/3.
 * 币头条（快讯）
 */

public class HomeBiFragment extends BaseFragment {

    @InjectView(R.id.pull_refresh_rv)
    SmartSwipeRefreshLayout bi;
    @InjectView(R.id.time_tv)
    TextView timeTv;
    HomeBiAdapter mAdapter;
    FormatTime formatTime = new FormatTime();
    private SwipeRefreshController<NoPageListBean<HomeBiBean>> controller;
    private ShareHelper mShareHelper;
//    LinearLayoutManager layoutMgr;
//    int firstPosition;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_home_bi;
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
        bi.initWithLinearLayout();
        bi.getRecyclerView().addItemDecoration(new DividerItemDecoration(context));

        HomeBiApi api = new HomeBiApi();
        api.cid = CommonUtils.isOne;
        api.uid = api.getUserId(context);
        mAdapter = new HomeBiAdapter(context);
        bi.setAdapter(mAdapter);
        controller = new SwipeRefreshController<NoPageListBean<HomeBiBean>>(context, bi, api, mAdapter) {
        };
        controller.loadFirstPage();

        controller.setIsFirstPageListener(new SwipeRefreshController.IsFirstPageListener() {
            @Override
            public void isFirst() {
                List<HomeBiBean> list = mAdapter.getData();
                if (!StringUtils.isEmpty(list)){
                    formatTime.setTime(list.get(0).getAddtime());
                    timeTv.setText(formatTime.getTime());
                }
            }
        });

        mAdapter.setShareListener(new ShareListener() {
            @Override
            public void share(String content, String time, List<String> list,String type) {

                ShareNewsView view = (ShareNewsView) LayoutInflater.from(getContext()).inflate(R.layout.view_capture, null);
                view.share(content,time,list,type);
                mShareHelper.share(view);
            }
        });

        bi.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                setTime(recyclerView);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

    }

    private void setTime(RecyclerView recyclerView){
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        //判断是当前layoutManager是否为LinearLayoutManager
        // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
            //获取最后一个可见view的位置
            int lastItemPosition = linearManager.findLastVisibleItemPosition();
            //获取第一个可见view的位置
            int firstItemPosition = linearManager.findFirstVisibleItemPosition();
            L.d("BaseAppCompatActivity",firstItemPosition+","+lastItemPosition);
            formatTime.setTime(mAdapter.getItem(firstItemPosition).getAddtime());
            timeTv.setText(formatTime.getTime());
        }
    }

    //快讯点赞(或点踩)时调用
    @Subscribe
    public void commEvent(HomeKuaiLikeEvent event) {
        if (mAdapter != null) {
            List<HomeBiBean> list = mAdapter.getData();
            if (StringUtils.isEmpty(list)) {
                return;
            }
            String unLike = event.getUnlike();
            String unLiked = event.getUnliked();
            String like = event.getLike();
            String liked = event.getLiked();
            String id = event.getId();
            int size = list.size();
            for (int i = 0; i < size; i++) {
                HomeBiBean bean = list.get(i);
                if (StringUtils.isSame(id, bean.getId())) {
                    bean.setLike(like);
                    bean.setLiked(liked);
                    bean.setUnlike(unLike);
                    bean.setUnliked(unLiked);
                    L.d("BaseAppCompatActivity",unLike+","+unLiked);
                    mAdapter.notifyDataSetChanged();
                    return;
                }
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
