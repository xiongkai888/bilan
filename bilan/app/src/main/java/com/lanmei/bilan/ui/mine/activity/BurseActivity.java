package com.lanmei.bilan.ui.mine.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lanmei.bilan.R;
import com.lanmei.bilan.api.BurseApi;
import com.lanmei.bilan.bean.UserBean;
import com.lanmei.bilan.bean.WithdrawCardListBean;
import com.lanmei.bilan.event.ChooseKaEvent;
import com.lanmei.bilan.helper.UserHelper;
import com.lanmei.bilan.utils.AKDialog;
import com.xson.common.app.BaseActivity;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.utils.IntentUtil;
import com.xson.common.utils.StringUtils;
import com.xson.common.utils.UIHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 钱包
 */
public class BurseActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.card_tv)
    TextView mCardNameTv;
    @InjectView(R.id.balance_tv)
    TextView mBalanceTv;//余额
    @InjectView(R.id.money_et)
    EditText moneyEt;

    @Override
    public int getContentViewId() {
        return R.layout.activity_burse;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        setSupportActionBar(mToolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("钱包");
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.mipmap.back);
        UserBean bean = UserHelper.getInstance(this).getUserBean();
        if (bean != null) {
            mBalanceTv.setText(bean.getBalance());
        }
        ajaxBurse();
    }

    private void ajaxBurse() {
        HttpClient httpClient = HttpClient.newInstance(this);
        BurseApi api = new BurseApi();
        api.uid = api.getUserId(this);
        httpClient.request(api, new BeanRequest.SuccessListener<NoPageListBean<WithdrawCardListBean>>() {
            @Override
            public void onResponse(NoPageListBean<WithdrawCardListBean> response) {
                if (isFinishing()) {
                    return;
                }
                List<WithdrawCardListBean> list = response.data;
                if (!StringUtils.isEmpty(list)) {
                    WithdrawCardListBean bean = list.get(0);
                    if (bean != null) {
                        mCardNameTv.setText(bean.getBanks_name());
                    }
                } else {
                    alertDialog();
                }
            }
        });

    }

    private void alertDialog() {
        AKDialog.getAlertDialog(this, "您还没绑定银行卡，是否先绑定银行卡？", new AKDialog.AlertDialogListener() {
            @Override
            public void yes() {
                IntentUtil.startActivity(BurseActivity.this, BoundKaActivity.class);
            }
        });
    }

    @OnClick({R.id.ll_choose_ka, R.id.carry_bt,R.id.num_subtract_iv, R.id.num_add_iv})
    public void onViewClicked(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.ll_choose_ka://选择提现银行卡
                IntentUtil.startActivity(this, ChooseKaActivity.class);
                break;
            case R.id.carry_bt://提现
                UIHelper.ToastMessage(this, R.string.developing);
                break;
            case R.id.num_subtract_iv:
                break;
            case R.id.num_add_iv:
                break;
        }
    }

    @Subscribe
    public void chooseKaEvent(ChooseKaEvent event){
        mCardNameTv.setText(event.getCarName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
