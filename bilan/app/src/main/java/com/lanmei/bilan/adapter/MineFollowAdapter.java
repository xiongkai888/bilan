package com.lanmei.bilan.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lanmei.bilan.R;
import com.lanmei.bilan.api.AddFriendsApi;
import com.lanmei.bilan.api.FollowApi;
import com.lanmei.bilan.bean.MineFollowBean;
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
 * 我的关注
 */
public class MineFollowAdapter extends SwipeRefreshAdapter<MineFollowBean> {

    public MineFollowAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_two_botton, parent, false));
    }

    @Override
    public void onBindViewHolder2(RecyclerView.ViewHolder holder, final int position) {
        final MineFollowBean bean = getItem(position);
        if (StringUtils.isEmpty(bean)) {
            return;
        }
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setParameter(bean);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("uid",bean.getMid());
                IntentUtil.startActivity(context, DynamicFriendsActivity.class, bundle);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.head_iv)
        CircleImageView headIv;
        @InjectView(R.id.name_tv)
        TextView nameTv;
        @InjectView(R.id.add_friends_tv)
        TextView addFriendsTv;
        @InjectView(R.id.attention_tv)
        TextView attentionTv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }

        public void setParameter(final MineFollowBean bean) {
            CommonUtils.setTextViewType(context,bean.getFollowed()+"",attentionTv,R.string.attention,R.string.attentioned);
            int isFriend = bean.getIs_friend();
            if (isFriend == 1) {
                addFriendsTv.setVisibility(View.GONE);
            } else {
                addFriendsTv.setVisibility(View.VISIBLE);
                addFriendsTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AKDialog.getAlertDialog(context, context.getString(R.string.is_add_friend), new AKDialog.AlertDialogListener() {
                            @Override
                            public void yes() {
                                isAddFriend(bean);
                            }
                        });
                    }
                });
            }
            ImageHelper.load(context, bean.getPic(), headIv, null, true, R.mipmap.default_pic, R.mipmap.default_pic);
            nameTv.setText(bean.getNickname());

            attentionTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!CommonUtils.isLogin(context)) {
                        return;
                    }
                    String str = "";
                    if (StringUtils.isSame(CommonUtils.isZero,bean.getFollowed()+"")) {//0|1  未关注|已关注
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

    private void isAddFriend(final MineFollowBean bean) {
        HttpClient httpClient = HttpClient.newInstance(context);
        AddFriendsApi api = new AddFriendsApi();
        api.mid = bean.getMid();
        api.uid = api.getUserId(context);
        httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                bean.setIs_friend(1);
//                            CommonUtils.loadUserInfo(context,null);
                UIHelper.ToastMessage(context, response.getInfo());
                notifyDataSetChanged();
            }
        });
    }

    private void isFollow(final MineFollowBean bean) {
        FollowApi api = new FollowApi();
        api.uid = api.getUserId(context);
        api.mid = bean.getMid();
//        api.type = bean.getType();
        HttpClient.newInstance(context).loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                if (StringUtils.isSame(CommonUtils.isZero,bean.getFollowed()+"")) {//0|1  未关注|已关注
                    bean.setFollowed(1);
                } else {
                    bean.setFollowed(0);
                }
                UIHelper.ToastMessage(context, response.getInfo());
                CommonUtils.loadUserInfo(context, null);
                notifyDataSetChanged();
            }
        });
    }
}

