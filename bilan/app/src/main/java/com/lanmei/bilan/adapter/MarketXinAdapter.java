package com.lanmei.bilan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lanmei.bilan.R;
import com.lanmei.bilan.bean.HomeQuBean;
import com.xson.common.adapter.SwipeRefreshAdapter;

import butterknife.ButterKnife;


/**
 * 行情--币种信息
 */
public class MarketXinAdapter extends SwipeRefreshAdapter<HomeQuBean> {


    public MarketXinAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_market_xin, parent, false));
    }

    @Override
    public void onBindViewHolder2(RecyclerView.ViewHolder holder, int position) {

        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setParameter(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

//        @InjectView(R.id.line_tv)
//        TextView lineTv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);

        }

        public void setParameter(int position){

        }
    }
}
