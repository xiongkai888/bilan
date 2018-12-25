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
import com.lanmei.bilan.bean.DiscoverSubListBean;
import com.lanmei.bilan.ui.discover.activity.OnlyWebActivity;
import com.xson.common.adapter.SwipeRefreshAdapter;
import com.xson.common.helper.ImageHelper;
import com.xson.common.utils.IntentUtil;
import com.xson.common.utils.StringUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * 速递或梦想
 */
public class ItemExpressSubAdapter extends SwipeRefreshAdapter<DiscoverSubListBean> {

    public ItemExpressSubAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_discover_xiang, parent, false));
    }

    @Override
    public void onBindViewHolder2(RecyclerView.ViewHolder holder, final int position) {
        final DiscoverSubListBean bean = getItem(position);
        if (StringUtils.isEmpty(bean)) {
            return;
        }
        ViewHolder viewHolder = (ViewHolder)holder;

        ImageHelper.load(context,bean.getImg(),viewHolder.image,null,true,R.mipmap.default_pic_b,R.mipmap.default_pic_b);
        viewHolder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("content",bean.getContent());
                bundle.putString("title",bean.getName());
                IntentUtil.startActivity(context, OnlyWebActivity.class,bundle);
            }
        });
        viewHolder.name.setText(bean.getName());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        @InjectView(R.id.image)
        ImageView image;
        @InjectView(R.id.name_tv)
        TextView name;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
