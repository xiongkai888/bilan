package com.lanmei.bilan.ui.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lanmei.bilan.R;
import com.lanmei.bilan.api.OrderAlterStatusApi;
import com.lanmei.bilan.api.PaytwoApi;
import com.lanmei.bilan.bean.MineOrderBean;
import com.lanmei.bilan.event.CommentEvent;
import com.lanmei.bilan.event.OrderDetailsEvent;
import com.lanmei.bilan.helper.OrderDetailsItemHelper;
import com.lanmei.bilan.utils.AKDialog;
import com.lanmei.bilan.utils.CommonUtils;
import com.lanmei.bilan.utils.SharedAccount;
import com.xson.common.app.BaseActivity;
import com.xson.common.bean.BaseBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.utils.IntentUtil;
import com.xson.common.utils.L;
import com.xson.common.utils.StringUtils;
import com.xson.common.utils.UIHelper;
import com.xson.common.widget.CenterTitleToolbar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.InjectView;

/**
 * 订单详情
 */
public class OrderDetailsActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    CenterTitleToolbar mToolbar;
    @InjectView(R.id.name_tv)
    TextView nameTv;
    @InjectView(R.id.address_tv)
    TextView addressTv;
    @InjectView(R.id.num_tv)
    TextView numTv;
    @InjectView(R.id.fee_tv)
    TextView feeTv;
    @InjectView(R.id.goods_price_tv)
    TextView goodsPriceTv;
    @InjectView(R.id.total_price_tv)
    TextView totalPriceTv;
    @InjectView(R.id.pay_way_tv)
    TextView payWayTv;//支付方式
    @InjectView(R.id.ll_item_goods)
    LinearLayout llItemGoods;
    MineOrderBean bean;//我的订单item信息
    @InjectView(R.id.image)
    ImageView image;
    @InjectView(R.id.content_tv)
    TextView contentTv;
    @InjectView(R.id.price_tv)
    TextView priceTv;
    @InjectView(R.id.goods_num_tv)
    TextView goodsNumTv;
    @InjectView(R.id.contact_tv)
    TextView contactTv;
    @InjectView(R.id.refund_tv)
    TextView refundTv;
    @InjectView(R.id.affirm_tv)
    TextView affirmTv;

    @Override
    public int getContentViewId() {
        return R.layout.activity_order_details;
    }

    @Override
    public void initIntent(Intent intent) {
        super.initIntent(intent);
        Bundle bundle = intent.getBundleExtra("bundle");
        if (!StringUtils.isEmpty(bundle)) {
            bean = (MineOrderBean) bundle.getSerializable("bean");
        }
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        setSupportActionBar(mToolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle(R.string.order_details);
        actionbar.setHomeAsUpIndicator(R.mipmap.back);
        setData();
//        OrderDetailsApi api = new OrderDetailsApi();
//        api.oid = bean.getId();
//        api.uid = api.getUserId(this);
//        HttpClient.newInstance(this).loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
//            @Override
//            public void onResponse(BaseBean response) {
//
//            }
//        });

        EventBus.getDefault().register(this);

    }


    String proId;
    String oid;

    private void setData() {
        if (StringUtils.isEmpty(bean)) {
            return;
        }
        oid = bean.getId();
        llItemGoods.removeAllViews();
        nameTv.setText(bean.getUname() + "\u3000" + bean.getUphone());
        addressTv.setText(bean.getUarea());
        List<MineOrderBean.ProductBean> list = bean.getProduct();
        proId = bean.getPro_id();
        new OrderDetailsItemHelper(this, llItemGoods, list).show();
        L.d("proId", proId);
        numTv.setText(bean.getOrder_num() + "件");
        //        feeTv.setText(String.format(getString(R.string.price_sub), bean.getFee()));
        String sellPrice = bean.getOrder_amount();
        goodsPriceTv.setText(sellPrice);
        totalPriceTv.setText(sellPrice);
        String payType = "";
        switch (bean.getPay_type()) {
            case "1":
                payType = "支付宝支付";
                break;
            case "6":
                payType = "余额支付";
                break;
            case "7":
                payType = "微信支付";
                break;
        }
        payWayTv.setText(payType);

        contactTv.setVisibility(View.VISIBLE);
        refundTv.setVisibility(View.VISIBLE);
        affirmTv.setVisibility(View.VISIBLE);
        contactTv.setText("联系商家");
        final String oid = bean.getId();//订单编号
        final String status = bean.getStatus();// 1|2|3|4|5|6 => 1生成订单|2确认订单|3取消订单|4作废订单|5完成订单|6申请退款
        final String paysStatus = bean.getPay_status();//支付状态 0：未支付，1：已支付，2：退款',
        L.d("MineOrderSubAdapter", "position" + 0 + ": status = " + status + ",paysStatus = " + paysStatus);

        switch (paysStatus) {
            case "0"://未支付
                refundTv.setText("取消订单");
                affirmTv.setText("去付款");

                break;
            case "1"://已支付
                switch (status) {
                    case "1":
                    case "2":
                        refundTv.setText("退款");
                        affirmTv.setText("确认收货");
                        break;
                    case "3":
                        refundTv.setVisibility(View.GONE);
                        affirmTv.setVisibility(View.GONE);
                        break;
                    case "4":
                        refundTv.setVisibility(View.GONE);
                        affirmTv.setVisibility(View.GONE);
                        contactTv.setText("删除订单");
                        break;
                    case "5":
                        refundTv.setText("删除订单");
                        if (StringUtils.isSame(CommonUtils.isZero,bean.getReviews())){
                            affirmTv.setText("晒单评价");
                        }else {
                            affirmTv.setVisibility(View.GONE);
                        }
                        break;
                    case "6":// 1|2|3|4|5|6 => 1生成订单|2确认订单|3取消订单|4作废订单|5完成订单|6申请退款
                        refundTv.setVisibility(View.GONE);
                        affirmTv.setText("退款中");
                        break;
                }

                break;
            case "2"://退款
                refundTv.setVisibility(View.GONE);
                affirmTv.setText("退款中");
                break;
        }
        contactTv.setOnClickListener(new View.OnClickListener() {//联系商家
            @Override
            public void onClick(View view) {
                switch (paysStatus) {
                    case "1"://联系商家
                        if (StringUtils.isSame(status,"4")){
                            AKDialog.getAlertDialog(OrderDetailsActivity.this, "确认要删除该订单？", new AKDialog.AlertDialogListener() {
                                @Override
                                public void yes() {
                                    orderAffirm("4", oid);
                                }
                            });
                            break;
                        }
                    case "0"://联系商家
                    case "2"://联系商家
                        CommonUtils.startChatActivity(OrderDetailsActivity.this, SharedAccount.getInstance(OrderDetailsActivity.this).getServiceId(), false);
                        break;
                }
            }
        });
        refundTv.setOnClickListener(new View.OnClickListener() {//退款
            @Override
            public void onClick(View view) {
                switch (paysStatus) {
                    case "0"://
                        AKDialog.getAlertDialog(OrderDetailsActivity.this, "确认要取消订单？", new AKDialog.AlertDialogListener() {
                            @Override
                            public void yes() {
                                orderAffirm("3", oid);
                            }
                        });

                    case "1"://
                        switch (status) {//状态值 1|2|3|4|5|6 =>生成订单|确认订单|取消订单|作废订单|完成订单|申请退款
                            case "1":
                            case "2":
                                AKDialog.getAlertDialog(OrderDetailsActivity.this, "确认要退款？", new AKDialog.AlertDialogListener() {
                                    @Override
                                    public void yes() {
                                        orderAffirm("6", oid);
                                    }
                                });
                                break;
                            case "5":
                                AKDialog.getAlertDialog(OrderDetailsActivity.this, "确认要删除该订单？", new AKDialog.AlertDialogListener() {
                                    @Override
                                    public void yes() {
                                        orderAffirm("4", oid);
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
                    case "0"://去支付
                        goPay();
                        break;
                    case "1"://
                        switch (status) {
                            case "1":
                            case "2":
                                AKDialog.getAlertDialog(OrderDetailsActivity.this, "确认要收货？", new AKDialog.AlertDialogListener() {
                                    @Override
                                    public void yes() {
                                        orderAffirm("5", oid);
                                    }
                                });
                                break;
                            case "3":
                                break;
                            case "4":
                                break;
                            case "5"://去评论
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("bean", bean);
                                IntentUtil.startActivity(OrderDetailsActivity.this, PublishCommentActivity.class,bundle);
                                break;
                        }
                        break;
                }
            }
        });
    }

    private void goPay() {
        PaytwoApi api = new PaytwoApi();
        api.oid = oid;
        api.uid = api.getUserId(this);
        HttpClient.newInstance(this).loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                int code = response.getCode();
                switch (code) {//1|10|100|404 成功|缺失参数|更改错误|未找到订单
                    case 1:
                        bean.setStatus("1");//1|2|3|4|5|6 => 1生成订单|2确认订单|3取消订单|4作废订单|5完成订单|6申请退款
                        bean.setPay_status("1");
                        setData();
                        EventBus.getDefault().post(new OrderDetailsEvent());//刷新订单列表
                        UIHelper.ToastMessage(OrderDetailsActivity.this, "操作成功");
                        break;
                    case 10:
                        UIHelper.ToastMessage(OrderDetailsActivity.this, "缺失参数");
                        break;
                    case 101:
                        UIHelper.ToastMessage(OrderDetailsActivity.this, "余额不足");
                        break;
                }
            }
        });
    }

    private void orderAffirm(final String status, String oid) {
        OrderAlterStatusApi api = new OrderAlterStatusApi();
        api.oid = oid;
        api.uid = api.getUserId(OrderDetailsActivity.this);
        if (StringUtils.isSame(status,"4")){
            api.if_del = "1";
        }else {
            api.status = status;
        }
        HttpClient.newInstance(OrderDetailsActivity.this).loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                int code = response.getCode();
                switch (code) {//1|10|100|404 成功|缺失参数|更改错误|未找到订单
                    case 1:
                        if (StringUtils.isSame(status,"4")){
                            EventBus.getDefault().post(new OrderDetailsEvent());//刷新订单列表
                            finish();
                            return;
                        }
                        bean.setStatus(status);//1|2|3|4|5|6 => 1生成订单|2确认订单|3取消订单|4作废订单|5完成订单|6申请退款
                        setData();
                        EventBus.getDefault().post(new OrderDetailsEvent());//刷新订单列表
                        UIHelper.ToastMessage(OrderDetailsActivity.this, "操作成功");
                        break;
                    case 10:
                        UIHelper.ToastMessage(OrderDetailsActivity.this, "缺失参数");
                        break;
                    case 100:
                        UIHelper.ToastMessage(OrderDetailsActivity.this, "更改错误");
                        break;
                    case 404:
                        UIHelper.ToastMessage(OrderDetailsActivity.this, "未找到订单");
                        break;
                }
            }
        });
    }

    //订单详情的所有操作完成后调用(评论成功)
    @Subscribe
    public void commentEvent(CommentEvent event){
        bean.setReviews("1");
        setData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
