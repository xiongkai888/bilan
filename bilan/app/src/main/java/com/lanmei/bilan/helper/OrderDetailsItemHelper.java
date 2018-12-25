package com.lanmei.bilan.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lanmei.bilan.R;
import com.lanmei.bilan.bean.MineOrderBean;
import com.xson.common.helper.ImageHelper;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by xkai on 2018/1/23.
 * 订单详情或我的订单列表item商品显示
 */

public class OrderDetailsItemHelper {
    LinearLayout root;
    Context context;
    List<MineOrderBean.ProductBean> list;
    public OrderDetailsItemHelper(Context context,LinearLayout root, List<MineOrderBean.ProductBean> list){
        this.root = root;
        this.list = list;
        this.context = context;
    }

    public void show(){
        for (MineOrderBean.ProductBean productBean:list){
            addView(root,productBean);
        }
    }

    private void addView(LinearLayout root, MineOrderBean.ProductBean bean) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_discover_two, null);
        root.addView(view);
        new GoodsViewViewHolder(view, bean);
    }

    public class GoodsViewViewHolder {

        @InjectView(R.id.image)
        ImageView image;
        @InjectView(R.id.content_tv)
        TextView contentTv;
        @InjectView(R.id.price_tv)
        TextView priceTv;
        @InjectView(R.id.goods_num_tv)
        TextView goodsNumTv;

        public GoodsViewViewHolder(View view, MineOrderBean.ProductBean bean) {
            ButterKnife.inject(this, view);
            ImageHelper.load(context,bean.getImg(),image,null,true, R.mipmap.default_pic,R.mipmap.default_pic);
            contentTv.setText(bean.getName());
            priceTv.setText(String.format(context.getString(R.string.price_sub),bean.getSell_price()));
            goodsNumTv.setText(bean.getGoods_nums()+context.getString(R.string.jian));
        }

    }
}
