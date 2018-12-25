package com.lanmei.bilan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lanmei.bilan.R;
import com.lanmei.bilan.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xkai on 2017/7/5.
 * 充值金额
 */

public class RechargeAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mList;
    private LayoutInflater inflater;
    public RechargeAdapter(Context context){
        mList =  new ArrayList<>();
        mList.add("500元");
        mList.add("1000元");
        mList.add("1500元");
        mList.add("2000元");
        mList.add("2500元");
        mList.add("3000元");
        mList.add(CommonUtils.OTHER_MONEY);

        mContext = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList == null?0:mList.size();
    }

    @Override
    public String getItem(int position) {
        if (mList == null){
            return "";
        }
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_recharge, parent, false);
            holder = new ItemHolder();
            holder.textView = (TextView) convertView;
            convertView.setTag(holder);
        } else {
            holder = (ItemHolder) convertView.getTag();
        }
        holder.textView.setText(getItem(position));
        return convertView;
    }

    public static class ItemHolder {
        TextView textView;
    }

}
