package com.lanmei.bilan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanmei.bilan.R;
import com.lanmei.bilan.bean.ContactsBean;

import java.util.List;

/**
 * Created by xkai on 2018/1/4.
 * 通讯录好友
 */

public class AddressBookAdapter extends BaseAdapter{

    private Context mContext;
    private List<ContactsBean> mList;
    private LayoutInflater inflater;
    public AddressBookAdapter(Context context, List<ContactsBean> list){
        mContext = context;
        mList = list;
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<ContactsBean> list){
        mList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList == null?0:mList.size();
    }

    @Override
    public ContactsBean getItem(int position) {
        if (mList == null){
            return null;
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
            holder = new ItemHolder();
            convertView = inflater.inflate(R.layout.item_address_book, parent, false);
            holder.headIv  = (ImageView) convertView.findViewById(R.id.head_iv);
            holder.name  = (TextView) convertView.findViewById(R.id.name_tv);
            holder.recommend  = (TextView) convertView.findViewById(R.id.recommend_tv);
            convertView.setTag(holder);
        } else {
            holder = (ItemHolder) convertView.getTag();
        }
        ContactsBean bean = getItem(position);
        if (bean != null){
            holder.name.setText(bean.getContactsName());
            holder.headIv.setImageBitmap(bean.getContactsPhonto());
        }
        holder.recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRecommendListener != null){
                    mRecommendListener.recommend();
                }
            }
        });
        return convertView;
    }

    RecommendListener mRecommendListener;//推荐监听

    public void setRecommendListener(RecommendListener l){
        mRecommendListener = l;
    }

    public interface RecommendListener{
        void recommend();
    }

    public static class ItemHolder {
        TextView name;
        TextView recommend;
        ImageView headIv;
    }

}
