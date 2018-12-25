package com.lanmei.bilan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lanmei.bilan.R;
import com.lanmei.bilan.bean.HomeQuBean;
import com.lanmei.bilan.utils.CommonUtils;
import com.xson.common.adapter.SwipeRefreshAdapter;
import com.xson.common.utils.UIHelper;

import butterknife.ButterKnife;


/**
 * 通讯录
 */
public class AddressBookSubAdapter extends SwipeRefreshAdapter<HomeQuBean> {


    public AddressBookSubAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_address_book, parent, false));
    }

    @Override
    public void onBindViewHolder2(RecyclerView.ViewHolder holder, int position) {

        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setParameter(position);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIHelper.ToastMessage(context, R.string.developing);
//                NewsDetailsActivity.startActivityNewsDetails(context, bean);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);

        }

        public void setParameter(int position) {

        }
    }

    @Override
    public int getCount() {
        return CommonUtils.quantity;
    }
}
