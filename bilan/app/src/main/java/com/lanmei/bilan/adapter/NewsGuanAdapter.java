package com.lanmei.bilan.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lanmei.bilan.R;
import com.lanmei.bilan.bean.NewsJingBean;
import com.lanmei.bilan.ui.home.activity.HomeBiDetailsActivity;
import com.lanmei.bilan.utils.CommonUtils;
import com.lanmei.bilan.utils.FormatTime;
import com.lanmei.bilan.widget.SudokuView;
import com.xson.common.adapter.SwipeRefreshAdapter;
import com.xson.common.utils.IntentUtil;
import com.xson.common.utils.StringUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * 资讯-关注(新闻、精选、我的文章)
 */
public class NewsGuanAdapter extends SwipeRefreshAdapter<NewsJingBean> {

    FormatTime time;

    public NewsGuanAdapter(Context context) {
        super(context);
        time = new FormatTime();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_home_bi, parent, false));
    }

    @Override
    public void onBindViewHolder2(RecyclerView.ViewHolder holder, final int position) {
        final NewsJingBean bean = getItem(position);
        if (StringUtils.isEmpty(bean)) {
            return;
        }
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setParameter(bean, position);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(bean, position);
            }
        });
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.title_tv)
        TextView titleTv;//
        @InjectView(R.id.sudokuView)
        SudokuView sudokuView;
        @InjectView(R.id.incident_tv)
        TextView incidentTv;//事件
        @InjectView(R.id.comment_num_tv)
        TextView commentNumTv;//评论数
        @InjectView(R.id.time_tv)
        TextView timeTv;//时间
        @InjectView(R.id.top_tv)
        TextView topTv;//置顶

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);

        }

        public void setParameter(final NewsJingBean bean, final int position) {
            titleTv.setText(bean.getTitle());
            incidentTv.setText(bean.getCname());
            commentNumTv.setText(String.format(context.getString(R.string.reviews_num), bean.getReviews()));
            time.setTime(bean.getAddtime());
            timeTv.setText(time.getFormatTime());
//            sudokuView.setListData(bean.getFile());
            sudokuView.setListData(bean.getFile());
            sudokuView.setOnSingleClickListener(new SudokuView.SudokuViewClickListener() {
                @Override
                public void onClick(int positionSub) {
                    startActivity(bean, position);
                }

                @Override
                public void onDoubleTap(int position) {
                    CommonUtils.showPhotoBrowserActivity(context, bean.getFile(), bean.getFile().get(position));
                }
            });

            if (StringUtils.isSame(CommonUtils.isOne, bean.getTop()) || StringUtils.isSame(CommonUtils.isOne, bean.getEssential()) || StringUtils.isSame(CommonUtils.isOne, bean.getRecommend())|| StringUtils.isSame(CommonUtils.isOne, bean.getAdvertising())) {
                if (StringUtils.isSame(CommonUtils.isOne, bean.getTop())) {
                    topTv.setText(R.string.top);
                } else if (StringUtils.isSame(CommonUtils.isOne, bean.getEssential())) {
                    topTv.setText(R.string.essential);
                } else if (StringUtils.isSame(CommonUtils.isOne, bean.getRecommend())){
                    topTv.setText(R.string.recommend);
                } else {
                    topTv.setText(R.string.advertising);
                }
                topTv.setVisibility(View.VISIBLE);
            } else {
                topTv.setVisibility(View.GONE);
            }
        }
    }

    public void startActivity(NewsJingBean bean, int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("bean", bean);
        bundle.putInt("position", position);
        IntentUtil.startActivity(context, HomeBiDetailsActivity.class, bundle);
    }

}
