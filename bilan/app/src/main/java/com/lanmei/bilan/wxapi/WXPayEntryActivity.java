package com.lanmei.bilan.wxapi;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.lanmei.bilan.R;
import com.lanmei.bilan.utils.Constant;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
    public static final String ACTION_PAY_COMPLETE_NOTIFY = "ACTION_PAY_COMPLETE_NOTIFY";//支付成功通知
    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);

        api = WXAPIFactory.createWXAPI(this, Constant.WEIXIN_APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(final BaseResp resp) {

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.app_tip);
            builder.setPositiveButton(R.string.app_ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    if (resp.errCode == BaseResp.ErrCode.ERR_OK) { // 支付成功，跳转到我的订单
//                        Constants.notifyDoSomething(WXPayEntryActivity.this, ACTION_PAY_COMPLETE_NOTIFY);
//						IntentUtil.startActivity(WXPayEntryActivity.this, MyAllOrderActivity.class);//
                    } else {
                        // 跳到待付款界面
//						OrdersForGoodsActivity.startActivityForWaitPay(WXPayEntryActivity.this); // 待付款
                    }
                    finish();
                }
            });
            String result = (resp.errCode == BaseResp.ErrCode.ERR_OK) ? "支付成功" : "支付失败(code=" + resp.errCode + ")";
            builder.setMessage(result);
            builder.setCancelable(false);
            builder.show();
        }
    }

    @Override
    public void onBackPressed() {
//		super.onBackPressed();
    }
}