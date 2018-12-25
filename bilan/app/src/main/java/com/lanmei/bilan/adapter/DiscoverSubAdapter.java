package com.lanmei.bilan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanmei.bilan.R;
import com.lanmei.bilan.bean.DiscoverSubListBean;
import com.lanmei.bilan.ui.goods.GoodsDetailsActivity;
import com.xson.common.adapter.SwipeRefreshAdapter;
import com.xson.common.helper.ImageHelper;
import com.xson.common.utils.IntentUtil;
import com.xson.common.utils.StringUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * 发现点击item进去的界面
 */
public class DiscoverSubAdapter extends SwipeRefreshAdapter<DiscoverSubListBean> {

    public DiscoverSubAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_discover_three, parent, false));
    }

    @Override
    public void onBindViewHolder2(RecyclerView.ViewHolder holder, final int position) {
        final DiscoverSubListBean bean = getItem(position);
        if (StringUtils.isEmpty(bean)) {
            return;
        }
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setParameter(bean);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtil.startActivity(context, GoodsDetailsActivity.class,bean.getId());
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.image)
        ImageView image;
        @InjectView(R.id.content_tv)
        TextView contentTv;
        @InjectView(R.id.money_tv)
        TextView moneyTv;
        @InjectView(R.id.sell_num_tv)
        TextView sellNumTv;//已购买(销售)数

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);

        }

        public void setParameter(DiscoverSubListBean bean) {
            ImageHelper.load(context, bean.getImg(), image, null, true, R.mipmap.default_pic_b, R.mipmap.default_pic_b);
            contentTv.setText(bean.getName());
            moneyTv.setText(String.format(context.getString(R.string.price_sub), bean.getSell_price()));
            sellNumTv.setText(String.format(context.getString(R.string.selled), bean.getSale()));
            sellNumTv.setVisibility(View.VISIBLE);
        }
    }

}
