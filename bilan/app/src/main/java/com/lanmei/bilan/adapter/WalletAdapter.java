package com.lanmei.bilan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lanmei.bilan.R;
import com.lanmei.bilan.bean.HomeQuBean;
import com.lanmei.bilan.utils.CommonUtils;
import com.xson.common.adapter.SwipeRefreshAdapter;
import com.xson.common.utils.UIHelper;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * 钱包
 */
public class WalletAdapter extends SwipeRefreshAdapter<HomeQuBean> {

    public WalletAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recharge_record, parent, false));
    }

    @Override
    public void onBindViewHolder2(RecyclerView.ViewHolder holder, int position) {
        //        HomeLianClassBean bean = getItem(position);
        //        if (StringUtils.isEmpty(bean)){
        //            return;
        //        }
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIHelper.ToastMessage(context, R.string.developing);
            }
        });
        //        viewHolder.setParameter(bean);

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

        public void setParameter(int position) {

        }
    }

    @Override
    public int getCount() {
        return CommonUtils.quantity;
    }
}
