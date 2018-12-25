package com.lanmei.bilan.ui.mine.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chatuidemo.DemoHelper;
import com.lanmei.bilan.R;
import com.lanmei.bilan.event.ExitLoginEvent;
import com.lanmei.bilan.event.SetUserInfoEvent;
import com.lanmei.bilan.helper.UserHelper;
import com.lanmei.bilan.ui.goods.shop.DBShopCartHelper;
import com.lanmei.bilan.ui.login.LoginActivity;
import com.lanmei.bilan.update.NotificationDownloadCreator;
import com.lanmei.bilan.utils.AKDialog;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.xson.common.app.BaseActivity;
import com.xson.common.helper.DataCleanManager;
import com.xson.common.helper.HttpClient;
import com.xson.common.utils.IntentUtil;
import com.xson.common.utils.UIHelper;
import com.xson.common.widget.CenterTitleToolbar;

import org.greenrobot.eventbus.EventBus;
import org.lzh.framework.updatepluginlib.UpdateBuilder;

import butterknife.InjectView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import rx.functions.Action1;


/**
 * 设置
 */

public class SettingActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    CenterTitleToolbar mToolbar;
    @InjectView(R.id.cache_count)
    TextView mCleanCacheTv;


    @Override
    public int getContentViewId() {
        return R.layout.activity_setting;
    }


    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        setSupportActionBar(mToolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle(R.string.setting);
        actionbar.setHomeAsUpIndicator(R.mipmap.back);

        try {
            mCleanCacheTv.setText(DataCleanManager.getCacheSize(getCacheDir()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @OnClick(R.id.back_login)
    public void showBackLogin() {
        AKDialog.getAlertDialog(this, getResources().getString(R.string.logout_tips), new AKDialog.AlertDialogListener() {
            @Override
            public void yes() {
//                logoutHx();
                UserHelper.getInstance(SettingActivity.this).cleanLogin();
                HttpClient.newInstance(SettingActivity.this).clearCache();
                // show login screen
                EventBus.getDefault().post(new SetUserInfoEvent());
                Toast.makeText(SettingActivity.this, "退出成功", Toast.LENGTH_LONG).show();
                DBShopCartHelper.dbGoodsCartManager = null;//不同用户登录uid重新赋值
                IntentUtil.startActivity(SettingActivity.this, LoginActivity.class);
                EventBus.getDefault().post(new ExitLoginEvent());

                JPushInterface.stopPush(getApplicationContext());

                onBackPressed();
            }
        });
    }

    void logoutHx() {
        final ProgressDialog pd = new ProgressDialog(this);
        String st = getResources().getString(R.string.Are_logged_out);
        pd.setMessage(st);
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        DemoHelper.getInstance().logout(false, new EMCallBack() {

            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        pd.dismiss();
                        UserHelper.getInstance(SettingActivity.this).cleanLogin();
                        HttpClient.newInstance(SettingActivity.this).clearCache();
                        // show login screen
                        EventBus.getDefault().post(new SetUserInfoEvent());
                        Toast.makeText(SettingActivity.this, "退出成功", Toast.LENGTH_LONG).show();
                        DBShopCartHelper.dbGoodsCartManager = null;//不同用户登录uid重新赋值
                        IntentUtil.startActivity(SettingActivity.this, LoginActivity.class);
                        EventBus.getDefault().post(new ExitLoginEvent());

                        JPushInterface.stopPush(getApplicationContext());

                        onBackPressed();
                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        pd.dismiss();
                        Toast.makeText(SettingActivity.this, "unbind devicetokens failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


    @OnClick({R.id.ll_about_us, R.id.ll_tickling, R.id.ll_info_setting,
            R.id.ll_help_info, R.id.ll_clean_cache, R.id.ll_reset_pwd, R.id.ll_versions})
    public void showSettingInfo(View view) {//
//        if (!CommonUtils.isLogin(this)) {
//            return;
//        }
        switch (view.getId()) {
            case R.id.ll_about_us://关于我们
                UIHelper.ToastMessage(this, R.string.developing);
                break;
            case R.id.ll_tickling://留言反馈
                UIHelper.ToastMessage(this, R.string.developing);
                break;
            case R.id.ll_info_setting://消息设置
                UIHelper.ToastMessage(this, R.string.developing);
                break;
            case R.id.ll_help_info://帮助信息
                UIHelper.ToastMessage(this, R.string.developing);
                break;
            case R.id.ll_clean_cache://清除缓存
                showClearCache();
                break;
            case R.id.ll_reset_pwd://修改密码
                UIHelper.ToastMessage(this, R.string.developing);
//                RegisterActivity.startActivity(this, RegisterActivity.RESET_PWD_STYLE);
                break;
            case R.id.ll_versions://版本信息
                requestStoragePermission();
                break;
        }

    }

    /** 请求文件读写权限。*/
    private void requestStoragePermission() {
        new RxPermissions(this)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean){
                            //app更新
                            UpdateBuilder.create()
                                    .downloadDialogCreator(new NotificationDownloadCreator())
//                                    .installDialogCreator(new DefaultNeedInstallCreator())
//                                    .strategy(new AllDialogShowStrategy())
                                    .check();
                        }
                    }
                });
    }

    public void showClearCache() {
        try {
            DataCleanManager.cleanInternalCache(getApplicationContext());
            DataCleanManager.cleanExternalCache(getApplicationContext());
            mCleanCacheTv.setText(DataCleanManager.getCacheSize(getCacheDir()));
            UIHelper.ToastMessage(this, "清理完毕");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
