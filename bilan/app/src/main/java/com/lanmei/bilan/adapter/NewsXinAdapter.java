package com.lanmei.bilan.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.lanmei.bilan.R;
import com.lanmei.bilan.bean.AdBean;
import com.lanmei.bilan.bean.NewsJingBean;
import com.lanmei.bilan.ui.home.activity.HomeBiDetailsActivity;
import com.lanmei.bilan.utils.CommonUtils;
import com.lanmei.bilan.utils.FormatTime;
import com.lanmei.bilan.widget.SudokuView;
import com.xson.common.adapter.SwipeRefreshAdapter;
import com.xson.common.utils.IntentUtil;
import com.xson.common.utils.StringUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * 资讯 新闻
 */
public class NewsXinAdapter extends SwipeRefreshAdapter<NewsJingBean> {

    final public static int TYPE_BANNER = 100;
    List<AdBean> list;
    boolean isFirst = true;

    FormatTime time;

    public NewsXinAdapter(Context context) {
        super(context);
        time = new FormatTime();
    }

    public void setParamenter(List<AdBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {

        if (viewType == TYPE_BANNER) { // banner
            return new BannerViewHolder(LayoutInflater.from(context).inflate(R.layout.include_banner, parent, false));
        }
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_home_bi, parent, false));
    }

    public static class BannerViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.banner)
        ConvenientBanner banner;

        BannerViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }

    @Override
    public int getCount() {
        return super.getCount() + 1;
    }

    @Override
    public int getItemViewType2(int position) {
        if (position == 0) {
            return TYPE_BANNER;
        }
        return super.getItemViewType2(position);
    }

    @Override
    public void onBindViewHolder2(RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_BANNER) {
            onBindBannerViewHolder(holder, position); // banner
            return;
        }

        final NewsJingBean bean = getItem(position - 1);
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


    public void onBindBannerViewHolder(RecyclerView.ViewHolder holder, int position) {


        final BannerViewHolder viewHolder = (BannerViewHolder) holder;
        if (StringUtils.isEmpty(list)) {
            return;
        }
        viewHolder.banner.setPages(new CBViewHolderCreator() {
            @Override
            public Object createHolder() {
                return new HomeAdAdapter();
            }
        }, list);
        viewHolder.banner.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        viewHolder.banner.setPageIndicator(new int[]{R.drawable.shape_item_index_white, R.drawable.shape_item_index_red});
        viewHolder.banner.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        viewHolder.banner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
        if (list.size() == 1) {
            return;
        }
        if (isFirst) {
            isFirst = !isFirst;
            viewHolder.banner.startTurning(3000);
        }
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
