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

import com.lanmei.bilan.R;
import com.lanmei.bilan.api.HomeQuLikeApi;
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

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * 我的动态
 */
public class MineDynamicAdapter extends SwipeRefreshAdapter<HomeQuBean> {

    FormatTime time;
    int who;//1区块圈2我的动态点进来的3好友信息中的动态

    public MineDynamicAdapter(Context context, int who) {
        super(context);
        time = new FormatTime();
        this.who = who;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_home_qu, parent, false));
    }


    @Override
    public void onBindViewHolder2(RecyclerView.ViewHolder holder, final int position) {

        final HomeQuBean bean = getItem(position);
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
            ImageHelper.load(context, bean.getPic(), userHeadIv, null, true, R.mipmap.default_pic_b, R.mipmap.default_pic_b);
            userHeadIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (who == 3) {
                        return;
                    }
                    if (!StringUtils.isSame(bean.getUid(), CommonUtils.getUserId(context))) {
                        IntentUtil.startActivity(context, DynamicFriendsActivity.class, bean.getUid());
                    }
                }
            });

            if (StringUtils.isEmpty(bean.getCity())){
                positionTv.setVisibility(View.GONE);
            }else {
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
                    startActivity(bean, position);
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

    public void startActivity(HomeQuBean bean, int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("bean", bean);
        bundle.putInt("position", position);
        bundle.putInt("who", who);
        IntentUtil.startActivity(context, HomeQuDetailsActivity.class, bundle);
    }

}
