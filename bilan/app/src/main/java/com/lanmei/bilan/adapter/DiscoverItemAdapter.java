package com.lanmei.bilan.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanmei.bilan.R;
import com.lanmei.bilan.bean.DiscoverBean;
import com.lanmei.bilan.ui.discover.activity.OnlyWebActivity;
import com.lanmei.bilan.ui.goods.GoodsDetailsActivity;
import com.xson.common.adapter.SwipeRefreshAdapter;
import com.xson.common.helper.ImageHelper;
import com.xson.common.utils.IntentUtil;
import com.xson.common.utils.StringUtils;
import com.xson.common.utils.UIHelper;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * 消息中的社区item的列表
 */
public class DiscoverItemAdapter extends SwipeRefreshAdapter<DiscoverBean.DataBean> {

    int positionIndex;//

    public DiscoverItemAdapter(Context context, int position) {
        super(context);
        positionIndex = position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {
        if (positionIndex == 0) {
            return new oneImageHolder(LayoutInflater.from(context).inflate(R.layout.item_discover_one, parent, false));
        } else {
            return new ThreeImageHolder(LayoutInflater.from(context).inflate(R.layout.item_discover_three, parent, false));
        }
    }

    @Override
    public void onBindViewHolder2(RecyclerView.ViewHolder holder, int position) {
        DiscoverBean.DataBean bean = getItem(position);
        if (StringUtils.isEmpty(bean)) {
            return;
        }
        if (positionIndex == 0) {
            onOneImageHolder(holder, position, bean); //
        } else {
            onThreeImageHolder(holder, position, bean); //
        }
    }

    public void onOneImageHolder(RecyclerView.ViewHolder holder, int position,final DiscoverBean.DataBean bean) {
        oneImageHolder viewHolder = (oneImageHolder) holder;
        ImageHelper.load(context, bean.getImg(), viewHolder.imageView, null, true, R.mipmap.default_pic, R.mipmap.default_pic);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {//进入商品详情
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("content",bean.getContent());
                bundle.putString("title",bean.getName());
                IntentUtil.startActivity(context, OnlyWebActivity.class,bundle);
            }
        });
    }

    public void onTwoImageHolder(RecyclerView.ViewHolder holder, int position, DiscoverBean.DataBean bean) {
        twoImageHolder viewHolder = (twoImageHolder) holder;
        viewHolder.contentTv.setText(bean.getName());
//        viewHolder.priceSubTv.setText(String.format(context.getString(R.string.price_sub),bean.getSell_price()));
        viewHolder.priceTv.setText(String.format(context.getString(R.string.price_sub), bean.getSell_price()));
        ImageHelper.load(context, bean.getImg(), viewHolder.imageView, null, true, R.mipmap.default_pic, R.mipmap.default_pic);

    }

    public void onThreeImageHolder(RecyclerView.ViewHolder holder, int position,final DiscoverBean.DataBean bean) {
        ThreeImageHolder viewHolder = (ThreeImageHolder) holder;
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {//进入商品详情
            @Override
            public void onClick(View v) {
                IntentUtil.startActivity(context, GoodsDetailsActivity.class,bean.getId());
            }
        });
        viewHolder.contentTv.setText(bean.getName());
        viewHolder.moneyTv.setText(String.format(context.getString(R.string.price_sub), bean.getSell_price()));
        ImageHelper.load(context, bean.getImg(), viewHolder.imageView, null, true, R.mipmap.default_pic, R.mipmap.default_pic);
    }


    /**
     * 创建三种ViewHolder
     */
    public class oneImageHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.image)
        ImageView imageView;

        public oneImageHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UIHelper.ToastMessage(context,R.string.developing);
                }
            });
        }
    }

    public class twoImageHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.image)
        ImageView imageView;
        @InjectView(R.id.content_tv)
        TextView contentTv;
        //        @InjectView(R.id.price_sub_tv)
//        TextView priceSubTv;
        @InjectView(R.id.price_tv)
        TextView priceTv;

        public twoImageHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IntentUtil.startActivity(context, GoodsDetailsActivity.class);
                }
            });
        }
    }

    public class ThreeImageHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.image)
        ImageView imageView;
        @InjectView(R.id.content_tv)
        TextView contentTv;
        @InjectView(R.id.money_tv)
        TextView moneyTv;

        public ThreeImageHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);

        }


    }
}
