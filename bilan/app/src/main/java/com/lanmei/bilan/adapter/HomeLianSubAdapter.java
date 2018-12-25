package com.lanmei.bilan.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lanmei.bilan.R;
import com.lanmei.bilan.bean.HomeLianContentBean;
import com.xson.common.adapter.SwipeRefreshAdapter;
import com.xson.common.utils.StringUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * 消息中的社区item的列表
 */
public class HomeLianSubAdapter extends SwipeRefreshAdapter<HomeLianContentBean> {


    public HomeLianSubAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_home_lian_sub, parent, false));
    }

    @Override
    public void onBindViewHolder2(RecyclerView.ViewHolder holder, int position) {
        HomeLianContentBean bean = getItem(position);
        if (StringUtils.isEmpty(bean)){
            return;
        }
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setParameter(bean);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.title_tv)
        TextView titleTv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }

        public void setParameter(final HomeLianContentBean bean) {
            titleTv.setText(bean.getSetname());
            titleTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse(bean.getLink());
                    Intent intent = new  Intent(Intent.ACTION_VIEW, uri);
                    context.startActivity(intent);
                }
            });
        }
    }
}
