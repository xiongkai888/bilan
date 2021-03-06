package com.lanmei.bilan.ui.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.lanmei.bilan.R;
import com.lanmei.bilan.adapter.WithdrawCardListAdapter;
import com.lanmei.bilan.api.BurseApi;
import com.lanmei.bilan.bean.UserBean;
import com.lanmei.bilan.bean.WithdrawCardListBean;
import com.lanmei.bilan.event.ChooseKaEvent;
import com.lanmei.bilan.utils.AKDialog;
import com.lanmei.bilan.utils.CommonUtils;
import com.xson.common.app.BaseActivity;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.utils.StringUtils;
import com.xson.common.utils.UIHelper;
import com.xson.common.widget.CenterTitleToolbar;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 选择银行卡
 */
public class ChooseKaActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener {

    @InjectView(R.id.toolbar)
    CenterTitleToolbar mToolbar;

    @InjectView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    WithdrawCardListAdapter mAdapter;
    int editType = 0;

    @Override
    public int getContentViewId() {
        return R.layout.activity_choose_ka;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        mToolbar.inflateMenu(R.menu.menu_edit);
        mToolbar.setOnMenuItemClickListener(this);
        mToolbar.setTitle("选择银行卡");
        mToolbar.setNavigationIcon(R.mipmap.back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        mRecyclerView.setLayoutManager(new LinearLayoutManager(ChooseKaActivity.this));
        mAdapter = new WithdrawCardListAdapter(ChooseKaActivity.this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setChooseCardListener(new WithdrawCardListAdapter.ChooseCardListener() {
            @Override
            public void chooseCard(String cardName,String banksNo) {
                EventBus.getDefault().post(new ChooseKaEvent(cardName,banksNo));
                finish();
            }

            @Override
            public void unBound(String cardId) {
                dialogCode(cardId);
            }
        });
        ajaxWithdrawCardList();
    }

    AlertDialog alertDialog;
    private void dialogCode(String cardId) {
        if (alertDialog != null && !alertDialog.isShowing()){
            alertDialog.show();
            return;
        }
        UserBean bean = CommonUtils.getUserBean(this);
        if (bean == null){
            return;
        }
        String phone = bean.getPhone();
        if (StringUtils.isEmpty(phone)){
            UIHelper.ToastMessage(this,"您还没有绑定手机号，请先绑定");
            return;
        }
        alertDialog = AKDialog.getChangePhoneDialog(this,"解绑银行卡验证码将以短息方式发送至您的手机，点击获取验证码按钮后请在60s内输入验证码！", phone,cardId, new AKDialog.ChangePhoneListener() {
            @Override
            public void succeed(String newPhone) {

            }

            @Override
            public void unBound() {
                ajaxWithdrawCardList();
//                Constants.notifyDoSomething(ChooseKaActivity.this,UNBOUND_SUCCEED);
                alertDialog.cancel();
            }
        });
        alertDialog.show();
    }

    private void ajaxWithdrawCardList() {
        HttpClient httpClient = HttpClient.newInstance(this);
        BurseApi api = new BurseApi();
        api.uid = api.getUserId(this);
        httpClient.loadingRequest(api, new BeanRequest.SuccessListener<NoPageListBean<WithdrawCardListBean>>() {
            @Override
            public void onResponse(NoPageListBean<WithdrawCardListBean> response) {
                List<WithdrawCardListBean> list = response.data;
                mAdapter.setData(list);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @OnClick(R.id.ll_add_ka)
    public void showAddKa() {//添加(绑定)银行卡
        Intent intent = new Intent(this, BoundKaActivity.class);
        startActivityForResult(intent, 1010);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1010) {
            ajaxWithdrawCardList();
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.action_edit) {
            mToolbar.getMenu().clear();
            mToolbar.inflateMenu(R.menu.menu_done);
            editType = 1;
        } else if (item.getItemId() == R.id.action_done) {
            mToolbar.getMenu().clear();
            mToolbar.inflateMenu(R.menu.menu_edit);
            editType = 0;
        }
        mAdapter.setEditStype(editType);
        return true;
    }
}
