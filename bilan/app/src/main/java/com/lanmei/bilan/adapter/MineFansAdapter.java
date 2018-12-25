package com.lanmei.bilan.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lanmei.bilan.R;
import com.lanmei.bilan.api.FollowApi;
import com.lanmei.bilan.bean.FansBean;
import com.lanmei.bilan.ui.friend.activity.DynamicFriendsActivity;
import com.lanmei.bilan.utils.AKDialog;
import com.lanmei.bilan.utils.CommonUtils;
import com.xson.common.adapter.SwipeRefreshAdapter;
import com.xson.common.bean.BaseBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.helper.ImageHelper;
import com.xson.common.utils.IntentUtil;
import com.xson.common.utils.StringUtils;
import com.xson.common.utils.UIHelper;
import com.xson.common.widget.CircleImageView;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * 我的粉丝
 */
public class MineFansAdapter extends SwipeRefreshAdapter<FansBean> {

    public MineFansAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_mine_fans, parent, false));
    }

    @Override
    public void onBindViewHolder2(RecyclerView.ViewHolder holder, final int position) {
        final FansBean bean = getItem(position);
        if (StringUtils.isEmpty(bean)) {
            return;
        }
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("uid",bean.getMid());
                IntentUtil.startActivity(context, DynamicFriendsActivity.class, bundle);
            }
        });
        viewHolder.setParameter(bean);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.head_iv)
        CircleImageView headIv;
        @InjectView(R.id.name_tv)
        TextView nameTv;
        @InjectView(R.id.attention_tv)
        TextView attentionTv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);

        }

        public void setParameter(final FansBean bean) {
            CommonUtils.setTextViewType(context,bean.getFollowed(),attentionTv,R.string.attention,R.string.attentioned);
            ImageHelper.load(context, bean.getPic(), headIv, null, true, R.mipmap.default_pic, R.mipmap.default_pic);
            nameTv.setText(bean.getNickname());
            attentionTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!CommonUtils.isLogin(context)) {
                        return;
                    }
                    String str = "";
                    if (StringUtils.isSame(CommonUtils.isZero,bean.getFollowed())) {//0|1  未关注|已关注
                        str = context.getString(R.string.is_follow);
                    } else {
                        str = context.getString(R.string.is_no_follow);
                    }
                    AKDialog.getAlertDialog(context, str, new AKDialog.AlertDialogListener() {
                        @Override
                        public void yes() {
                            isFollow(bean);
                        }
                    });
                }
            });

        }
    }

    private void isFollow(final FansBean bean) {
        FollowApi api = new FollowApi();
        api.uid = api.getUserId(context);
        api.mid = bean.getUid();
//        api.type = bean.getType();
        HttpClient.newInstance(context).loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                if (StringUtils.isEmpty(bean)){
                    return;
                }
                if (StringUtils.isSame(CommonUtils.isZero,bean.getFollowed())) {//0|1  未关注|已关注
                    bean.setFollowed(CommonUtils.isOne);
                } else {
                    bean.setFollowed(CommonUtils.isZero);
                }
                UIHelper.ToastMessage(context, response.getInfo());
                CommonUtils.loadUserInfo(context, null);
                notifyDataSetChanged();
            }
        });
    }
}

