package com.lanmei.bilan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lanmei.bilan.R;
import com.lanmei.bilan.bean.MarketBi2Bean;
import com.lanmei.bilan.utils.CommonUtils;
import com.xson.common.adapter.SwipeRefreshAdapter;
import com.xson.common.utils.DoubleUtil;
import com.xson.common.utils.StringUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * 行情--币种信息
 */
public class MarketBi2Adapter extends SwipeRefreshAdapter<MarketBi2Bean> {


    public MarketBi2Adapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_market_bi, parent, false));
    }

    @Override
    public void onBindViewHolder2(RecyclerView.ViewHolder holder, int position) {
        MarketBi2Bean bean = getItem(position);
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

        public void setParameter(final MarketBi2Bean bean) {

            nameTv.setText(bean.getSymbol());
            double dealNum = DoubleUtil.formatDouble(bean.getTotal_supply());
            dealNumTv.setText((dealNum >= 10000) ? DoubleUtil.formatWan(bean.getTotal_supply()) + context.getString(R.string.wan) : DoubleUtil.formatFloatNumber(dealNum));//成交量
            double deal = DoubleUtil.formatDouble(bean.getMarket_cap());
            dealETv.setText((deal >= 10000) ? DoubleUtil.formatWan(bean.getMarket_cap()) + context.getString(R.string.wan) : DoubleUtil.formatFloatNumber(deal));//成交额
            double dealPrice = DoubleUtil.formatDouble(bean.getPrice());
            double range = Double.parseDouble(StringUtils.isEmpty(bean.getPercent_change_24h()) ? CommonUtils.isZero : bean.getPercent_change_24h());
            riseTv.setTextColor((range < 0) ? context.getResources().getColor(R.color.colorAccent) : context.getResources().getColor(R.color.color00D3C4));
            dealPriceTv.setTextColor((range < 0) ? context.getResources().getColor(R.color.colorAccent) : context.getResources().getColor(R.color.color00D3C4));
            dealPriceTv.setText(getDealPriceStr(range, dealPrice, bean));
            riseTv.setText(range + context.getString(R.string.range));//涨跌幅
        }
    }

    public String getDealPriceStr(double range, double dealPrice, MarketBi2Bean bean) {
        int id = (range < 0) ? R.string.num_down : R.string.num_up;
        return (dealPrice > 1) ? String.format(context.getString(id), "" + DoubleUtil.formatFloatNumber(bean.getPrice())) : String.format(context.getString(id), "" + dealPrice);
    }

}
