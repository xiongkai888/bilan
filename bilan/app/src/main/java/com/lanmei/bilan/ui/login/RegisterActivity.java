package com.lanmei.bilan.ui.login;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lanmei.bilan.R;
import com.lanmei.bilan.api.CodeApi;
import com.lanmei.bilan.api.GetuserByPhoneApi;
import com.lanmei.bilan.api.RegisterApi;
import com.lanmei.bilan.api.ResetPwdApi;
import com.lanmei.bilan.event.RegisterEvent;
import com.lanmei.bilan.utils.CommonUtils;
import com.lanmei.bilan.utils.RandomUtil;
import com.xson.common.app.BaseActivity;
import com.xson.common.bean.BaseBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.helper.SimpleTextWatcher;
import com.xson.common.utils.CodeCountDownTimer;
import com.xson.common.utils.L;
import com.xson.common.utils.StringUtils;
import com.xson.common.utils.UIHelper;
import com.xson.common.widget.CenterTitleToolbar;
import com.xson.common.widget.DrawClickableEditText;

import org.greenrobot.eventbus.EventBus;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 注册、忘记密码、重设密码
 */
public class RegisterActivity extends BaseActivity implements CodeCountDownTimer.CodeCountDownTimerListener, Toolbar.OnMenuItemClickListener {

    @InjectView(R.id.toolbar)
    CenterTitleToolbar toolbar;
    //    @InjectView(R.id.name_et)
//    DrawClickableEditText nameEt;
    @InjectView(R.id.ll_name)
    LinearLayout llName;
    @InjectView(R.id.user_name_et)
    DrawClickableEditText userNameEt;//
    @InjectView(R.id.phone_et)
    DrawClickableEditText phoneEt;
    @InjectView(R.id.code_et)
    DrawClickableEditText codeEt;
    @InjectView(R.id.obtainCode_bt)
    Button obtainCodeBt;
    @InjectView(R.id.pwd_et)
    DrawClickableEditText pwdEt;
    @InjectView(R.id.showPwd_iv)
    ImageView showPwdIv;
    @InjectView(R.id.pwd_again_et)
    DrawClickableEditText pwdAgainEt;
    @InjectView(R.id.showPwd_again_iv)
    ImageView showPwdAgainIv;
    @InjectView(R.id.referrer_phone_et)
    DrawClickableEditText referrerPhoneEt;
    @InjectView(R.id.ll_referrer_phone)
    LinearLayout llReferrerPhone;
    @InjectView(R.id.register_bt)
    Button button;

    String type;

    private CodeCountDownTimer mCountDownTimer;//获取验证码倒计时


    @Override
    public int getContentViewId() {
        return R.layout.activity_register;
    }

    boolean isExist;//是否存在该用户

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();

        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.mipmap.back);

        //初始化倒计时
        initCountDownTimer();
        type = getIntent().getStringExtra("value");
        if (StringUtils.isSame(type, "register")) {
            llReferrerPhone.setVisibility(View.VISIBLE);
            actionbar.setTitle(R.string.register);
        } else {
            llReferrerPhone.setVisibility(View.GONE);
            actionbar.setTitle("找回密码");
            button.setText("确定");
            phoneEt.addTextChangedListener(new SimpleTextWatcher() {
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (!StringUtils.isEmpty(charSequence + "") && charSequence.length() == 11) {
                        isExist = false;
                        GetuserByPhoneApi api = new GetuserByPhoneApi();
                        api.phone = charSequence + "";
                        HttpClient.newInstance(RegisterActivity.this).loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
                            @Override
                            public void onResponse(BaseBean response) {
                                if (isFinishing()) {
                                    return;
                                }
                                if (!StringUtils.isEmpty(response.getMsg())) {
                                    UIHelper.ToastMessage(RegisterActivity.this, "该用户不存在");
                                } else {
                                    isExist = true;
                                }
                            }
                        });
                    }
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_login:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initCountDownTimer() {
        mCountDownTimer = new CodeCountDownTimer(60 * 1000, 1000);
        mCountDownTimer.setCodeCountDownTimerListener(this);
    }

    private String phone;
    String codeStr = "-k";

    private void loadObtainCode(String phone) {
        codeStr = RandomUtil.generateNumberString(6);//随机生成的六位验证码，好神奇
        L.d("codeStr", codeStr);
        HttpClient httpClient = HttpClient.newInstance(this);
        CodeApi codeApi = new CodeApi();
        codeApi.phone = phone;
        codeApi.code = codeStr;
        httpClient.loadingRequest(codeApi, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                if (RegisterActivity.this.isFinishing()) {
                    return;
                }
                mCountDownTimer.start();
                UIHelper.ToastMessage(RegisterActivity.this, getString(R.string.send_code_succeed));
            }
        });
    }


    //注册或找回密码、修改密码
    private void registerOrRetrievePwd(String userName, String phone, String code, String pwd, String phone1) {
        HttpClient httpClient = HttpClient.newInstance(this);
        if (StringUtils.isSame(type, "register")) {
            register(userName, phone, pwd, code, httpClient, phone1);
        } else {
            forgotPwd(phone, pwd, code, httpClient);
        }
    }

    private void forgotPwd(String phone, String pwd, String code, HttpClient httpClient) {
        ResetPwdApi api = new ResetPwdApi();
        api.phone = phone;
        api.password = pwd;
        httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                if (isFinishing()) {
                    return;
                }
                UIHelper.ToastMessage(RegisterActivity.this, "修改密码成功");
                finish();
            }
        });
    }


    //注册
    private void register(String userName, final String phone, String pwd, String code, HttpClient httpClient, String phone1) {
        RegisterApi api = new RegisterApi();
//        api.username = userName;
        api.nickname = phone;
        api.user_type = "0";
        api.password = pwd;
        api.phone = phone;
        api.rtid = phone1;//推荐人手机号码
        api.open_type = "0";
        httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                if (isFinishing()) {
                    return;
                }
                EventBus.getDefault().post(new RegisterEvent(phone));
                UIHelper.ToastMessage(RegisterActivity.this, "注册成功");
                finish();
            }
        });
    }


    private boolean isShowPwd = false;//是否显示密码

    @Override
    public void onTick(long l) {
        if (obtainCodeBt != null) {
            obtainCodeBt.setText(l / 1000 + "s后重新获取");
            obtainCodeBt.setClickable(false);
            obtainCodeBt.setTextSize(11);
        }
    }

    @Override
    public void onFinish() {
        if (obtainCodeBt != null) {
            obtainCodeBt.setText(getString(R.string.obtain_code));
            obtainCodeBt.setClickable(true);
            obtainCodeBt.setTextSize(14);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        onBackPressed();
        return true;
    }


    @OnClick({R.id.showPwd_iv, R.id.showPwd_again_iv, R.id.register_bt, R.id.obtainCode_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.showPwd_iv:
                if (!isShowPwd) {//显示密码
                    pwdEt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    showPwdIv.setImageResource(R.mipmap.pwd_on);
                } else {//隐藏密码
                    pwdEt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    showPwdIv.setImageResource(R.mipmap.pwd_off);
                }
                isShowPwd = !isShowPwd;
                break;
            case R.id.showPwd_again_iv:
                break;
            case R.id.register_bt://注册
                loadRegister();
                break;
            case R.id.obtainCode_bt://获取验证码
                phone = phoneEt.getText().toString();//电话号码
                if (StringUtils.isEmpty(phone)) {
                    UIHelper.ToastMessage(this, R.string.input_phone_number);
                    return;
                }
                if (!StringUtils.isMobile(phone)) {
                    Toast.makeText(this, R.string.not_mobile_format, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isExist && !StringUtils.isSame(type, "register")) {
                    UIHelper.ToastMessage(this,"该用户不存在");
                    return;
                }
                loadObtainCode(phone);
                break;
        }
    }

    private void loadRegister() {
        String userName = CommonUtils.getStringByEditText(userNameEt);//姓名
//        if (StringUtils.isEmpty(userName)) {
//            UIHelper.ToastMessage(this, R.string.input_name);
//            return;
//        }
        phone = CommonUtils.getStringByEditText(phoneEt);//电话号码
        if (StringUtils.isEmpty(phone)) {
            UIHelper.ToastMessage(this, R.string.input_phone_number);
            return;
        }
        if (!StringUtils.isMobile(phone)) {
            Toast.makeText(this, R.string.not_mobile_format, Toast.LENGTH_SHORT).show();
            return;
        }
        String code = CommonUtils.getStringByEditText(codeEt);//
        if (StringUtils.isEmpty(code)) {
            UIHelper.ToastMessage(this, R.string.input_code);
            return;
        }
        String pwd = CommonUtils.getStringByEditText(pwdEt);//
        if (StringUtils.isEmpty(pwd) || pwd.length() < 6) {
            UIHelper.ToastMessage(this, R.string.input_password_count);
            return;
        }
        String pwdAgain = CommonUtils.getStringByEditText(pwdAgainEt);//
        if (StringUtils.isEmpty(pwdAgain)) {
            UIHelper.ToastMessage(this, R.string.input_pwd_again);
            return;
        }
        if (!StringUtils.isSame(pwd, pwdAgain)) {
            UIHelper.ToastMessage(this, R.string.password_inconformity);
            return;
        }
        if (!code.equals(codeStr)) {
            UIHelper.ToastMessage(this, R.string.code_error);
            return;
        }
        String phone1 = CommonUtils.getStringByEditText(referrerPhoneEt);//推荐人电话号码

        registerOrRetrievePwd(userName, phone, code, pwd, phone1);
    }

}
