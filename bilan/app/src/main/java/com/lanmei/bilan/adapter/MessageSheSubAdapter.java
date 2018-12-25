package com.lanmei.bilan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.lanmei.bilan.R;
import com.lanmei.bilan.bean.SheQuQunBean;
import com.lanmei.bilan.monitor.JoinGroupListener;
import com.lanmei.bilan.utils.AKDialog;
import com.lanmei.bilan.utils.CommonUtils;
import com.xson.common.adapter.SwipeRefreshAdapter;
import com.xson.common.helper.ImageHelper;
import com.xson.common.utils.StringUtils;
import com.xson.common.widget.CircleImageView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * 消息中的社区item的列表
 */
public class MessageSheSubAdapter extends SwipeRefreshAdapter<SheQuQunBean> {


    public MessageSheSubAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_message_she_sub, parent, false));
    }

    @Override
    public void onBindViewHolder2(RecyclerView.ViewHolder holder, int position) {
        final SheQuQunBean bean = getItem(position);
        if (StringUtils.isEmpty(bean)) {
            return;
        }
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.nameTv.setText(String.format(context.getString(R.string.she_name_num),bean.getName(),bean.getAffiliations_count()+""));
        ImageHelper.load(context,bean.getImg(),viewHolder.imageViev,null,true,R.mipmap.default_pic,R.mipmap.default_pic);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CommonUtils.isLogin(context)){
                    return;
                }
                final String groupid = bean.getRoomid();
                List<EMGroup> grouplist = EMClient.getInstance().groupManager().getAllGroups();
                for (EMGroup group : grouplist) {
                    if (StringUtils.isSame(groupid,group.getGroupId())){
                        CommonUtils.startChatActivity(context,groupid,true);
                        return;
                    }
                }

                AKDialog.getAlertDialog(context, context.getString(R.string.isJoinGroup) , new AKDialog.AlertDialogListener() {
                    @Override
                    public void yes() {
                        if (l != null){
                            l.joinGroup(groupid);
                        }
                    }
                });
            }
        });
    }

    //加入群监听
    JoinGroupListener l;

    public void setJoinGroupListener(JoinGroupListener l){
        this.l = l;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.name_tv)
        TextView nameTv;
        @InjectView(R.id.user_head_iv)
        CircleImageView imageViev;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);

        }
    }

}
