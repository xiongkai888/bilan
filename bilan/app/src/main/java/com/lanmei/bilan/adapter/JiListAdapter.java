package com.lanmei.bilan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lanmei.bilan.R;
import com.lanmei.bilan.bean.JiListBean;
import com.lanmei.bilan.utils.CommonUtils;
import com.lanmei.bilan.utils.FormatTime;
import com.xson.common.adapter.SwipeRefreshAdapter;
import com.xson.common.utils.StringUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * 积分流水
 */
public class JiListAdapter extends SwipeRefreshAdapter<JiListBean> {

    FormatTime time;

    public JiListAdapter(Context context) {
        super(context);
        time = new FormatTime();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recharge_record, parent, false));
    }

    @Override
    public void onBindViewHolder2(RecyclerView.ViewHolder holder, int position) {

        JiListBean bean = getItem(position);
        if (bean == null) {
            return;
        }
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.contentTv.setText(bean.getContent());
//        String price  = String.format(context.getString(R.string.price_sub),bean.getPrice());
        String price  = bean.getPrice();
        if (StringUtils.isSame(bean.getType(), CommonUtils.isZero)){//充值
            price =  String.format(context.getString(R.string.price_zheng),price);
            viewHolder.priceTv.setTextColor(context.getResources().getColor(R.color.colorAccent));
        }else {
            price = String.format(context.getString(R.string.price_fu),price);
            viewHolder.priceTv.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
        }
        viewHolder.priceTv.setText(price);
        time.setTime(bean.getAddtime());
        viewHolder.timeTv.setText(time.getFormatTime());
        viewHolder.balanceTv.setText(String.format(context.getString(R.string.tang_balance),bean.getBalance()));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.content_tv)
        TextView contentTv;
        @InjectView(R.id.price_tv)
        TextView priceTv;
        @InjectView(R.id.balance_tv)
        TextView balanceTv;
        @InjectView(R.id.time_tv)
        TextView timeTv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
