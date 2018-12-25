package com.lanmei.bilan.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lanmei.bilan.R;
import com.lanmei.bilan.bean.HomeLianClassBean;
import com.xson.common.adapter.SwipeRefreshAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * 快讯-快讯
 */
public class HomeKuaiAdapter extends SwipeRefreshAdapter<HomeLianClassBean> {


    public HomeKuaiAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_message_she, parent, false));
    }

    @Override
    public void onBindViewHolder2(RecyclerView.ViewHolder holder, int position) {
//        HomeLianClassBean bean = getItem(position);
//        if (StringUtils.isEmpty(bean)) {
//            return;
//        }
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setParameter(null);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.title_tv)
        TextView titleTv;
        @InjectView(R.id.recyclerView)
        RecyclerView recyclerView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);

        }

        public void setParameter(HomeLianClassBean bean) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
//            recyclerView.setNestedScrollingEnabled(false);
            HomeKuaiSubAdapter adapter = new HomeKuaiSubAdapter(context);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return 1;
    }
}
