package com.lanmei.bilan.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.lanmei.bilan.R;
import com.lanmei.bilan.api.HomeQuLikeApi;
import com.lanmei.bilan.bean.AdBean;
import com.lanmei.bilan.bean.HomeQuBean;
import com.lanmei.bilan.event.HomeQuLikeEvent;
import com.lanmei.bilan.helper.ShareListener;
import com.lanmei.bilan.ui.friend.activity.DynamicFriendsActivity;
import com.lanmei.bilan.ui.home.activity.HomeQuDetailsActivity;
import com.lanmei.bilan.utils.CommonUtils;
import com.lanmei.bilan.utils.FormatTime;
import com.lanmei.bilan.widget.SudokuView;
import com.xson.common.adapter.SwipeRefreshAdapter;
import com.xson.common.bean.BaseBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.helper.ImageHelper;
import com.xson.common.utils.IntentUtil;
import com.xson.common.utils.StringUtils;
import com.xson.common.utils.UIHelper;
import com.xson.common.widget.CircleImageView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * 区块圈
 */
public class HomeQuAdapter extends SwipeRefreshAdapter<HomeQuBean> {

    final public static int TYPE_BANNER = 100;
//    List<String> list;
    List<AdBean> list;
    FormatTime time;
    int who;//1区块圈2我的动态点进来的3好友信息中的动态

    boolean isFirst = true;

    public HomeQuAdapter(Context context, int who) {
        super(context);
        time = new FormatTime();
        this.who = who;
    }

    public void setParamenter(List<AdBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {

        if (viewType == TYPE_BANNER) { // banner
            BannerViewHolder bannerHolder = new BannerViewHolder(LayoutInflater.from(context).inflate(R.layout.include_banner, parent, false));
            return bannerHolder;
        }

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_home_qu, parent, false));
    }

    static class BannerViewHolder extends RecyclerView.ViewHolder {

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

        final HomeQuBean bean = getItem(position - 1);
        if (StringUtils.isEmpty(bean)) {
            return;
        }
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setParameter(bean, position);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(bean);
            }
        });
    }

    public void onBindBannerViewHolder(RecyclerView.ViewHolder holder, int position) {


        final BannerViewHolder viewHolder = (BannerViewHolder) holder;
        if (who != 1) {
            viewHolder.banner.setVisibility(View.GONE);
            return;
        }
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

        @InjectView(R.id.user_head_iv)
        CircleImageView userHeadIv;
        @InjectView(R.id.user_name_tv)
        TextView nameTv;
        @InjectView(R.id.time_tv)
        TextView timeTv;
        @InjectView(R.id.title_tv)
        TextView titleTv;//
        @InjectView(R.id.sudokuView)
        SudokuView sudokuView;
        @InjectView(R.id.zan_iv)
        ImageView zanIv;//点赞图片
        @InjectView(R.id.zan_tv)
        TextView zanTv;
        @InjectView(R.id.share_tv)
        TextView shareTv;
        @InjectView(R.id.position_tv)
        TextView positionTv;
        @InjectView(R.id.comment_tv)
        TextView commentTv;
        @InjectView(R.id.ll_zan)
        LinearLayout llzan;
        @InjectView(R.id.ll_share)
        LinearLayout llshare;
        @InjectView(R.id.ll_comm)
        LinearLayout llcomm;


        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);

        }

        public void setParameter(final HomeQuBean bean, final int position) {
            ImageHelper.load(context, bean.getPic(), userHeadIv, null, true, R.mipmap.default_pic, R.mipmap.default_pic);
            userHeadIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (who == 3) {
                        return;
                    }
                    if (!StringUtils.isSame(bean.getUid(), CommonUtils.getUserId(context))) {
                        Bundle bundle = new Bundle();
                        bundle.putString("uid", bean.getUid());
                        IntentUtil.startActivity(context, DynamicFriendsActivity.class, bundle);
                    }
                }
            });

            if (StringUtils.isEmpty(bean.getCity())) {
                positionTv.setVisibility(View.GONE);
            } else {
                positionTv.setVisibility(View.VISIBLE);
                positionTv.setText(bean.getCity());
            }

            nameTv.setText(bean.getNickname());
            time.setTime(bean.getAddtime());
            timeTv.setText(time.getFormatTime());
            titleTv.setText(bean.getTitle());
            zanTv.setText(bean.getLike());//点赞数
            commentTv.setText(String.format(context.getString(R.string.comm_num), bean.getReviews()));//评论数
            sudokuView.setListData(bean.getFile());
            sudokuView.setOnSingleClickListener(new SudokuView.SudokuViewClickListener() {
                @Override
                public void onClick(int positionSub) {
                    startActivity(bean);
                }

                @Override
                public void onDoubleTap(int position) {
                    CommonUtils.showPhotoBrowserActivity(context, bean.getFile(), bean.getFile().get(position));
                }
            });
            if (StringUtils.isSame(bean.getLiked(), CommonUtils.isZero)) {
                zanIv.setImageResource(R.mipmap.home_zan_off);
            } else {
                zanIv.setImageResource(R.mipmap.home_zan_on);
            }
            llzan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!CommonUtils.isLogin(context)) {
                        return;
                    }
                    HomeQuLikeApi api = new HomeQuLikeApi();
                    api.uid = api.getUserId(context);
                    api.id = bean.getId();
                    HttpClient.newInstance(context).loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
                        @Override
                        public void onResponse(BaseBean response) {
                            int like = Integer.valueOf(bean.getLike());
                            if (StringUtils.isSame(bean.getLiked(), CommonUtils.isZero)) {
                                bean.setLiked(CommonUtils.isOne);
                                like++;
                            } else {
                                bean.setLiked(CommonUtils.isZero);
                                like--;
                            }
                            EventBus.getDefault().post(new HomeQuLikeEvent("" + like, bean.getLiked(), bean.getReviews(),bean.getId()));//刷新区块圈列表
                            bean.setLike("" + like);
                            UIHelper.ToastMessage(context, response.getInfo());
                            notifyDataSetChanged();
                        }
                    });
                }
            });
            llshare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!CommonUtils.isLogin(context)) {
                        return;
                    }
                    if (l != null) {
                        l.share(bean.getTitle(),bean.getAddtime(),bean.getFile(),CommonUtils.isTwo);
                    }
                }
            });
        }
    }

    ShareListener l;

    public void setShareListener(ShareListener l) {
        this.l = l;
    }

    public void startActivity(HomeQuBean bean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("bean", bean);
        bundle.putInt("who", who);
        IntentUtil.startActivity(context, HomeQuDetailsActivity.class, bundle);
    }

}
