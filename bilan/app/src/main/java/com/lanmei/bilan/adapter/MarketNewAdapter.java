package com.lanmei.bilan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lanmei.bilan.R;
import com.lanmei.bilan.bean.MarketBean;
import com.xson.common.adapter.SwipeRefreshAdapter;
import com.xson.common.utils.DoubleUtil;
import com.xson.common.utils.StringUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * 行情--币种信息
 */
public class MarketNewAdapter extends SwipeRefreshAdapter<MarketBean> {


    public MarketNewAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_market_bi, parent, false));
    }

    @Override
    public void onBindViewHolder2(RecyclerView.ViewHolder holder, int position) {

        final MarketBean bean = getItem(position);
        if (StringUtils.isEmpty(bean)) {
            return;
        }
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setParameter(bean, position);
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

        public void setParameter(MarketBean bean, int position) {
            nameTv.setText(bean.getSymbol());
            double dealNum = bean.getTotal_supply();
//            double dealNum = DoubleUtil.formatDouble(bean.getTotal_supply());
            if (dealNum >= 10000) {
                dealNumTv.setText(DoubleUtil.formatWan(dealNum + "") + context.getString(R.string.wan));//成交量
            } else {
                dealNumTv.setText(DoubleUtil.formatFloatNumber(dealNum));//成交量
            }
            MarketBean.QuotesBean quotesBean = bean.getQuotes();
            if (StringUtils.isEmpty(quotesBean)) {
                return;
            }
            MarketBean.QuotesBean.USDBean usdBean = quotesBean.getUSD();
            if (StringUtils.isEmpty(usdBean)) {
                return;
            }
            double deal = DoubleUtil.formatDouble(usdBean.getMarket_cap() + "");
            if (deal >= 10000) {
                dealETv.setText(DoubleUtil.formatWan(usdBean.getMarket_cap() + "") + context.getString(R.string.wan));//成交额
            } else {
                dealETv.setText(DoubleUtil.formatFloatNumber(deal));//成交额
            }
            double dealPrice = usdBean.getPrice();
            double range = usdBean.getPercent_change_24h();
            if (range < 0) {
                riseTv.setTextColor(context.getResources().getColor(R.color.colorAccent));
                dealPriceTv.setTextColor(context.getResources().getColor(R.color.colorAccent));
                dealPriceTv.setText("$" + dealPrice + "↓");//成交价
//                if (dealPrice > 1) {
//                    dealPriceTv.setText("$" + DoubleUtil.formatFloatNumber(dealPrice) + "↓");//成交价
//                } else {
//                    dealPriceTv.setText("$" + dealPrice + "↓");//成交价
//                }
            } else {
                riseTv.setTextColor(context.getResources().getColor(R.color.color00D3C4));
                dealPriceTv.setTextColor(context.getResources().getColor(R.color.color00D3C4));
                dealPriceTv.setText("$" + dealPrice + "↑");//成交价
//                if (dealPrice > 1) {
//                    dealPriceTv.setText("$" + DoubleUtil.formatFloatNumber(dealPrice) + "↑");//成交价
//                } else {
//                    dealPriceTv.setText("$" + dealPrice + "↑");//成交价
//                }
            }
            riseTv.setText(range + "%");//涨跌幅
        }
    }
}
