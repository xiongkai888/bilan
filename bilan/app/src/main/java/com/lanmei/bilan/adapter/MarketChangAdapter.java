package com.lanmei.bilan.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.chatuidemo.Constant;
import com.hyphenate.chatuidemo.ui.ChatActivity;
import com.lanmei.bilan.R;
import com.lanmei.bilan.bean.MarketChangBean;
import com.lanmei.bilan.utils.CommonUtils;
import com.lanmei.bilan.utils.SharedAccount;
import com.xson.common.adapter.SwipeRefreshAdapter;
import com.xson.common.utils.StringUtils;
import com.xson.common.widget.CircleImageView;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * 行情--币种信息
 */
public class MarketChangAdapter extends SwipeRefreshAdapter<MarketChangBean> {


    public MarketChangAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_market_chang, parent, false));
    }

    @Override
    public void onBindViewHolder2(RecyclerView.ViewHolder holder, int position) {
        MarketChangBean bean = getItem(position);
        if (StringUtils.isEmpty(bean)){
            return;
        }
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setParameter(bean);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.cudo_iv)
        CircleImageView cudoIv;
        @InjectView(R.id.classname_tv)
        TextView classnameTv;
        @InjectView(R.id.num_tv)
        TextView numTv;
        @InjectView(R.id.price_tv)
        TextView priceTv;
        @InjectView(R.id.message_iv)
        ImageView messageIv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);

        }

        public void setParameter(final MarketChangBean bean) {
            if ("1".equals(bean.getCudo())){
                cudoIv.setImageResource(R.mipmap.icon_chang_in);
            }else {
                cudoIv.setImageResource(R.mipmap.icon_chang_out);
            }
            classnameTv.setText(bean.getClassname());
            numTv.setText(String.format(context.getString(R.string.num),bean.getNum()));
            priceTv.setText(String.format(context.getString(R.string.price),bean.getPrice()));
            messageIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!CommonUtils.isLogin(context)){
                        return;
                    }
                    Intent intent = new Intent(context, ChatActivity.class);
                    intent.putExtra("isOnLine",true);
                    intent.putExtra("bean",bean);
                    intent.putExtra(Constant.EXTRA_USER_ID, SharedAccount.getInstance(context).getServiceId());
                    context.startActivity(intent);
//                    CommonUtils.startChatActivity(context,bean.getId());
                }
            });
        }
    }
}
