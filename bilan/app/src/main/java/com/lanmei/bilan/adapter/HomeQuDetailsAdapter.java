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
import com.lanmei.bilan.api.FollowApi;
import com.lanmei.bilan.api.HomeQuLikeApi;
import com.lanmei.bilan.bean.HomeQuBean;
import com.lanmei.bilan.bean.homeQuCommBean;
import com.lanmei.bilan.event.AttentionFriendEvent;
import com.lanmei.bilan.event.HomeQuLikeEvent;
import com.lanmei.bilan.helper.ShareListener;
import com.lanmei.bilan.ui.friend.activity.DynamicFriendsActivity;
import com.lanmei.bilan.utils.AKDialog;
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
 * 区块圈详情评论列表
 */
public class HomeQuDetailsAdapter extends SwipeRefreshAdapter<homeQuCommBean> {

    public int TYPE_BANNER = 100;
    private FormatTime time;
    private HomeQuBean mBean;
    private int who;//1区块圈2我的动态点进来
    private boolean isSelf;//是否是自己的动态

    public HomeQuDetailsAdapter(Context context, HomeQuBean bean,boolean isSelf,int who) {
        super(context);
        time = new FormatTime();
        mBean = bean;
        this.isSelf = isSelf;
        this.who = who;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {
        if (viewType == TYPE_BANNER) { // banner
            return new BannerViewHolder(LayoutInflater.from(context).inflate(R.layout.head_home_qu_details, parent, false));
        }
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false));
    }


    @Override
    public void onBindViewHolder2(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_BANNER) {
            onBindBannerViewHolder(holder, position); // banner
            return;
        }

        homeQuCommBean bean = getItem(position - 1);
        if (StringUtils.isEmpty(bean)) {
            return;
        }
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setParameter(bean);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.user_head_iv)
        CircleImageView userHeadIv;
        @InjectView(R.id.name_tv)
        TextView nameTv;
        @InjectView(R.id.content_tv)
        TextView contentTv;
        @InjectView(R.id.time_tv)
        TextView timeTv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);

        }

        public void setParameter(final homeQuCommBean bean) {
            ImageHelper.load(context,bean.getPic(),userHeadIv,null,true,R.mipmap.default_pic,R.mipmap.default_pic);
            nameTv.setText(bean.getUsername());
            contentTv.setText(bean.getContent());
            time.setTime(bean.getAddtime());
            timeTv.setText(time.getFormatTime());
        }
    }

    @Override
    public int getItemViewType2(int position) {
        if (position == 0) {
            return TYPE_BANNER;
        }
        return super.getItemViewType2(position);
    }


    //头部
    public class BannerViewHolder extends RecyclerView.ViewHolder {


        @InjectView(R.id.user_head_iv)
        CircleImageView userHeadIv;
        @InjectView(R.id.user_name_tv)
        TextView userNameTv;
        @InjectView(R.id.time_tv)
        TextView timeTv;
        @InjectView(R.id.attention_tv)
        TextView attentionTv;
        @InjectView(R.id.content_tv)
        TextView contentTv;
        @InjectView(R.id.zan_tv)
        TextView zanTv;
        @InjectView(R.id.comment_tv)
        TextView commentTv;
        @InjectView(R.id.sudokuView)
        SudokuView sudokuView;
        @InjectView(R.id.ll_zan)
        LinearLayout llzan;
        @InjectView(R.id.ll_share)
        LinearLayout llshare;
        @InjectView(R.id.ll_comm)
        LinearLayout llcomm;
        @InjectView(R.id.zan_iv)
        ImageView zanIv;//点赞图片

        BannerViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }

    @Override
    public int getCount() {
        return super.getCount() + 1;
    }

    public void onBindBannerViewHolder(RecyclerView.ViewHolder holder, int position) {
        final BannerViewHolder viewHolder = (BannerViewHolder) holder;
        if (mBean == null) {
            return;
        }
        if (!isSelf){
            CommonUtils.setTextViewType(context,mBean.getFollowed(),viewHolder.attentionTv,R.string.attention,R.string.attentioned);
            viewHolder.attentionTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!CommonUtils.isLogin(context)){
                        return;
                    }
                    String str = "";
                    if (CommonUtils.isZero.equals(mBean.getFollowed())){//0|1  未关注|已关注
                        str = context.getString(R.string.is_follow);
                    }else {
                        str = context.getString(R.string.is_no_follow);
                    }
                    AKDialog.getAlertDialog(context, str , new AKDialog.AlertDialogListener() {
                        @Override
                        public void yes() {
                            isFollow();
                        }
                    });
                }
            });
        }else {
            viewHolder.attentionTv.setVisibility(View.GONE);
        }
        viewHolder.userNameTv.setText(mBean.getNickname());
        time.setTime(mBean.getAddtime());
        viewHolder.timeTv.setText(time.getFormatTime());
        viewHolder.timeTv.setText(time.getFormatTime());
        viewHolder.commentTv.setText(mBean.getReviews());
        viewHolder.contentTv.setText(mBean.getTitle());
        viewHolder.sudokuView.setListData(mBean.getFile());
        viewHolder.sudokuView.setOnSingleClickListener(new SudokuView.SudokuViewClickListener() {
            @Override
            public void onClick(int positionSub) {
                CommonUtils.showPhotoBrowserActivity(context, mBean.getFile(), mBean.getFile().get(positionSub));
            }

            @Override
            public void onDoubleTap(int position) {

            }
        });

        viewHolder.zanTv.setText(mBean.getLike());
        viewHolder.commentTv.setText(String.format(context.getString(R.string.comm_num),mBean.getReviews()));//评论数
        viewHolder.commentTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        viewHolder.userHeadIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("uid",mBean.getId());
                IntentUtil.startActivity(context, DynamicFriendsActivity.class, bundle);
            }
        });
        ImageHelper.load(context,mBean.getPic(),viewHolder.userHeadIv,null,true,R.mipmap.default_pic,R.mipmap.default_pic);

        int idImage = StringUtils.isSame(mBean.getLiked(), CommonUtils.isZero)?R.mipmap.home_zan_off:R.mipmap.home_zan_on;
        viewHolder.zanIv.setImageResource(idImage);
        viewHolder.llzan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CommonUtils.isLogin(context)){
                    return;
                }
                HomeQuLikeApi api = new HomeQuLikeApi();
                api.uid = api.getUserId(context);
                api.id = mBean.getId();
                HttpClient.newInstance(context).loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
                    @Override
                    public void onResponse(BaseBean response) {
                        int like = Integer.valueOf(mBean.getLike());
                        if (StringUtils.isSame(mBean.getLiked(), CommonUtils.isZero)) {
                            mBean.setLiked(CommonUtils.isOne);
                            like++;
                        } else {
                            mBean.setLiked(CommonUtils.isZero);
                            like--;
                        }
                        EventBus.getDefault().post(new HomeQuLikeEvent("" + like, mBean.getLiked(), mBean.getReviews(),mBean.getId()));//刷新区块圈列表
                        mBean.setLike("" + like);
                        UIHelper.ToastMessage(context, response.getInfo());
                        notifyDataSetChanged();
                    }
                });
            }
        });
        viewHolder.llshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CommonUtils.isLogin(context)){
                    return;
                }
                if (l != null){
                    l.share(mBean.getTitle(),mBean.getAddtime(),mBean.getFile(),CommonUtils.isTwo);
                }
            }
        });
    }

    private ShareListener l;

    public void setShareListener(ShareListener l){
        this.l = l;
    }

    private void isFollow() {
        FollowApi api = new FollowApi();
        api.uid = api.getUserId(context);
        api.mid = mBean.getUid();
        HttpClient.newInstance(context).loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                if (mBean == null){
                    return;
                }
                if (CommonUtils.isZero.equals(mBean.getFollowed())){//0|1  未关注|已关注
                    mBean.setFollowed(CommonUtils.isOne);
                }else {
                    mBean.setFollowed(CommonUtils.isZero);
                }
                UIHelper.ToastMessage(context,response.getInfo());
                EventBus.getDefault().post(new AttentionFriendEvent(mBean.getUid(),mBean.getFollowed()));//该uid都设置为关注或不关注
                CommonUtils.loadUserInfo(context,null);
                notifyDataSetChanged();
            }
        });
    }

}
