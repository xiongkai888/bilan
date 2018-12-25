package com.lanmei.bilan.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lanmei.bilan.R;
import com.lanmei.bilan.bean.MineOrderBean;
import com.lanmei.bilan.helper.OrderDetailsItemHelper;
import com.lanmei.bilan.ui.mine.activity.OrderDetailsActivity;
import com.lanmei.bilan.ui.mine.activity.PublishCommentActivity;
import com.lanmei.bilan.utils.AKDialog;
import com.lanmei.bilan.utils.CommonUtils;
import com.lanmei.bilan.utils.SharedAccount;
import com.xson.common.adapter.SwipeRefreshAdapter;
import com.xson.common.utils.IntentUtil;
import com.xson.common.utils.L;
import com.xson.common.utils.StringUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * 我的订单 全部
 */
public class MineOrderSubAdapter extends SwipeRefreshAdapter<MineOrderBean> {

    public MineOrderSubAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_order, parent, false));
    }

    @Override
    public void onBindViewHolder2(RecyclerView.ViewHolder holder, final int position) {
        final MineOrderBean bean = getItem(position);
        if (StringUtils.isEmpty(bean)) {
            return;
        }
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setParameter(bean, position);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("bean", bean);
                IntentUtil.startActivity(context, OrderDetailsActivity.class, bundle);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.pay_no_tv)
        TextView payNoTv;
        @InjectView(R.id.pay_status_tv)
        TextView payStatusTv;
        @InjectView(R.id.ll_item_goods)
        LinearLayout llItemGoods;
        @InjectView(R.id.total_goods_num_tv)
        TextView totalGoodsNumTv;
        @InjectView(R.id.total_price_tv)
        TextView totalPriceTv;
        @InjectView(R.id.contact_tv)
        TextView contactTv;
        @InjectView(R.id.refund_tv)
        TextView refundTv;
        @InjectView(R.id.affirm_tv)
        TextView affirmTv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }

        public void setParameter(final MineOrderBean bean, int position) {
            L.d("ScanActivity",""+bean.getReviews());
            contactTv.setVisibility(View.VISIBLE);
            refundTv.setVisibility(View.VISIBLE);
            affirmTv.setVisibility(View.VISIBLE);
            contactTv.setText("联系商家");
            final String oid = bean.getId();//订单id
            payNoTv.setText(String.format(context.getString(R.string.order_num), bean.getPay_no()));

            llItemGoods.removeAllViews();
            List<MineOrderBean.ProductBean> list = bean.getProduct();
            if (!StringUtils.isEmpty(list)) {
                new OrderDetailsItemHelper(context, llItemGoods, list).show();
            }
            totalGoodsNumTv.setText(String.format(context.getString(R.string.goods_num), bean.getOrder_num() + ""));
            totalPriceTv.setText(String.format(context.getString(R.string.price_sub), bean.getOrder_amount() + ""));

            final String status = bean.getStatus();// 1|2|3|4|5|6 => 1生成订单|2确认订单|3取消订单|4作废订单|5完成订单|6申请退款
            final String paysStatus = bean.getPay_status();//支付状态 0：未支付，1：已支付，2：退款',
            L.d("MineOrderSubAdapter", "position" + position + ": status = " + status + ",paysStatus = " + paysStatus);

            switch (paysStatus) {
                case "0"://未支付
                    refundTv.setText("取消订单");
                    affirmTv.setText("去付款");

                    payStatusTv.setText("待付款");
                    break;
                case "1"://已支付
                    payStatusTv.setText("待收货");
                    switch (status) {
                        case "1":
                        case "2":
                            payStatusTv.setText("待收货");
                            refundTv.setText("退款");
                            affirmTv.setText("确认收货");
                            break;
                        case "3":
                            payStatusTv.setText("交易关闭");
                            refundTv.setVisibility(View.GONE);
                            affirmTv.setVisibility(View.GONE);
                            break;
                        case "4":
                            payStatusTv.setText("交易关闭");
                            refundTv.setVisibility(View.GONE);
                            affirmTv.setVisibility(View.GONE);
                            contactTv.setText("删除订单");
                            break;
                        case "5":
                            payStatusTv.setText("交易完成");
                            refundTv.setText("删除订单");
                            if (StringUtils.isSame(CommonUtils.isZero,bean.getReviews())){
                                affirmTv.setText("晒单评价");
                            }else {
                                affirmTv.setVisibility(View.GONE);
                            }
                            break;
                        case "6":// 1|2|3|4|5|6 => 1生成订单|2确认订单|3取消订单|4作废订单|5完成订单|6申请退款
                            payStatusTv.setText("退款中");
                            refundTv.setVisibility(View.GONE);
                            affirmTv.setText("退款中");
                            break;
                    }

                    break;
                case "2"://退款
                    refundTv.setVisibility(View.GONE);
                    affirmTv.setText("退款中");

                    payStatusTv.setText("退款中");
                    break;
            }

            contactTv.setOnClickListener(new View.OnClickListener() {//联系商家
                @Override
                public void onClick(View view) {
                    switch (paysStatus) {
                        case "1"://联系商家
                            if (StringUtils.isSame(status,"4")){
                                AKDialog.getAlertDialog(context, "确认要删除该订单？", new AKDialog.AlertDialogListener() {
                                    @Override
                                    public void yes() {
                                        if (l != null) {
                                            l.affirm("4", oid);
                                        }
                                    }
                                });
                                break;
                            }
                        case "0"://联系商家
                        case "2"://联系商家
                            CommonUtils.startChatActivity(context, SharedAccount.getInstance(context).getServiceId(), false);
                            break;
                    }
                }
            });
            refundTv.setOnClickListener(new View.OnClickListener() {//退款
                @Override
                public void onClick(View view) {
                    switch (paysStatus) {
                        case "0"://
                            AKDialog.getAlertDialog(context, "确认要取消订单？", new AKDialog.AlertDialogListener() {
                                @Override
                                public void yes() {
                                    if (l != null) {
                                        l.affirm("3", oid);
                                    }
                                }
                            });

                        case "1"://
                            switch (status) {//状态值 1|2|3|4|5|6 =>生成订单|确认订单|取消订单|作废订单|完成订单|申请退款
                                case "1":
                                case "2":
                                    AKDialog.getAlertDialog(context, "确认要退款？", new AKDialog.AlertDialogListener() {
                                        @Override
                                        public void yes() {
                                            if (l != null) {
                                                l.affirm("6", oid);
                                            }
                                        }
                                    });
                                    break;
                                case "5":
                                    AKDialog.getAlertDialog(context, "确认要删除该订单？", new AKDialog.AlertDialogListener() {
                                        @Override
                                        public void yes() {
                                            if (l != null) {
                                                l.affirm("4", oid);
                                            }
                                        }
                                    });
                                    break;
                            }
                            break;
                    }
                }
            });
            affirmTv.setOnClickListener(new View.OnClickListener() {//确认收货
                @Override
                public void onClick(View view) {
                    switch (paysStatus) {
                        case "0"://
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("bean", bean);
                            IntentUtil.startActivity(context, OrderDetailsActivity.class, bundle);//去付款

                            break;
                        case "1"://
                            switch (status) {
                                case "1":
                                case "2":
                                    AKDialog.getAlertDialog(context, "确认要收货？", new AKDialog.AlertDialogListener() {
                                        @Override
                                        public void yes() {
                                            if (l != null) {
                                                l.affirm("5", oid);
                                            }
                                        }
                                    });
                                    break;
                                case "3":
                                    break;
                                case "4":
                                    break;
                                case "5"://去评论
                                    Bundle bundle1 = new Bundle();
                                    bundle1.putSerializable("bean", bean);
                                    IntentUtil.startActivity(context, PublishCommentActivity.class,bundle1);
                                    break;
                            }
                            break;
                    }
                }
            });
        }
    }

    OrderAlterListener l;

    public interface OrderAlterListener {
        void affirm(String status, String oid);
    }

    public void setOrderAlterListener(OrderAlterListener l) {
        this.l = l;
    }

}

