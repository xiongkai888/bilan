package com.lanmei.bilan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanmei.bilan.R;
import com.lanmei.bilan.bean.MinePublishBean;
import com.xson.common.adapter.SwipeRefreshAdapter;
import com.xson.common.utils.StringUtils;
import com.xson.common.utils.UIHelper;
import com.xson.common.widget.CircleImageView;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * 我的发布
 */
public class MinePulishAdapter extends SwipeRefreshAdapter<MinePublishBean> {

    public MinePulishAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_market_chang, parent, false));
    }

    @Override
    public void onBindViewHolder2(RecyclerView.ViewHolder holder, final int position) {
        final MinePublishBean bean = getItem(position);
        if (StringUtils.isEmpty(bean)){
            return;
        }
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setParameter(bean,position);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIHelper.ToastMessage(context,R.string.developing);
            }
        });
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

        public void setParameter(final MinePublishBean bean, final int position) {
            String cudo = bean.getCudo();
            if ("1".equals(cudo)){//                    1|0=>进|出
                cudoIv.setImageResource(R.mipmap.icon_chang_in);
            }else {
                cudoIv.setImageResource(R.mipmap.icon_chang_out);
            }
            classnameTv.setText(bean.getClassname());
            numTv.setText(String.format(context.getString(R.string.num),bean.getNum()));
            priceTv.setText(String.format(context.getString(R.string.price),bean.getPrice()));
            messageIv.setVisibility(View.GONE);
        }
    }
}
