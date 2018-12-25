package com.lanmei.bilan.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lanmei.bilan.R;
import com.lanmei.bilan.bean.DiscoverBean;
import com.lanmei.bilan.ui.discover.activity.DiscoverSubActivity;
import com.lanmei.bilan.ui.discover.activity.ItemExpressActivity;
import com.xson.common.adapter.SwipeRefreshAdapter;
import com.xson.common.utils.IntentUtil;
import com.xson.common.utils.StringUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * 发现
 */
public class DiscoverAdapter extends SwipeRefreshAdapter<DiscoverBean> {


    public DiscoverAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_discover, parent, false));
    }

    @Override
    public void onBindViewHolder2(RecyclerView.ViewHolder holder,final int position) {
        final DiscoverBean bean = getItem(position);
        if (StringUtils.isEmpty(bean)) {
            return;
        }
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.titleTv.setText(bean.getName());
        viewHolder.moreTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 0){
                    IntentUtil.startActivity(context, ItemExpressActivity.class, bean.getId());
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("name", bean.getName());
                bundle.putString("brandId", bean.getId());
                IntentUtil.startActivity(context, DiscoverSubActivity.class, bundle);
            }
        });
        DiscoverItemAdapter subAdapter = new DiscoverItemAdapter(context, position);
        if (position == 0) {
            viewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            viewHolder.recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        }
        subAdapter.setData(bean.getData());
        viewHolder.recyclerView.setAdapter(subAdapter);
    }

    @Override
    public int getCount() {
        if (super.getCount() > 3) {
            return 3;
        }
        return super.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.more_tv)
        TextView moreTv;
        @InjectView(R.id.title_tv)
        TextView titleTv;
        @InjectView(R.id.recyclerView)
        RecyclerView recyclerView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
