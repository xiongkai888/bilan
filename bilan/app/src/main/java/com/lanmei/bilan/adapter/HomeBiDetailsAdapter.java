package com.lanmei.bilan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.lanmei.bilan.R;
import com.lanmei.bilan.api.CollectNewsApi;
import com.lanmei.bilan.api.NewsAttentionApi;
import com.lanmei.bilan.api.NewsAttentionCancelApi;
import com.lanmei.bilan.bean.NewsJingBean;
import com.lanmei.bilan.bean.UserBean;
import com.lanmei.bilan.bean.homeBiCommBean;
import com.lanmei.bilan.event.AttentionEvent;
import com.lanmei.bilan.utils.CommonUtils;
import com.lanmei.bilan.utils.FormatTime;
import com.lanmei.bilan.webviewpage.WebViewPhotoBrowserUtil;
import com.xson.common.adapter.SwipeRefreshAdapter;
import com.xson.common.bean.BaseBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.helper.ImageHelper;
import com.xson.common.utils.StringUtils;
import com.xson.common.utils.UIHelper;
import com.xson.common.widget.CircleImageView;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * 币头条详情评论列表
 */
public class HomeBiDetailsAdapter extends SwipeRefreshAdapter<homeBiCommBean> {

    final public static int TYPE_BANNER = 100;
    FormatTime time;
    NewsJingBean mBean;

    public HomeBiDetailsAdapter(Context context) {
        super(context);
        time = new FormatTime();
    }

    public void setHomeBiDetails(NewsJingBean bean) {
        this.mBean = bean;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {
        if (viewType == TYPE_BANNER) { // banner
            return new BannerViewHolder(LayoutInflater.from(context).inflate(R.layout.head_home_bi_details, parent, false));
        }
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false));
    }


    @Override
    public void onBindViewHolder2(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_BANNER) {
            onBindBannerViewHolder(holder, position); // banner
            return;
        }
        homeBiCommBean bean = getItem(position - 1);
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

        public void setParameter(final homeBiCommBean bean) {
            ImageHelper.load(context, bean.getPic(), userHeadIv, null, true, R.mipmap.default_pic, R.mipmap.default_pic);
            nameTv.setText(bean.getNickname());
            time.setTime(bean.getAddtime());
            timeTv.setText(time.getFormatTime());
            contentTv.setText(bean.getContent());
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

        @InjectView(R.id.web_view)
        WebView mWebView;
        @InjectView(R.id.title_tv)
        TextView mTitleTv;//标题
        @InjectView(R.id.time_tv)
        TextView mTimeTv;//时间
        @InjectView(R.id.collect_tv)
        TextView mCollectTv;//收藏
        @InjectView(R.id.attention_tv)
        TextView attentionTv;//关注
        @InjectView(R.id.cname_tv)
        TextView mCnameTv;//咨询类型
        @InjectView(R.id.comment_tv)
        TextView mCommentTv;//评论
        @InjectView(R.id.time1_tv)
        TextView mTime1Tv;//时间1
        @InjectView(R.id.head_iv)
        CircleImageView headIv;
        @InjectView(R.id.name_tv)
        TextView nameTv;

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

        UserBean userBean = mBean.getUser();
        if (userBean != null) {
            viewHolder.nameTv.setText(userBean.getNickname());
//            L.d("BaseAppCompatActivity",userBean.getPic());
            ImageHelper.load(context,userBean.getPic(),viewHolder.headIv,null,true,R.mipmap.default_pic,R.mipmap.default_pic);
        }
        viewHolder.mTitleTv.setText(mBean.getTitle());
        time.setTime(mBean.getAddtime());
        viewHolder.mCommentTv.setText(String.format(context.getString(R.string.reviews_num), mBean.getReviews()));
        viewHolder.mTimeTv.setText(time.getFormatTime());
        viewHolder.mTime1Tv.setText(time.getFormatTime());
        viewHolder.mCnameTv.setText(mBean.getCname());
        CommonUtils.setTextViewType(context, mBean.getFavoured() + "", viewHolder.mCollectTv, R.string.collect, R.string.collected);
        viewHolder.mCollectTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CommonUtils.isLogin(context)) {
                    return;
                }
                CollectNewsApi api = new CollectNewsApi();
                api.uid = api.getUserId(context);
                api.postid = mBean.getId();
                HttpClient.newInstance(context).loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
                    @Override
                    public void onResponse(BaseBean response) {
                        if (context == null) {
                            return;
                        }
                        mBean.setFavoured((mBean.getFavoured() == 0) ? 1 : 0);//0为未收藏，1为收藏
                        CommonUtils.setTextViewType(context, mBean.getFavoured() + "", viewHolder.mCollectTv, R.string.collect, R.string.collected);
                        UIHelper.ToastMessage(context, CommonUtils.getString(response));
                    }
                });
            }
        });
        CommonUtils.setTextViewType(context, mBean.getFocuson() + "", viewHolder.attentionTv, R.string.attention, R.string.attentioned);
        viewHolder.attentionTv.setOnClickListener(new View.OnClickListener() {//关注
            @Override
            public void onClick(View v) {
                if (!CommonUtils.isLogin(context)) {
                    return;
                }
                loadFocuson(viewHolder.attentionTv);
            }
        });
        WebViewPhotoBrowserUtil.photoBrowser(context, viewHolder.mWebView, mBean.getContent());
    }


    private void loadFocuson(final TextView attentionTv) {
        if (mBean.getFocuson() == 0) {
            NewsAttentionApi api = new NewsAttentionApi();
            api.uid = api.getUserId(context);
            api.postid = mBean.getId();
            HttpClient.newInstance(context).loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
                @Override
                public void onResponse(BaseBean response) {
                    if (context == null) {
                        return;
                    }
                    setFocuson(1, attentionTv, response);
                }
            });
        } else {
            NewsAttentionCancelApi api = new NewsAttentionCancelApi();
            api.uid = api.getUserId(context);
            api.userid = mBean.getUid();
            HttpClient.newInstance(context).loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
                @Override
                public void onResponse(BaseBean response) {
                    if (context == null) {
                        return;
                    }
                    setFocuson(0, attentionTv, response);
                }
            });
        }
    }

    private void setFocuson(int focuson, TextView attentionTv, BaseBean response) {
        mBean.setFocuson(focuson);//0为未关注，1为关注
        CommonUtils.setTextViewType(context, mBean.getFocuson() + "", attentionTv, R.string.attention, R.string.attentioned);
        UIHelper.ToastMessage(context, CommonUtils.getString(response));
        EventBus.getDefault().post(new AttentionEvent());
    }

}
