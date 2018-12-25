package com.lanmei.bilan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lanmei.bilan.R;
import com.lanmei.bilan.bean.RecommendGoodsBean;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * item页底部推荐商品适配器
 */
public class ItemRecommendGoodsAdapter extends BaseAdapter {

    private Context context = null;
    private List<RecommendGoodsBean> data = null;

    public ItemRecommendGoodsAdapter(Context context, List<RecommendGoodsBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_recommend_goods, null);
            holder = new ViewHolder(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        initItemView(position, holder);
        return convertView;
    }

    private void initItemView(int position, ViewHolder holder) {
        RecommendGoodsBean bean = data.get(position);
        Glide.with(context).load(bean.imag).error(R.mipmap.default_pic).into(holder.itemGoodsImage);
        holder.tvGoodsName.setText(bean.title);
        holder.tvGoodsPrice.setText("￥" + bean.currentPrice);
        holder.tvGoodsOldPrice.setText("￥" + bean.price);
    }

    class ViewHolder {
        @InjectView(R.id.item_goods_image)
        ImageView itemGoodsImage;
        @InjectView(R.id.tv_goods_name)
        TextView tvGoodsName;
        @InjectView(R.id.tv_goods_price)
        TextView tvGoodsPrice;
        @InjectView(R.id.tv_goods_old_price)
        TextView tvGoodsOldPrice;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
            view.setTag(this);
        }
    }
}
