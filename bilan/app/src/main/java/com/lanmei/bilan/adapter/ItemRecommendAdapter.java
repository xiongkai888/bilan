package com.lanmei.bilan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.lanmei.bilan.R;
import com.lanmei.bilan.bean.RecommendGoodsBean;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * item页底部的推荐商品适配器
 */
public class ItemRecommendAdapter implements Holder<List<RecommendGoodsBean>> {

    @InjectView(R.id.recommend_gridView)
    GridView gridView;

    @Override
    public View createView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recommend, null);
        ButterKnife.inject(this,view);
        return view;
    }

    @Override
    public void UpdateUI(Context context, int position, List<RecommendGoodsBean> data) {
        gridView.setAdapter(new ItemRecommendGoodsAdapter(context, data));
    }
}
