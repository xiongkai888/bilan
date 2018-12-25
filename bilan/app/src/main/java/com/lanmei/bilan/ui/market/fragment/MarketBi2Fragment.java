package com.lanmei.bilan.ui.market.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.lanmei.bilan.R;
import com.lanmei.bilan.adapter.MarketBi2Adapter;
import com.lanmei.bilan.api.MarketBiCategory2Api;
import com.lanmei.bilan.bean.MarketBi2Bean;
import com.xson.common.app.BaseFragment;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.helper.SwipeRefreshController;
import com.xson.common.utils.StringUtils;
import com.xson.common.widget.CenterTitleToolbar;
import com.xson.common.widget.DividerItemDecoration;
import com.xson.common.widget.SmartSwipeRefreshLayout;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xkai on 2018/1/3.
 * 行情——币种信息
 */

public class MarketBi2Fragment extends BaseFragment {

    @InjectView(R.id.toolbar)
    CenterTitleToolbar mToolbar;

    @InjectView(R.id.deal_price_iv)
    ImageView dealPriceIv;
    @InjectView(R.id.deal_num_iv)
    ImageView dealNumIv;
    @InjectView(R.id.deal_e_iv)
    ImageView dealEIv;
    @InjectView(R.id.rise_iv)
    ImageView riseIv;
    private int TIME = 30000;
    private int sort = 0;//排序

    @InjectView(R.id.pull_refresh_rv)
    SmartSwipeRefreshLayout smartSwipeRefreshLayout;
    MarketBi2Adapter mAdapter;
    private SwipeRefreshController<NoPageListBean<MarketBi2Bean>> controller;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_market_bi;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        mToolbar.setTitle("行情");
        initSwipeRefreshLayout();
    }

    MarketBiCategory2Api api;

    private void initSwipeRefreshLayout() {
        smartSwipeRefreshLayout.initWithLinearLayout();
        smartSwipeRefreshLayout.getRecyclerView().addItemDecoration(new DividerItemDecoration(context));

        api = new MarketBiCategory2Api();
        mAdapter = new MarketBi2Adapter(context);
        smartSwipeRefreshLayout.setAdapter(mAdapter);
        controller = new SwipeRefreshController<NoPageListBean<MarketBi2Bean>>(context, smartSwipeRefreshLayout, api, mAdapter) {
            @Override
            public boolean onSuccessResponse(NoPageListBean<MarketBi2Bean> response) {
                List<MarketBi2Bean> list = response.data;
                mAdapter.setData(list);
                mAdapter.notifyDataSetChanged();
                if (sort != 0) {
                    sort();
                }
                return true;
            }
        };
        controller.loadFirstPage();
        handler.postDelayed(runnable, TIME); //每隔n秒执行

        httpClient = HttpClient.newInstance(context);
    }


    HttpClient httpClient;

    private void load() {
        if (httpClient == null) {
            return;
        }
        httpClient.request(api, new BeanRequest.SuccessListener<NoPageListBean<MarketBi2Bean>>() {
            @Override
            public void onResponse(NoPageListBean<MarketBi2Bean> response) {
                mAdapter.setData(response.data);
                mAdapter.notifyDataSetChanged();
                if (sort != 0) {
                    sort();
                }
            }
        });
    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {

        @Override
        public void run() {
            // handler自带方法实现定时器
            try {
                handler.postDelayed(this, TIME);
                load();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    @OnClick({R.id.deal_price_tv, R.id.deal_num_tv, R.id.deal_e_tv, R.id.rise_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.deal_price_tv:
                setSort(1, dealPriceIv);
                break;
            case R.id.deal_num_tv:
                setSort(3, dealNumIv);
                break;
            case R.id.deal_e_tv:
                setSort(5, dealEIv);
                break;
            case R.id.rise_tv:
                setSort(7, riseIv);
                break;
            default:
//                    sort();//为什么没效果？？
                break;
        }
    }


    private void setSort(int sortNum, ImageView iv) {
        if (sort != sortNum) {
            sort = sortNum;
            clearImg(iv);
            sort();
        } else {
            if (sort == sortNum) {
                sort = (sortNum + 1);
            }
            clearImg(iv);
            sort();
        }
    }

    private void clearImg(ImageView iv) {
        int id = R.mipmap.sort_default;
        dealPriceIv.setImageResource(id);
        dealNumIv.setImageResource(id);
        dealEIv.setImageResource(id);
        riseIv.setImageResource(id);
        if (sort == 1 || sort == 3 || sort == 5 || sort == 7) {
            iv.setImageResource(R.mipmap.sort_down);
        } else {
            iv.setImageResource(R.mipmap.sort_up);
        }
    }

    private void sort() {
        List<MarketBi2Bean> list = mAdapter.getData();
        if (StringUtils.isEmpty(list)) {
            return;
        }
        Collections.sort(list, new Comparator<MarketBi2Bean>() {

            public int compare(MarketBi2Bean o1, MarketBi2Bean o2) {
                        /*
                     * int compare() 返回一个基本类型的整型，
                     * 返回负数表示：o1 小于o2，
                     * 返回0 表示：o1和o2相等，
                     * 返回正数表示：o1大于o2。
                     */

                switch (sort) {
                    case 1://成交价降序
                        return getSort(o2.getPrice(), o1.getPrice());
                    case 2://成交价升序
                        return getSort(o1.getPrice(), o2.getPrice());
                    case 3://成交量降序
                        return getSort(o2.getTotal_supply(), o1.getTotal_supply());
                    case 4://成交量升序
                        return getSort(o1.getTotal_supply(), o2.getTotal_supply());
                    case 5://成交额降序
                        return getSort(o2.getMarket_cap(), o1.getMarket_cap());
                    case 6://成交额升序
                        return getSort(o1.getMarket_cap(), o2.getMarket_cap());
                    case 7://涨跌幅降序
                        return getSort(o2.getPercent_change_24h(), o1.getPercent_change_24h());
                    case 8://涨跌幅升序
                        return getSort(o1.getPercent_change_24h(), o2.getPercent_change_24h());
                }
                return getSort(o1.getPrice(), o2.getPrice());
            }
        });
        mAdapter.notifyDataSetChanged();
    }

    private int getSort(String o1, String o2) {
        return Double.compare(Double.valueOf(o1), Double.valueOf(o2));
    }

}
