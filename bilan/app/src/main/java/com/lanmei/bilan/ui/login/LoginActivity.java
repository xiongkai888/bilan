package com.lanmei.bilan.ui.login;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.data.volley.Response;
import com.data.volley.error.VolleyError;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chatuidemo.DemoHelper;
import com.hyphenate.chatuidemo.db.DemoDBManager;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.lanmei.bilan.BtcimApp;
import com.lanmei.bilan.MainActivity;
import com.lanmei.bilan.R;
import com.lanmei.bilan.api.LoginApi;
import com.lanmei.bilan.bean.UserBean;
import com.lanmei.bilan.event.ExitLoginEvent;
import com.lanmei.bilan.event.LoginEMClientEvent;
import com.lanmei.bilan.event.RegisterEvent;
import com.lanmei.bilan.event.SetUserInfoEvent;
import com.lanmei.bilan.helper.UserHelper;
import com.lanmei.bilan.utils.CommonUtils;
import com.lanmei.bilan.utils.SharedAccount;
import com.xson.common.app.BaseActivity;
import com.xson.common.bean.DataBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.utils.IntentUtil;
import com.xson.common.utils.StringUtils;
import com.xson.common.utils.UIHelper;
import com.xson.common.widget.CenterTitleToolbar;
import com.xson.common.widget.DrawClickableEditText;
import com.xson.common.widget.ProgressHUD;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.InjectView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 * 登录
 */
public class LoginActivity extends BaseActivity {


    @InjectView(R.id.toolbar)
    CenterTitleToolbar toolbar;
    @InjectView(R.id.phone_et)
    DrawClickableEditText phoneEt;
    @InjectView(R.id.pwd_et)
    DrawClickableEditText pwdEt;
    @InjectView(R.id.showPwd_iv)
    ImageView showPwdIv;

    @Override
    public int getContentViewId() {
        return R.layout.activity_login;
    }


    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle(R.string.login);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.mipmap.back);
        String mobile = SharedAccount.getInstance(this).getMobile();
        phoneEt.setText(mobile);
//        pwdEt.setText("123456");
        EventBus.getDefault().register(this);
        initProgressDialog();
    }

    /**
     * 登录等待
     */
    ProgressHUD mProgressHUD;

    private void initProgressDialog() {
        mProgressHUD = ProgressHUD.show(this, "正在登陆...", true, false, null);
        mProgressHUD.cancel();
        mProgressHUD.setCancelable(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_register:
                IntentUtil.startActivity(this, RegisterActivity.class,"register");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.showPwd_iv, R.id.forgotPwd_tv, R.id.login_bt, R.id.weixin_login_iv, R.id.qq_login_iv, R.id.weibo_login_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.showPwd_iv://显示密码
                showPwd();
                break;
            case R.id.forgotPwd_tv://忘记密码
                IntentUtil.startActivity(this, RegisterActivity.class,"forgotPwd");
                break;
            case R.id.login_bt://登录
                login();
                break;
            case R.id.weixin_login_iv:
                UIHelper.ToastMessage(this, R.string.developing);
                break;
            case R.id.qq_login_iv:
                UIHelper.ToastMessage(this, R.string.developing);
                break;
            case R.id.weibo_login_iv:
                UIHelper.ToastMessage(this, R.string.developing);
                break;
        }
    }

    String phone;
    UserBean bean;
    private void login() {
        phone = CommonUtils.getStringByEditText(phoneEt);
        if (StringUtils.isEmpty(phone)) {
            Toast.makeText(this, R.string.input_phone_number, Toast.LENGTH_SHORT).show();
            return;
        }
        if (!StringUtils.isMobile(phone)) {
            Toast.makeText(this, R.string.not_mobile_format, Toast.LENGTH_SHORT).show();
            return;
        }
        String pwd = CommonUtils.getStringByEditText(pwdEt);
        if (StringUtils.isEmpty(pwd) || pwd.length() < 6) {
            Toast.makeText(this, R.string.input_password_count, Toast.LENGTH_SHORT).show();
            return;
        }
        mProgressHUD.show();
        LoginApi api = new LoginApi();
        api.phone = phone;
        api.password = pwd;
        HttpClient.newInstance(this).request(api, new BeanRequest.SuccessListener<DataBean<UserBean>>() {
            @Override
            public void onResponse(DataBean<UserBean> response) {
                if (isFinishing()) {
                    return;
                }
                bean = response.data;
//                loginHx();

                JPushInterface.resumePush(getApplicationContext());
                JPushInterface.setAlias(getApplicationContext(), 0 ,bean.getId());
                //先清空MainActivity上面的Activity，再退出MainActivity
                IntentUtil.startActivity(LoginActivity.this, MainActivity.class);
                EventBus.getDefault().post(new ExitLoginEvent());

                UserHelper.getInstance(LoginActivity.this).saveBean(bean);
                EventBus.getDefault().post(new SetUserInfoEvent());
                SharedAccount.getInstance(LoginActivity.this).saveMobile(phone);
//                EventBus.getDefault().post(new LoginEMClientEvent());
                IntentUtil.startActivity(LoginActivity.this, MainActivity.class);//重新进入MainActivity加载数据，
                mProgressHUD.cancel();
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (isFinishing()) {
                    return;
                }
                mProgressHUD.cancel();
                UIHelper.ToastMessage(LoginActivity.this, error.getMessage());
            }
        });
    }

    //    String userType;
    String currentPassword = "123456";
    String currentUsername;

    public void loginHx() {
        if (!EaseCommonUtils.isNetWorkConnected(this)) {
            Toast.makeText(this, R.string.network_isnot_available, Toast.LENGTH_SHORT).show();
            return;
        }
        if (bean != null) {
            currentUsername = BtcimApp.HX_USER_Head + bean.getId();
        }
        if (TextUtils.isEmpty(currentUsername)) {
            Toast.makeText(this, R.string.User_name_cannot_be_empty, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(currentPassword)) {
            Toast.makeText(this, R.string.Password_cannot_be_empty, Toast.LENGTH_SHORT).show();
            return;
        }


        // After logout，the DemoDB may still be accessed due to async callback, so the DemoDB will be re-opened again.
        // close it before login to make sure DemoDB not overlap
        DemoDBManager.getInstance().closeDB();

        // reset current user name before login
        DemoHelper.getInstance().setCurrentUserName(currentUsername);

        final long start = System.currentTimeMillis();
        EMClient.getInstance().login(currentUsername, currentPassword, new EMCallBack() {

            @Override
            public void onSuccess() {
                // ** manually load all local groups and conversation
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();

                // update current user's display name for APNs
                boolean updatenick = EMClient.getInstance().pushManager().updatePushNickname(
                        BtcimApp.currentUserNick.trim());
                if (!updatenick) {
//                    Log.e("LoginActivity", "update current user nick fail");
                }
                if (!LoginActivity.this.isFinishing() && mProgressHUD.isShowing()) {
                    mProgressHUD.dismiss();
                }

                JPushInterface.resumePush(getApplicationContext());
                JPushInterface.setAlias(getApplicationContext(), 0 ,bean.getId());

                DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();
                UserHelper.getInstance(LoginActivity.this).saveBean(bean);
                EventBus.getDefault().post(new SetUserInfoEvent());
                SharedAccount.getInstance(LoginActivity.this).saveMobile(phone);
                EventBus.getDefault().post(new LoginEMClientEvent());
                IntentUtil.startActivity(LoginActivity.this, MainActivity.class);
                finish();
            }

            @Override
            public void onProgress(int progress, String status) {
            }

            @Override
            public void onError(final int code, final String message) {
//                Log.d(TAG, "login: onError: " + code);
                runOnUiThread(new Runnable() {
                    public void run() {
                        mProgressHUD.dismiss();
                        UserHelper.getInstance(LoginActivity.this).cleanLogin();
                        EventBus.getDefault().post(new SetUserInfoEvent());
                        Toast.makeText(getApplicationContext(), "登录超时", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private boolean isShowPwd = false;//是否显示密码

    private void showPwd() {
        if (!isShowPwd) {//显示密码
            pwdEt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            showPwdIv.setImageResource(R.mipmap.pwd_on);
        } else {//隐藏密码
            pwdEt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            showPwdIv.setImageResource(R.mipmap.pwd_off);
        }
        isShowPwd = !isShowPwd;
    }

    //注册后调用
    @Subscribe
    public void respondRegisterEvent(RegisterEvent event) {
        phoneEt.setText(event.getPhone());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}