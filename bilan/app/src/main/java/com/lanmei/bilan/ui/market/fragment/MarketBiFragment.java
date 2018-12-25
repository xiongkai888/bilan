package com.lanmei.bilan.ui.market.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.lanmei.bilan.R;
import com.lanmei.bilan.adapter.MarketBiAdapter;
import com.lanmei.bilan.api.MarketBiCategoryApi;
import com.lanmei.bilan.bean.MarketBiBean;
import com.lanmei.bilan.event.SortEvent;
import com.xson.common.app.BaseFragment;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.SwipeRefreshController;
import com.xson.common.utils.DoubleUtil;
import com.xson.common.utils.L;
import com.xson.common.utils.StringUtils;
import com.xson.common.widget.DividerItemDecoration;
import com.xson.common.widget.SmartSwipeRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xkai on 2018/1/3.
 * 行情——币种信息
 */

public class MarketBiFragment extends BaseFragment {

    @InjectView(R.id.deal_price_iv)
    ImageView dealPriceIv;
    @InjectView(R.id.deal_num_iv)
    ImageView dealNumIv;
    @InjectView(R.id.deal_e_iv)
    ImageView dealEIv;
    @InjectView(R.id.rise_iv)
    ImageView riseIv;
    private int TIME = 5000;
    private int position = 0;
    private int sort = 0;//排序

    @InjectView(R.id.pull_refresh_rv)
    SmartSwipeRefreshLayout smartSwipeRefreshLayout;
    MarketBiAdapter mAdapter;
    private SwipeRefreshController<NoPageListBean<MarketBiBean>> controller;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_market_bi;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        initSwipeRefreshLayout();
    }


    private void initSwipeRefreshLayout() {
        smartSwipeRefreshLayout.initWithLinearLayout();
        smartSwipeRefreshLayout.getRecyclerView().addItemDecoration(new DividerItemDecoration(context));

        MarketBiCategoryApi api = new MarketBiCategoryApi();
        api.state2 = "1";
        mAdapter = new MarketBiAdapter(context);
        smartSwipeRefreshLayout.setAdapter(mAdapter);
        controller = new SwipeRefreshController<NoPageListBean<MarketBiBean>>(context, smartSwipeRefreshLayout, api, mAdapter) {
            @Override
            public boolean onSuccessResponse(NoPageListBean<MarketBiBean> response) {
                controller.setHasMore(false);
                List<MarketBiBean> list = response.data;
                if (!StringUtils.isEmpty(list)) {
                    int size = list.size();
                    for (int i = 0; i < size; i++) {
                        MarketBiBean bean = list.get(i);
                        bean.setRefresh(true);
                    }
                }
                mAdapter.setData(list);
                mAdapter.setSort(sort,false);
                mAdapter.notifyDataSetChanged();
                if (sort != 0) {
                    sort();
                }
                return true;
            }
        };
        controller.loadFirstPage();
        handler.postDelayed(runnable, TIME); //每隔1s执行

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {

        @Override
        public void run() {
            // handler自带方法实现定时器
            try {
                handler.postDelayed(this, TIME);
                List<MarketBiBean> list = mAdapter.getData();
                if (!StringUtils.isEmpty(list)) {
                    int size = list.size();
                    for (int i = 0; i < size; i++) {
                        MarketBiBean bean = list.get(i);
                        bean.setRefresh(false);
                    }
                    if (position >= size) {
                        position = 0;
                        return;
                    }
                    MarketBiBean marketBiBean = list.get(position);
                    marketBiBean.setRefresh(true);
                    position += 1;
                    mAdapter.setSort(sort,true);
                    mAdapter.notifyDataSetChanged();
//                    if (sort != 0) {
//                        sort();
//                    }
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    };

    int sl = 0;

    @Subscribe
    public void sortEvent(SortEvent event){
        if (sort != 0) {
            sort();
        }
        L.d("clearImg", "sortEvent="+(sl++));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.deal_price_tv, R.id.deal_num_tv, R.id.deal_e_tv, R.id.rise_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.deal_price_tv:
                if (sort != 1) {
                    sort = 1;
                    clearImg(dealPriceIv);
                    sort();
                    break;
                }
                if (sort == 1) {
                    sort = 2;
                }
                clearImg(dealPriceIv);
                sort();
                break;
            case R.id.deal_num_tv:
                if (sort != 3) {
                    sort = 3;
                    clearImg(dealNumIv);
                    sort();
                    break;
                }
                if (sort == 3) {
                    sort = 4;
                }
                clearImg(dealNumIv);
                sort();
                break;
            case R.id.deal_e_tv:
                if (sort != 5) {
                    sort = 5;
                    clearImg(dealEIv);
                    sort();
                    break;
                }
                if (sort == 5) {
                    sort = 6;
                }
                clearImg(dealEIv);
                sort();
                break;
            case R.id.rise_tv:
                if (sort != 7) {
                    sort = 7;
                    clearImg(riseIv);
                    sort();
                    break;
                }
                if (sort == 7) {
                    sort = 8;
                }
                clearImg(riseIv);
                sort();
                break;
            default:
//                    sort();//为什么没效果？？
                break;

        }
    }

    private void clearImg(ImageView iv) {
        dealPriceIv.setImageResource(R.mipmap.sort_default);
        dealNumIv.setImageResource(R.mipmap.sort_default);
        dealEIv.setImageResource(R.mipmap.sort_default);
        riseIv.setImageResource(R.mipmap.sort_default);

        switch (sort) {
            case 1:
            case 3:
            case 5:
            case 7:
                iv.setImageResource(R.mipmap.sort_down);
                break;
            case 2:
            case 4:
            case 6:
            case 8:
                iv.setImageResource(R.mipmap.sort_up);
                break;
        }

    }

    private void sort() {
        List<MarketBiBean> list = mAdapter.getData();
        if (StringUtils.isEmpty(list)) {
            return;
        }
        Collections.sort(list, new Comparator<MarketBiBean>() {

            public int compare(MarketBiBean o1, MarketBiBean o2) {
                        /*
                     * int compare() 返回一个基本类型的整型，
                     * 返回负数表示：o1 小于o2，
                     * 返回0 表示：o1和o2相等，
                     * 返回正数表示：o1大于o2。
                     */

                switch (sort) {
                    case 1://成交价降序
                        return (int) (DoubleUtil.formatFloatNumber(o2.getNewclinchprice()) - DoubleUtil.formatFloatNumber(o1.getNewclinchprice()));
                    case 2://成交价升序
                        return (int) (DoubleUtil.formatFloatNumber(o1.getNewclinchprice()) - DoubleUtil.formatFloatNumber(o2.getNewclinchprice()));
                    case 3://成交量降序
                        return (int) (DoubleUtil.formatFloatNumber(o2.getCount24()) - DoubleUtil.formatFloatNumber(o1.getCount24()));
                    case 4://成交量升序
                        return (int) (DoubleUtil.formatFloatNumber(o1.getCount24()) - DoubleUtil.formatFloatNumber(o2.getCount24()));
                    case 5://成交额降序
                        return (int) (DoubleUtil.formatFloatNumber(o2.getMoney24()) - DoubleUtil.formatFloatNumber(o1.getMoney24()));
                    case 6://成交额升序
                        return (int) (DoubleUtil.formatFloatNumber(o1.getMoney24()) - DoubleUtil.formatFloatNumber(o2.getMoney24()));
                    case 7://涨跌幅降序
                        return (int) (DoubleUtil.formatFloatNumber(o2.getRange24()) - DoubleUtil.formatFloatNumber(o1.getRange24()));
                    case 8://涨跌幅升序
                        return (int) (DoubleUtil.formatFloatNumber(o1.getRange24()) - DoubleUtil.formatFloatNumber(o2.getRange24()));
                }
                return (int) (DoubleUtil.formatFloatNumber(o1.getNewclinchprice()) - DoubleUtil.formatFloatNumber(o2.getNewclinchprice()));
            }
        });
        mAdapter.notifyDataSetChanged();
    }

}
