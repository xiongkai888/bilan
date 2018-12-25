package com.lanmei.bilan.ui.mine.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.lanmei.bilan.R;
import com.lanmei.bilan.api.BurseApi;
import com.lanmei.bilan.helper.UserHelper;
import com.xson.common.app.BaseActivity;
import com.xson.common.bean.BaseBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.utils.StringUtils;
import com.xson.common.utils.UIHelper;
import com.xson.common.widget.CenterTitleToolbar;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 绑定银行卡
 */
public class BoundKaActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    CenterTitleToolbar mToolbar;
    @InjectView(R.id.name_et)
    EditText mNameEt;//开户名
    @InjectView(R.id.ka_et)
    EditText mKaEt;//银行卡号
    @InjectView(R.id.spinner)
    Spinner mSpinner;
    String mKa;

    @Override
    public int getContentViewId() {
        return R.layout.activity_bound_ka;
    }


    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        setSupportActionBar(mToolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle("绑定银行卡");
        actionbar.setHomeAsUpIndicator(R.mipmap.back);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] arry = getResources().getStringArray(R.array.ka);
                mKa = arry[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @OnClick(R.id.bt_binding)
    public void showBinding(){//立即绑定
        String name = mNameEt.getText().toString().trim();
        if (StringUtils.isEmpty(name)){
            UIHelper.ToastMessage(this,getString(R.string.input_kaihu));
            return;
        }
        String kaHao = mKaEt.getText().toString().trim();
        if (StringUtils.isEmpty(kaHao)){
            UIHelper.ToastMessage(this,getString(R.string.input_brank_num));
            return;
        }

        HttpClient httpClient = HttpClient.newInstance(getContext());
        BurseApi api = new BurseApi();
        api.token = UserHelper.getInstance(getContext()).getToken();
        api.banks_name = mKa;
        api.banks_no = kaHao;
        api.realname = name;
        httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                if (isFinishing()){
                    return;
                }
                UIHelper.ToastMessage(BoundKaActivity.this,"绑定成功");
                setResult(RESULT_OK);
                finish();
            }
        });
    }
}
