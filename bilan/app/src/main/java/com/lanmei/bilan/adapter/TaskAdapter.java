package com.lanmei.bilan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lanmei.bilan.R;
import com.lanmei.bilan.bean.TaskBean;
import com.xson.common.adapter.SwipeRefreshAdapter;
import com.xson.common.utils.StringUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * 任务
 */
public class TaskAdapter extends SwipeRefreshAdapter<TaskBean> {

    final public static int TYPE_BANNER = 100;

    public TaskAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {

        if (viewType == TYPE_BANNER) { // banner
            BannerViewHolder bannerHolder = new BannerViewHolder(LayoutInflater.from(context).inflate(R.layout.head_my, parent, false));
            return bannerHolder;
        }
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_my, parent, false));
    }

    public static class BannerViewHolder extends RecyclerView.ViewHolder {


        BannerViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }

    @Override
    public int getCount() {
        return super.getCount() + 1;
    }

    @Override
    public int getItemViewType2(int position) {
        if (position == 0) {
            return TYPE_BANNER;
        }
        return super.getItemViewType2(position);
    }

    @Override
    public void onBindViewHolder2(RecyclerView.ViewHolder holder, final int position) {

        if (getItemViewType(position) == TYPE_BANNER) {
            return;
        }

        TaskBean bean = getItem(position - 1);
        if (StringUtils.isEmpty(bean)) {
            return;
        }
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setParameter(bean, position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.title_tv)
        TextView titleTv;
        @InjectView(R.id.content_tv)
        TextView contentTv;
        @InjectView(R.id.button_tv)
        TextView buttonTv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);

        }

        public void setParameter(final TaskBean bean, int position) {
            buttonTv.setOnClickListener(null);
            String title = bean.getTitle();
            String done = "";
            if (bean.getStatus() == 0){
                String s = title+"  <font color=\"#FF0000\">未完成</font>";
                titleTv.setText(Html.fromHtml(s));
                if (!StringUtils.isEmpty(title) && title.length() >= 2) {
                    done = title.substring(0,2);
                }
                buttonTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener !=  null){
                            listener.onClick(bean);
                        }
                    }
                });
            }else {
                titleTv.setText(title);
                if (!StringUtils.isEmpty(title) && title.length() >= 2) {
                    done = "已"+title.substring(0,2);
                }
            }
            buttonTv.setText(done);
            contentTv.setText(bean.getSubtitle());
        }
    }


    OnButtonClickListener listener;

   public interface OnButtonClickListener{
        void onClick(TaskBean bean);
   }

   public void setOnButtonClickListener(OnButtonClickListener l){
       listener = l;
   }

}
