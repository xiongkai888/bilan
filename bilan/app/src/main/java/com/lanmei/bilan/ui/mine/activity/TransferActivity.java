package com.lanmei.bilan.ui.mine.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.lanmei.bilan.R;
import com.lanmei.bilan.api.JiTransferApi;
import com.lanmei.bilan.event.TransferEvent;
import com.lanmei.bilan.utils.CommonUtils;
import com.xson.common.app.BaseActivity;
import com.xson.common.bean.BaseBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.utils.StringUtils;
import com.xson.common.utils.UIHelper;
import com.xson.common.widget.CenterTitleToolbar;

import org.greenrobot.eventbus.EventBus;

import butterknife.InjectView;

/**
 * 转出
 */
public class TransferActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    CenterTitleToolbar mToolbar;
    @InjectView(R.id.id_et)
    EditText idEt;
    @InjectView(R.id.transfer_ji_et)
    EditText transferJiEt;
    @InjectView(R.id.remark_tv)
    EditText remarkTv;
    String id;

    @Override
    public int getContentViewId() {
        return R.layout.activity_transfer;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        setSupportActionBar(mToolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle("赠送");
        actionbar.setHomeAsUpIndicator(R.mipmap.back);
        id = getIntent().getStringExtra("value");
        if (!StringUtils.isEmpty(id)){
            idEt.setText(id);
            idEt.setFocusable(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_submit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_submit:
                loadTransfer();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadTransfer() {
        String id = CommonUtils.getStringByEditText(idEt);
        if (com.xson.common.utils.StringUtils.isEmpty(id)){
            UIHelper.ToastMessage(this,R.string.input_id);
            return;
        }
        String ji = CommonUtils.getStringByEditText(transferJiEt);
        if (com.xson.common.utils.StringUtils.isEmpty(ji)){
            UIHelper.ToastMessage(this,R.string.input_ji);
            return;
        }
        JiTransferApi api = new JiTransferApi();
        api.price = ji;
        api.userid = api.getUserId(this);
        api.payee = id;
        api.content = CommonUtils.getStringByEditText(remarkTv);
        HttpClient.newInstance(this).loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                if (isFinishing()){
                    return;
                }
                UIHelper.ToastMessage(TransferActivity.this,response.getMsg());
                int code = response.getCode();
                if (code == 1){
                    EventBus.getDefault().post(new TransferEvent());
                    onBackPressed();
                }
            }
        });
    }

}
