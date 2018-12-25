package com.lanmei.bilan.ui.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lanmei.bilan.R;
import com.lanmei.bilan.utils.AKDialog;
import com.xson.common.app.BaseActivity;
import com.xson.common.utils.StringUtils;
import com.xson.common.utils.UIHelper;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 提现
 */
public class WithdrawDepositActivity extends BaseActivity {

    private static final int setZhifubao = 1;//设置支付宝账号

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.withdraw_money_et)
    EditText mMoneyEt;
    @InjectView(R.id.money_tv)
    TextView mMoneyTv;//可以余额
    @InjectView(R.id.account_tv)
    TextView mAccountTv;//显示到账账号


    private String mAccountName;//账号名称
    private String mMoney = "";//可提取金额


    @Override
    public int getContentViewId() {
        return R.layout.activity_withdraw_deposit;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        setSupportActionBar(mToolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle(R.string.withdraw_deposit);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.mipmap.back);

        mMoney = getIntent().getStringExtra("money");
        if (!StringUtils.isEmpty(mMoney)) {
            mMoneyTv.setText("可用余额" + mMoney + "元");
        }
//        ajaxAccount();//查询当前的支付宝账号
    }

//    private void ajaxAccount() {
//        HttpClient httpClient = HttpClient.newInstance(this);
//        ZhiFuBaoAccountApi api = new ZhiFuBaoAccountApi();
//        api.token = api.getToken(this);
//        httpClient.loadingRequest(api, new BeanRequest.SuccessListener<DataBean<ZhiFuBaoAccountBean>>() {
//            @Override
//            public void onResponse(DataBean<ZhiFuBaoAccountBean> response) {
//                if (isFinishing()){
//                    return;
//                }
//                ZhiFuBaoAccountBean bean = response.data;
//                if (bean != null && !StringUtils.isEmpty(bean.getAccount())) {
//                    mAccountName = bean.getAccount();
//                    mAccountTv.setText("到帐账户：" + bean.getBank() + "(" + mAccountName + ")");
//                }
//            }
//        });
//    }

    //
    //    @Override
    //    public boolean onCreateOptionsMenu(Menu menu) {
    //        getMenuInflater().inflate(R.menu.menu_recharge, menu);
    //        return true;
    //    }
    //
    //    @Override
    //    public boolean onOptionsItemSelected(MenuItem item) {
    //        switch (item.getItemId()) {
    //            case R.id.action_cancel:
    //                UIHelper.ToastMessage(this, R.string.developmenting);
    //                break;
    //        }
    //        return super.onOptionsItemSelected(item);
    //    }

    @OnClick({R.id.ll_add_wd_account, R.id.all_withdraw_tv, R.id.withdraw_bt})
    public void showOnClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.ll_add_wd_account://添加提现账号
                AKDialog.getBottomDialog(this, "添加银行卡", "添加支付宝账号", new AKDialog.DialogOnClickListener() {
                    @Override
                    public void OnClickTitle() {
                        UIHelper.ToastMessage(WithdrawDepositActivity.this, R.string.developing);
                    }

                    @Override
                    public void OnClickTitleSub() {
                        UIHelper.ToastMessage(WithdrawDepositActivity.this,R.string.developing);
//                IntentUtil.startActivity(WithdrawDepositActivity.this, AddZhiFuBaoActivity.class);
                    }
                });
                break;
            case R.id.all_withdraw_tv://全部提现
                mMoneyEt.setText("0");
                break;
            case R.id.withdraw_bt://提现
                UIHelper.ToastMessage(this,R.string.developing);
//                ajaxWithdraw();
                break;
        }
    }
    AlertDialog dialog;
//    private void ajaxWithdraw() {
//        if (StringUtils.isEmpty(mAccountName)) {
//            UIHelper.ToastMessage(this, "请先" + getString(R.string.add_withdraw_account));
//            return;
//        }
//        final String money = mMoneyEt.getText().toString().trim();
//        if (StringUtils.isEmpty(money)) {
//            UIHelper.ToastMessage(this, getString(R.string.input_withdraw_money));
//            return;
//        }
//        dialog = AKDialog.getInputPwdDialog(getContext(),getResources().getString(R.string.input_pay_pwd), "到帐金额：￥"+money, new AKDialog.DialogAffirmPayListener() {
//            @Override
//            public void affirm(String pwd) {
//                ajaxWithdrawApply(pwd,money);
//                dialog.cancel();
//            }
//        });
//        dialog.show();
//    }

//    private void ajaxWithdrawApply(String pwd,String money) {
//        if (StringUtils.isEmpty(pwd)){
//            UIHelper.ToastMessage(this,R.string.input_pay_pwd);
//            return;
//        }
//        HttpClient httpClient = HttpClient.newInstance(this);
//        WithdrawApplyApi api = new WithdrawApplyApi();
//        api.token = api.getToken(this);
//        api.money = money;
//        api.paypass = pwd;
//        httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
//            @Override
//            public void onResponse(BaseBean response) {
//                if (isFinishing()){
//                    return;
//                }
//                UIHelper.ToastMessage(WithdrawDepositActivity.this,"提现成功");
//                DataLoader.getInstance().loadUserInfo(WithdrawDepositActivity.this,null);
//                finish();
//            }
//        });
//    }

//    private void dialogPayStyle() {
//        AKDialog.getBottomDialog(this, "添加银行卡", "添加支付宝账号", R.layout.botton_dialog, new AKDialog.DialogOnClickListener() {
//            @Override
//            public void OnClickTitle() {
//                UIHelper.ToastMessage(WithdrawDepositActivity.this, R.string.developmenting);
//            }
//
//            @Override
//            public void OnClickTitleSub() {
//                Intent intent = new Intent();
//                intent.setClass(WithdrawDepositActivity.this,AddZhiFuBaoActivity.class);
//                startActivityForResult(intent,setZhifubao);
////                IntentUtil.startActivity(WithdrawDepositActivity.this, AddZhiFuBaoActivity.class);
//            }
//        });
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case setZhifubao:
//                ajaxAccount();
                break;
            case 2:
//                ZhiFuBaoAccountBean bean =(ZhiFuBaoAccountBean) data.getSerializableExtra("bean");
//                if (bean != null){
//                    mAccountName = bean.getAccount();
//                    mAccountTv.setText("到帐账户：" + bean.getBank() + "(" + mAccountName + ")");
//                }
                break;
        }
    }
}
