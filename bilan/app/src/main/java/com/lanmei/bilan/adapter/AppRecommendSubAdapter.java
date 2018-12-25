package com.lanmei.bilan.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanmei.bilan.R;
import com.lanmei.bilan.bean.AppRecommendSubBean;
import com.xson.common.adapter.SwipeRefreshAdapter;
import com.xson.common.helper.ImageHelper;
import com.xson.common.utils.StringUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * 应该推荐sub
 */
public class AppRecommendSubAdapter extends SwipeRefreshAdapter<AppRecommendSubBean> {

    public AppRecommendSubAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_app_recommend, parent, false));
    }

    @Override
    public void onBindViewHolder2(RecyclerView.ViewHolder holder, int position) {
        final AppRecommendSubBean bean = getItem(position);
        if (StringUtils.isEmpty(bean)) {
            return;
        }
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setParameter(bean);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(bean.getLink());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.pic)
        ImageView pic;
        @InjectView(R.id.name_tv)
        TextView nameTv;
        @InjectView(R.id.content_tv)
        TextView contentTv;
        @InjectView(R.id.download_tv)
        TextView downloadTv;


        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }

        public void setParameter(final AppRecommendSubBean bean) {
            ImageHelper.load(context,bean.getImg(),pic,null,true,R.mipmap.default_pic,R.mipmap.default_pic);
            nameTv.setText(bean.getSetname());
            contentTv.setText(bean.getContent());
//            downloadTv.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    UIHelper.ToastMessage(context,R.string.developing);
//                }
//            });
        }
    }
}
