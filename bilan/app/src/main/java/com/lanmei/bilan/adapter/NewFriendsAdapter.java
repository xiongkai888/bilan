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
import com.lanmei.bilan.bean.NewFriendsBean;
import com.lanmei.bilan.event.AttentionFriendEvent;
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

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * 新朋友
 */
public class NewFriendsAdapter extends SwipeRefreshAdapter<NewFriendsBean> {

    @InjectView(R.id.head_iv)
    CircleImageView headIv;
    @InjectView(R.id.name_tv)
    TextView nameTv;
    @InjectView(R.id.attention_tv)
    TextView attentionTv;

    public NewFriendsAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_mine_fans, parent, false));
    }

    @Override
    public void onBindViewHolder2(final RecyclerView.ViewHolder holder, int position) {
        final NewFriendsBean bean = getItem(position);
        if (StringUtils.isEmpty(bean)){
            return;
        }
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setParameter(bean);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("uid",bean.getId());
                IntentUtil.startActivity(context, DynamicFriendsActivity.class, bundle);
            }
        });
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

        public void setParameter(final NewFriendsBean bean) {
            ImageHelper.load(context,bean.getPic(),headIv,null,true,R.mipmap.default_pic,R.mipmap.default_pic);
            nameTv.setText(bean.getNickname());
            CommonUtils.setTextViewType(context,bean.getFollowed(),attentionTv,R.string.attention,R.string.attentioned);
            attentionTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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



    private void isFollow(final NewFriendsBean bean) {
        FollowApi api = new FollowApi();
        api.uid = api.getUserId(context);
        api.mid = bean.getId();
        HttpClient.newInstance(context).loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                if (StringUtils.isSame(CommonUtils.isZero,bean.getFollowed())) {//0|1  未关注|已关注
                    bean.setFollowed(CommonUtils.isOne);
                } else {
                    bean.setFollowed(CommonUtils.isZero);
                }
                UIHelper.ToastMessage(context, response.getInfo());
                CommonUtils.loadUserInfo(context, null);
                EventBus.getDefault().post(new AttentionFriendEvent(bean.getId(),bean.getFollowed()));//通知好友列表改变该好友的关注状态
                notifyDataSetChanged();
            }
        });
    }


}
