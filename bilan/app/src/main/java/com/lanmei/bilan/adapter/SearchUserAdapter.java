package com.lanmei.bilan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lanmei.bilan.R;
import com.lanmei.bilan.api.AddFriendsApi;
import com.lanmei.bilan.api.FollowApi;
import com.lanmei.bilan.bean.SearchUserBean;
import com.lanmei.bilan.event.AddFriendsEvent;
import com.lanmei.bilan.event.AttentionFriendEvent;
import com.lanmei.bilan.utils.AKDialog;
import com.lanmei.bilan.utils.CommonUtils;
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
 * 搜索好友
 */
public class SearchUserAdapter extends SwipeRefreshAdapter<SearchUserBean> {


    public SearchUserAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_two_botton, parent, false));
    }

    @Override
    public void onBindViewHolder2(final RecyclerView.ViewHolder holder, int position) {
        SearchUserBean bean = getItem(position);
        if (bean == null) {
            return;
        }
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setParameter(bean);
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

        public void setParameter(final SearchUserBean bean){
            ImageHelper.load(context,bean.getPic(),headIv,null,true,R.mipmap.default_pic,R.mipmap.default_pic);
            nameTv.setText(bean.getNickname());
            if (!CommonUtils.isSelf(context,bean.getId())){
                addFriendsTv.setVisibility(View.VISIBLE);
                attentionTv.setVisibility(View.VISIBLE);
                CommonUtils.setTextViewType(context,bean.getFollowed(),attentionTv,R.string.attention,R.string.attentioned);
                attentionTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String str = "";
                        if (StringUtils.isSame(CommonUtils.isZero,bean.getFollowed())){//0|1  未关注|已关注
                            str = context.getString(R.string.is_follow);
                        }else {
                            str = context.getString(R.string.is_no_follow);
                        }
                        AKDialog.getAlertDialog(context, str , new AKDialog.AlertDialogListener() {
                            @Override
                            public void yes() {
                                isFollow(bean);
                            }
                        });
                    }
                });
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
            }else {
                addFriendsTv.setVisibility(View.GONE);
                attentionTv.setVisibility(View.GONE);
            }
        }
    }


    private void isAddFriend(final SearchUserBean bean) {
        AddFriendsApi api = new AddFriendsApi();
        api.mid = bean.getId();
        api.uid = api.getUserId(context);
        HttpClient.newInstance(context).loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                bean.setIs_friend(1);
                UIHelper.ToastMessage(context, response.getInfo());
                EventBus.getDefault().post(new AddFriendsEvent());//在好友界面点击进入搜索好友时需刷新好友列表
                notifyDataSetChanged();
            }
        });
    }

    private void isFollow(final SearchUserBean bean) {
        FollowApi api = new FollowApi();
        api.uid = api.getUserId(context);
        api.mid = bean.getId();
        HttpClient.newInstance(context).loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                if (StringUtils.isSame(CommonUtils.isZero,bean.getFollowed())){//0|1  未关注|已关注
                    bean.setFollowed(CommonUtils.isOne);
                }else {
                    bean.setFollowed(CommonUtils.isZero);
                }
                UIHelper.ToastMessage(context,response.getInfo());
                EventBus.getDefault().post(new AttentionFriendEvent(bean.getId(),bean.getFollowed()));
                CommonUtils.loadUserInfo(context,null);
                notifyDataSetChanged();
            }
        });
    }

}
