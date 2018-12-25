package com.lanmei.bilan.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lanmei.bilan.R;
import com.lanmei.bilan.api.SheQuQunApi;
import com.lanmei.bilan.bean.HomeLianClassBean;
import com.lanmei.bilan.bean.SheQuQunBean;
import com.lanmei.bilan.monitor.JoinGroupListener;
import com.xson.common.adapter.SwipeRefreshAdapter;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.utils.StringUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * 消息中的社区
 */
public class MessageSheAdapter extends SwipeRefreshAdapter<HomeLianClassBean> {


    public MessageSheAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_message_she, parent, false));
    }

    @Override
    public void onBindViewHolder2(RecyclerView.ViewHolder holder, int position) {
        HomeLianClassBean bean = getItem(position);
        if (StringUtils.isEmpty(bean)){
            return;
        }
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setParameter(bean);
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
            titleTv.setText(bean.getClassname());
            SheQuQunApi api = new SheQuQunApi();
            api.classid = bean.getId();
            HttpClient.newInstance(context).request(api, new BeanRequest.SuccessListener<NoPageListBean<SheQuQunBean>>() {
                @Override
                public void onResponse(NoPageListBean<SheQuQunBean> response) {
                    if (StringUtils.isEmpty(context) || StringUtils.isEmpty(response)){
                        return;
                    }
                    List<SheQuQunBean> list = response.data;
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    recyclerView.setNestedScrollingEnabled(false);
                    MessageSheSubAdapter adapter = new MessageSheSubAdapter(context);
                    adapter.setData(list);
                    if (l != null){
                        adapter.setJoinGroupListener(l);
                    }
                    recyclerView.setAdapter(adapter);
                }
            });
        }
    }


    //加入群监听
    JoinGroupListener l;

    public void setJoinGroupListener(JoinGroupListener l){
        this.l = l;
    }
}
