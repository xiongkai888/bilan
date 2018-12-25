package com.lanmei.bilan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lanmei.bilan.R;
import com.lanmei.bilan.bean.SheQuQunBean;
import com.xson.common.adapter.SwipeRefreshAdapter;

import butterknife.ButterKnife;


/**
 * 快讯-快讯-子项
 */
public class HomeKuaiSubAdapter extends SwipeRefreshAdapter<SheQuQunBean> {


    public HomeKuaiSubAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_home_kuai_sub, parent, false));
    }

    @Override
    public void onBindViewHolder2(RecyclerView.ViewHolder holder, int position) {
//        final SheQuQunBean bean = getItem(position);
//        if (StringUtils.isEmpty(bean)) {
//            return;
//        }
//        ViewHolder viewHolder = (ViewHolder) holder;
//        viewHolder.nameTv.setText(String.format(context.getString(R.string.she_name_num),bean.getName(),bean.getAffiliations_count()+""));
//        ImageHelper.load(context,bean.getImg(),viewHolder.imageViev,null,true,R.mipmap.default_pic,R.mipmap.default_pic);
//        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!CommonUtils.isLogin(context)){
//                    return;
//                }
//                final String groupid = bean.getRoomid();
//                List<EMGroup> grouplist = EMClient.getInstance().groupManager().getAllGroups();
//                for (EMGroup group : grouplist) {
//                    if (StringUtils.isSame(groupid,group.getGroupId())){
//                        CommonUtils.startChatActivity(context,groupid,true);
//                        return;
//                    }
//                }
//            }
//        });

        ViewHolder viewHolder = (ViewHolder) holder;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);

        }

        public void setParameter(){

        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
