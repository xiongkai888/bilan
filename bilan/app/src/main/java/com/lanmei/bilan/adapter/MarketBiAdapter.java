package com.lanmei.bilan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.data.volley.Response;
import com.data.volley.error.VolleyError;
import com.lanmei.bilan.R;
import com.lanmei.bilan.api.MarketBiApi;
import com.lanmei.bilan.bean.MarketBiBean;
import com.lanmei.bilan.event.SortEvent;
import com.xson.common.adapter.SwipeRefreshAdapter;
import com.xson.common.bean.DataBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.utils.DoubleUtil;
import com.xson.common.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * 行情--币种信息
 */
public class MarketBiAdapter extends SwipeRefreshAdapter<MarketBiBean> {

    public Map<String, MarketBiBean> biBeanMap = new HashMap<>();//
    int sort;
    boolean isSort;

    public MarketBiAdapter(Context context) {
        super(context);
    }

    public void setSort(int sort, boolean isSort) {
        this.sort = sort;
        this.isSort = isSort;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_market_bi, parent, false));
    }

    @Override
    public void onBindViewHolder2(RecyclerView.ViewHolder holder, int position) {
        MarketBiBean bean = getItem(position);
        if (bean == null) {
            return;
        }
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setParameter(bean);
        if (position % 2 == 1) {
            viewHolder.itemView.setBackgroundColor(context.getResources().getColor(R.color.white));
        } else {
            viewHolder.itemView.setBackgroundColor(context.getResources().getColor(R.color.colorEEE));
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                UIHelper.ToastMessage(context,R.string.developing);
//                NewsDetailsActivity.startActivityNewsDetails(context, bean);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.name_tv)
        TextView nameTv;
        @InjectView(R.id.deal_price_tv)
        TextView dealPriceTv;
        @InjectView(R.id.deal_num_tv)
        TextView dealNumTv;
        @InjectView(R.id.deal_e_tv)
        TextView dealETv;
        @InjectView(R.id.rise_tv)
        TextView riseTv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);

        }

        public void setParameter(final MarketBiBean bean) {
            if (biBeanMap.containsKey(bean.getClassname())) {
                MarketBiBean biBean = biBeanMap.get(bean.getClassname());
                setBiBean(biBean);
            }
            if (bean.isRefresh()) {
                loadBi(bean.getClassname(), bean);
            }
        }


        private void loadBi(final String name, final MarketBiBean bean) {
            MarketBiApi api = new MarketBiApi();
            api.payCoinName = "USDT";
            api.coinName = name;
//            api.setAPI_URL(context.getString(R.string.api_url));
            HttpClient.newInstance(context).request(api, new BeanRequest.SuccessListener<DataBean<MarketBiBean>>() {
                @Override
                public void onResponse(DataBean<MarketBiBean> response) {
                    if (nameTv == null) {
                        return;
                    }
                    MarketBiBean biBean = response.model;
                    if (biBean == null) {
                        return;
                    }
                    setArgument(bean, biBean);
                    biBeanMap.put(name, bean);
//                    DoubleUtil.formatFloatNumber(biBean.getNewclinchprice())
                    setBiBean(biBean);
                    if (sort != 0 && isSort) {
                        isSort = !isSort;
                        EventBus.getDefault().post(new SortEvent());
                    }

                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }

        private void setArgument(MarketBiBean bean, MarketBiBean biBean) {
            bean.setLogo(biBean.getLogo());
            bean.setDefaultenname(biBean.getDefaultenname());
            bean.setDefaultcnname(biBean.getDefaultcnname());
            bean.setNewclinchprice(biBean.getNewclinchprice());
            bean.setNewclinchtype(biBean.getNewclinchtype());
            bean.setBuyprice(biBean.getBuyprice());
            bean.setSellprice(biBean.getSellprice());
            bean.setCount24(biBean.getCount24());
            bean.setMoney24(biBean.getMoney24());
            bean.setRange24(biBean.getRange24());
            bean.setHighprice(biBean.getHighprice());
            bean.setLowprice(biBean.getLowprice());
        }

        public void setBiBean(MarketBiBean biBean) {

            nameTv.setText(biBean.getDefaultenname());
            double dealNum = DoubleUtil.formatDouble(biBean.getCount24());
            if (dealNum >= 10000){
                dealNumTv.setText(DoubleUtil.formatWan(biBean.getCount24()) + context.getString(R.string.wan));//成交量
            }else {
                dealNumTv.setText(DoubleUtil.formatFloatNumber(dealNum));//成交量
            }
            double deal = DoubleUtil.formatDouble(biBean.getMoney24());
            if (deal >= 10000){
                dealETv.setText(DoubleUtil.formatWan(biBean.getMoney24()) + context.getString(R.string.wan));//成交额
            }else {
                dealETv.setText(DoubleUtil.formatFloatNumber(deal));//成交额
            }
            biBean.getNewclinchprice();
            double dealPrice = DoubleUtil.formatDouble(biBean.getNewclinchprice());
            String range = biBean.getRange24();
            if (!StringUtils.isEmpty(range) && range.startsWith("-")) {
                riseTv.setTextColor(context.getResources().getColor(R.color.colorAccent));
                dealPriceTv.setTextColor(context.getResources().getColor(R.color.colorAccent));
                if (dealPrice > 1) {
                    dealPriceTv.setText("$" + DoubleUtil.formatFloatNumber(biBean.getNewclinchprice()) + "↓");//成交价
                } else {
                    dealPriceTv.setText("$" + dealPrice + "↓");//成交价
                }

            } else {
                riseTv.setTextColor(context.getResources().getColor(R.color.color00D3C4));
                dealPriceTv.setTextColor(context.getResources().getColor(R.color.color00D3C4));
                if (dealPrice > 1) {
                    dealPriceTv.setText("$" + DoubleUtil.formatFloatNumber(biBean.getNewclinchprice()) + "↑");//成交价
                } else {

                    dealPriceTv.setText("$" + dealPrice + "↑");//成交价
                }
            }
            riseTv.setText(DoubleUtil.formatFloatNumber(range) + "%");//涨跌幅
        }

    }

}
