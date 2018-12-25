package com.lanmei.bilan.ui.mine.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lanmei.bilan.R;
import com.lanmei.bilan.adapter.RechargeAdapter;
import com.lanmei.bilan.utils.CommonUtils;
import com.xson.common.app.BaseActivity;
import com.xson.common.utils.StringUtils;
import com.xson.common.utils.UIHelper;
import com.xson.common.widget.CenterTitleToolbar;
import com.xson.common.widget.WrapHeightGridView;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 充值
 */
public class RechargeActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    CenterTitleToolbar mToolbar;
    @InjectView(R.id.gridView)
    WrapHeightGridView mGridView;//充值金额列表
    RechargeAdapter mAdapter;
    @InjectView(R.id.ll_input_money)
    LinearLayout mLLInputMoney;//输入金额布局

    @InjectView(R.id.cb_zhifubao)
    CheckBox mZhifubaoCB;//支付宝
    @InjectView(R.id.cb_weixin)
    CheckBox mWeixinCB;//微信
    @InjectView(R.id.input_money_et)
    EditText mInputMoneyEt;//输入其他金额

    private boolean isOtherMoney = false;//是不是其他金额
    private String rechargeMoney;//充值的金额

    @Override
    public int getContentViewId() {
        return R.layout.activity_recharge;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        setSupportActionBar(mToolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle("充值");
        actionbar.setHomeAsUpIndicator(R.mipmap.back);

        mAdapter = new RechargeAdapter(this);
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view;
                textView.setSelected(true);
                String itemStr = (String) parent.getItemAtPosition(position);
                if (CommonUtils.OTHER_MONEY.equals(itemStr)) {
                    isOtherMoney = true;
                    rechargeMoney = "";
                    mLLInputMoney.setVisibility(View.VISIBLE);
                } else {
                    isOtherMoney = false;
                    if (!StringUtils.isEmpty(itemStr) && itemStr.length() > 1) {
                        rechargeMoney = itemStr.substring(0, itemStr.length() - 1);
                    }
                    mLLInputMoney.setVisibility(View.GONE);
                }
            }
        });
    }

    @OnClick(R.id.affirm_bt)
    public void showAffirmRecharge() {//确认并充值

        if (isOtherMoney) {
            rechargeMoney = mInputMoneyEt.getText().toString().trim();
            if (StringUtils.isEmpty(rechargeMoney)) {
                UIHelper.ToastMessage(this, R.string.input_recharge_money);
                return;
            }
        } else {
            if (StringUtils.isEmpty(rechargeMoney)) {
                UIHelper.ToastMessage(this, R.string.choose_recharge_money);
                return;
            }
        }
        ajaxPay();//充值
    }

    private void ajaxPay() {
        UIHelper.ToastMessage(this,R.string.developing);
//        HttpClient httpClient = HttpClient.newInstance(this);
//        RechargeApi api = new RechargeApi();
//        api.token = UserHelper.getInstance(this).getToken();
//        api.money = rechargeMoney;
//        api.pay_type = type;
//        if (type == 1) {//支付宝充值
//            httpClient.loadingRequest(api, new BeanRequest.SuccessListener<DataBean<ZhiFuBaoBean>>() {
//                @Override
//                public void onResponse(DataBean<ZhiFuBaoBean> response) {
//                    if (RechargeActivity.this.isFinishing()){
//                        return;
//                    }
//                    ZhiFuBaoBean bean = response.data;
//                    AlipayHelper alipayHelper = new AlipayHelper(RechargeActivity.this);
//                    alipayHelper.setPayParam(bean);
//                    alipayHelper.payNow();
//                }
//            });
//        } else if (type == 7) {//微信充值
//            httpClient.loadingRequest(api, new BeanRequest.SuccessListener<DataBean<WeiXinBean>>() {
//                @Override
//                public void onResponse(DataBean<WeiXinBean> response) {
//                    if (RechargeActivity.this.isFinishing()){
//                        return;
//                    }
//                    WeiXinBean bean = response.data;
//                    WXPayHelper payHelper = new WXPayHelper(RechargeActivity.this);
//                    payHelper.setPayParam(bean);
//                    payHelper.orderNow();
//                }
//            });
//        }

    }


    @OnClick({R.id.ll_zhifubao_pay, R.id.ll_weixin_pay,
            R.id.cb_zhifubao, R.id.cb_weixin})
    public void selectPayMethod(View view) {//选择充值方式
        switch (view.getId()) {

            case R.id.ll_weixin_pay:
            case R.id.cb_weixin://微信
                selectCheckBox(mWeixinCB, mZhifubaoCB, 7);
                break;
            case R.id.ll_zhifubao_pay:
            case R.id.cb_zhifubao://支付宝
                selectCheckBox(mZhifubaoCB, mWeixinCB, 1);
                break;
        }

    }


    int type = 1;// 1:支付宝充值   7：微信充值

    //选择支付方式
    private void selectCheckBox(CheckBox cb1, CheckBox cb2, int type) {
        this.type = type;
        cb1.setChecked(true);
        if (cb2.isChecked()) {
            cb2.setChecked(false);
        }
    }

}
