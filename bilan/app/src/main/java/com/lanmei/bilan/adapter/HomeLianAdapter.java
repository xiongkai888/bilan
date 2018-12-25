package com.lanmei.bilan.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lanmei.bilan.R;
import com.lanmei.bilan.api.HomeLianContentApi;
import com.lanmei.bilan.bean.HomeLianClassBean;
import com.lanmei.bilan.bean.HomeLianContentBean;
import com.lanmei.bilan.ui.home.activity.HomeLianSubActivity;
import com.xson.common.adapter.SwipeRefreshAdapter;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.utils.IntentUtil;
import com.xson.common.utils.StringUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * 首页 链世界
 */
public class HomeLianAdapter extends SwipeRefreshAdapter<HomeLianClassBean> {



    public HomeLianAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_home_lian, parent, false));
    }

    @Override
    public void onBindViewHolder2(RecyclerView.ViewHolder holder, int position) {
        HomeLianClassBean bean = getItem(position);
        if (StringUtils.isEmpty(bean)){
            return;
        }
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setParameter(bean);
//        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                NewsDetailsActivity.startActivityNewsDetails(context, bean);
//            }
//        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.title_tv)
        TextView titleTv;
        @InjectView(R.id.ll_all)
        LinearLayout llAll;
        @InjectView(R.id.recyclerView)
        RecyclerView recyclerView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);

        }

        public void setParameter(final HomeLianClassBean bean) {
            titleTv.setText(bean.getClassname());
            HomeLianContentApi api = new HomeLianContentApi();
            api.classid = bean.getId();
            HttpClient.newInstance(context).request(api, new BeanRequest.SuccessListener<NoPageListBean<HomeLianContentBean>>() {
                @Override
                public void onResponse(NoPageListBean<HomeLianContentBean> response) {
                    HomeLianSubAdapter adapter = new HomeLianSubAdapter(context);
                    adapter.setData(response.data);
                    recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
                    recyclerView.setAdapter(adapter);
                }
            });
            llAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("bean",bean);
                    IntentUtil.startActivity(context, HomeLianSubActivity.class,bundle);
                }
            });
        }
    }

}
