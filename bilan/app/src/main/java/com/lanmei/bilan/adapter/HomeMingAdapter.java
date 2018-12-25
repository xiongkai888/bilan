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
import com.lanmei.bilan.bean.HomeMingBean;
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
 * 名人堂
 */
public class HomeMingAdapter extends SwipeRefreshAdapter<HomeMingBean> {


    public HomeMingAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_mine_fans, parent, false));
    }

    @Override
    public void onBindViewHolder2(RecyclerView.ViewHolder holder, int position) {
        final HomeMingBean bean = getItem(position);
        if (StringUtils.isEmpty(bean)) {
            return;
        }
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setParameter(bean);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!StringUtils.isSame(bean.getId(), CommonUtils.getUserId(context))) {
                    Bundle bundle = new Bundle();
                    bundle.putString("uid",bean.getId());
                    IntentUtil.startActivity(context, DynamicFriendsActivity.class, bundle);
                }
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

        public void setParameter(final HomeMingBean bean) {
            final int judge = bean.getJudge();
//            if (judge == 0){//0|1  未关注|已关注
//                attentionTv.setText(R.string.attention);
//            }else {
//                attentionTv.setText(R.string.attentioned);
//            }
            ImageHelper.load(context,bean.getPic(),headIv,null,true,R.mipmap.default_pic,R.mipmap.default_pic);
            nameTv.setText(bean.getNickname());
            CommonUtils.setTextViewType(context,judge+"",attentionTv,R.string.attention,R.string.attentioned);
            attentionTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!CommonUtils.isLogin(context)){
                        return;
                    }
                    String str = "";
                    if (judge == 0){//0|1  未关注|已关注
                        str = context.getString(R.string.is_follow_ming);
                    }else {
                        str = context.getString(R.string.is_no_follow_ming);
                    }
                    AKDialog.getAlertDialog(context, str , new AKDialog.AlertDialogListener() {
                        @Override
                        public void yes() {
                            isFollowMing(bean);
                        }
                    });
                }
            });

        }
    }

    private void isFollowMing(final HomeMingBean bean) {
        FollowApi api = new FollowApi();
        api.uid = api.getUserId(context);
        api.mid = bean.getId();
        api.type = "2";//0|1|2=>普通会员|公众号|名人堂
        HttpClient.newInstance(context).loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                if (context == null){
                    return;
                }
                int judge = bean.getJudge();
                if (judge == 0){//0|1  未关注|已关注
                    bean.setJudge(1);
                }else {
                    bean.setJudge(0);
                }
                UIHelper.ToastMessage(context,response.getInfo());
                CommonUtils.loadUserInfo(context,null);
                EventBus.getDefault().post(new AttentionFriendEvent(bean.getId(),bean.getJudge()+""));//该uid都设置为关注或不关注
//                notifyDataSetChanged();
            }
        });
    }
}
