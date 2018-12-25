package com.lanmei.bilan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lanmei.bilan.R;
import com.lanmei.bilan.api.FollowApi;
import com.lanmei.bilan.bean.HomeGongBean;
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
 * 公众号
 */
public class HomeGongAdapter extends SwipeRefreshAdapter<HomeGongBean> {


    public HomeGongAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_home_gong, parent, false));
    }

    @Override
    public void onBindViewHolder2(RecyclerView.ViewHolder holder, int position) {
        final HomeGongBean bean = getItem(position);
        if (StringUtils.isEmpty(bean)){
            return;
        }
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setParameter(bean);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!StringUtils.isSame(bean.getId(), CommonUtils.getUserId(context))) {
                    IntentUtil.startActivity(context, DynamicFriendsActivity.class, bean.getId());
                }
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.head_iv)
        CircleImageView headIv;
        @InjectView(R.id.name_tv)
        TextView nameTv;
        @InjectView(R.id.content_tv)
        TextView contentTv;
        @InjectView(R.id.attention_tv)
        TextView attentionTv;
        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);

        }

        public void setParameter(final HomeGongBean bean) {
            final int judge = bean.getJudge();
            if (judge == 0){//0|1  未关注|已关注
                attentionTv.setText(R.string.attention);
            }else {
                attentionTv.setText(R.string.attentioned);
            }
            ImageHelper.load(context,bean.getPic(),headIv,null,true,R.mipmap.default_pic,R.mipmap.default_pic);
            nameTv.setText(bean.getNickname());
            contentTv.setText(bean.getSignature());
            attentionTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!CommonUtils.isLogin(context)){
                        return;
                    }
                    String str = "";
                    if (judge == 0){//0|1  未关注|已关注
                        str = context.getString(R.string.is_follow_gong);
                    }else {
                        str = context.getString(R.string.is_no_follow_gong);
                    }
                    AKDialog.getAlertDialog(context, str , new AKDialog.AlertDialogListener() {
                        @Override
                        public void yes() {
                            isFollowGong(bean);
                        }
                    });
                }
            });
        }
    }

    private void isFollowGong(final HomeGongBean bean) {
        FollowApi api = new FollowApi();
        api.uid = api.getUserId(context);
        api.mid = bean.getId();
        api.type = "1";//0|1|2=>普通会员|公众号|名人堂
        HttpClient.newInstance(context).loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                int judge = bean.getJudge();
                if (judge == 0){//0|1  未关注|已关注
                    bean.setJudge(1);
                }else {
                    bean.setJudge(0);
                }
                UIHelper.ToastMessage(context,response.getInfo());
                CommonUtils.loadUserInfo(context,null);
                notifyDataSetChanged();
            }
        });
    }
}
