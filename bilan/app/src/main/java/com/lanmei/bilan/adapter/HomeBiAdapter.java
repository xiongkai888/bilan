package com.lanmei.bilan.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lanmei.bilan.R;
import com.lanmei.bilan.api.HomeKuaiLikeApi;
import com.lanmei.bilan.api.LiKongApi;
import com.lanmei.bilan.bean.HomeBiBean;
import com.lanmei.bilan.event.HomeKuaiLikeEvent;
import com.lanmei.bilan.helper.ShareListener;
import com.lanmei.bilan.ui.home.activity.HomeBiDetailsActivity;
import com.lanmei.bilan.utils.CommonUtils;
import com.lanmei.bilan.utils.FormatTime;
import com.lanmei.bilan.widget.DoubleScaleImageView;
import com.xson.common.adapter.SwipeRefreshAdapter;
import com.xson.common.bean.BaseBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.helper.ImageHelper;
import com.xson.common.utils.IntentUtil;
import com.xson.common.utils.StringUtils;
import com.xson.common.utils.UIHelper;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * 币头条（快讯）
 */
public class HomeBiAdapter extends SwipeRefreshAdapter<HomeBiBean> {

    FormatTime time;
    int lines = 4;

    public HomeBiAdapter(Context context) {
        super(context);
        time = new FormatTime();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_home_kuai_sub, parent, false));
    }

    @Override
    public void onBindViewHolder2(RecyclerView.ViewHolder holder, final int position) {
        final HomeBiBean bean = getItem(position);
        if (StringUtils.isEmpty(bean)) {
            return;
        }
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setParameter(bean, position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.title_tv)
        TextView titleTv;//
//        @InjectView(R.id.sudokuView)
//        SudokuView sudokuView;
        //        @InjectView(R.id.incident_tv)
//        TextView incidentTv;//事件
        @InjectView(R.id.zan_iv)
        ImageView zanIv;//利好（点赞图片）
        @InjectView(R.id.likong_iv)
        ImageView likongIv;//利空（点踩图片）

        @InjectView(R.id.zan_tv)
        TextView liHaoTv;//利好
        @InjectView(R.id.comment_num_tv)
        TextView liKongTv;//利空
        @InjectView(R.id.time_tv)
        TextView timeTv;//时间

        @InjectView(R.id.ll_zan)
        LinearLayout llzan;
        @InjectView(R.id.ll_share)
        LinearLayout llshare;
        @InjectView(R.id.ll_likong)
        LinearLayout llLikong;

        @InjectView(R.id.only_imageView)
        DoubleScaleImageView onlyImageView;
        int lastCharIndex = -1;


        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);

        }

        /**
         * 获取最大极限行的最后一个char索引，如果不超过限制，返回-1
         *
         * @param textView
         * @param content
         * @param maxLine
         * @return
         */
        public int getLastCharIndexForLimitTextView(TextView textView, String content, int maxLine) {
//            L.d("BaseAppCompatActivity", textView.getWidth() + ":textView.getWidth()");
            TextPaint textPaint = textView.getPaint();
            StaticLayout staticLayout = new StaticLayout(content, textPaint, textView.getWidth(), Layout.Alignment.ALIGN_NORMAL, 1, 0, false);
            if (staticLayout.getLineCount() > maxLine)
                return staticLayout.getLineStart(maxLine) - 1;//exceed
            else return -1;//not exceed the max line
        }

        public void setParameter(final HomeBiBean bean, final int position) {

            final String content = CommonUtils.convert2HTML(bean.getContent());

            lastCharIndex = getLastCharIndexForLimitTextView(titleTv, content, lines);
            if (lastCharIndex != -1) {
                if (bean.isFlag()){
                    titleTv.setLines(lines);
                }else {
                    titleTv.setSingleLine(false);
                }
                titleTv.setEllipsize(bean.isFlag()?TextUtils.TruncateAt.END:null); // 收缩
                titleTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (bean.isFlag()) {
                            bean.setFlag(false);
                            titleTv.setEllipsize(null); // 展开
                            titleTv.setSingleLine(bean.isFlag());
                        } else {
                            bean.setFlag(true);
                            titleTv.setEllipsize(TextUtils.TruncateAt.END); // 收缩
                            titleTv.setLines(lines);
                        }
                    }
                });
            } else {
                titleTv.setSingleLine(false);
                titleTv.setOnClickListener(null);
            }
            titleTv.setText(content);
//            incidentTv.setText(bean.getCname());
            liHaoTv.setText(String.format(context.getString(R.string.lihao), bean.getLike()));
            liKongTv.setText(String.format(context.getString(R.string.likong), bean.getUnlike()));
            time.setTime(bean.getAddtime());
            timeTv.setText(time.formatterTimeToHour());

            if (StringUtils.isSame(bean.getLiked(), CommonUtils.isZero)) {
                zanIv.setImageResource(R.mipmap.lihao_off);
            } else {
                zanIv.setImageResource(R.mipmap.lihao_on);
            }
            if (StringUtils.isSame(bean.getUnliked(), CommonUtils.isZero)) {
                likongIv.setImageResource(R.mipmap.likong_off);
            } else {
                likongIv.setImageResource(R.mipmap.likong_on);
            }
            if (StringUtils.isEmpty(bean.getFile())){
                onlyImageView.setVisibility(View.GONE);
            }else {
                onlyImageView.setVisibility(View.VISIBLE);
                ImageHelper.load(context,bean.getFile().get(0),onlyImageView,null,true,R.mipmap.default_pic_b,R.mipmap.default_pic_b);
                onlyImageView.setDoubleClickListener(new DoubleScaleImageView.DoubleClickListener() {
                    @Override
                    public void onSingleTapConfirmed() {
                        CommonUtils.showPhotoBrowserActivity(context,bean.getFile(),bean.getFile().get(0));
                    }

                    @Override
                    public void onDoubleTap() {

                    }
                });
            }
//            sudokuView.setListData(bean.getFile());
//            sudokuView.setOnSingleClickListener(new SudokuView.SudokuViewClickListener() {
//                @Override
//                public void onClick(int positionSub) {
//                    CommonUtils.showPhotoBrowserActivity(context,bean.getFile(),bean.getFile().get(positionSub));
//                }
//
//                @Override
//                public void onDoubleTap(int position) {
//
//                }
//            });
            llzan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {//利好
                    if (!CommonUtils.isLogin(context)) {
                        return;
                    }
                    HomeKuaiLikeApi api = new HomeKuaiLikeApi();
                    api.uid = api.getUserId(context);
                    api.posts_id = bean.getId();
                    HttpClient.newInstance(context).loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
                        @Override
                        public void onResponse(BaseBean response) {
                            if (context == null){
                                return;
                            }
                            int like = Integer.valueOf(bean.getLike());
                            if (StringUtils.isSame(bean.getLiked(), CommonUtils.isZero)) {
                                bean.setLiked(CommonUtils.isOne);
                                like++;
                            } else {
                                bean.setLiked(CommonUtils.isZero);
                                like--;
                            }
                            bean.setLike("" + like);
                            EventBus.getDefault().post(new HomeKuaiLikeEvent(bean.getLike(), bean.getLiked(), bean.getUnlike(), bean.getUnliked(), bean.getId()));//刷新区块圈列表
                            UIHelper.ToastMessage(context, CommonUtils.getString(response));
                            notifyDataSetChanged();
                        }
                    });
                }
            });
            llLikong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {//利空
                    if (!CommonUtils.isLogin(context)) {
                        return;
                    }
                    LiKongApi api = new LiKongApi();
                    api.uid = api.getUserId(context);
                    api.posts_id = bean.getId();
                    HttpClient.newInstance(context).loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
                        @Override
                        public void onResponse(BaseBean response) {
                            if (context == null){
                                return;
                            }
                            int unlike = Integer.valueOf(bean.getUnlike());
                            if (StringUtils.isSame(bean.getUnliked(), CommonUtils.isZero)) {
                                bean.setUnliked(CommonUtils.isOne);
                                unlike++;
                            } else {
                                bean.setUnliked(CommonUtils.isZero);
                                unlike--;
                            }
                            bean.setUnlike("" + unlike);
                            EventBus.getDefault().post(new HomeKuaiLikeEvent(bean.getLike(), bean.getLiked(), bean.getUnlike(), bean.getUnliked(), bean.getId()));//刷新区块圈列表
                            UIHelper.ToastMessage(context, CommonUtils.getString(response));
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
                    if (listener != null) {
                        listener.share(bean.getContent(), bean.getAddtime(), bean.getFile(), CommonUtils.isOne);
                    }
                }
            });
        }
    }

    ShareListener listener;

    public void setShareListener(ShareListener l) {
        this.listener = l;
    }

    public void startActivity(HomeBiBean bean, int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("bean", bean);
        bundle.putInt("position", position);
        IntentUtil.startActivity(context, HomeBiDetailsActivity.class, bundle);
    }

}
