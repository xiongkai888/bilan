package com.lanmei.bilan.ui.market.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.lanmei.bilan.R;
import com.lanmei.bilan.api.BiClassApi;
import com.lanmei.bilan.api.BiClassPublishApi;
import com.lanmei.bilan.bean.BiClassBean;
import com.lanmei.bilan.event.PublishBiClassEvent;
import com.lanmei.bilan.utils.CommonUtils;
import com.lanmei.bilan.widget.KaiSpinner;
import com.xson.common.app.BaseActivity;
import com.xson.common.bean.BaseBean;
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
 * 场外发布
 */
public class MarketChangPublishActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    CenterTitleToolbar toolbar;
    @InjectView(R.id.out_choose_iv)
    ImageView outChooseIv;
    @InjectView(R.id.in_choose_iv)
    ImageView inChooseIv;
    @InjectView(R.id.spinner)
    KaiSpinner mSpinner;
    @InjectView(R.id.num_et)
    EditText numEt;
    @InjectView(R.id.price_et)
    EditText priceEt;

    @Override
    public int getContentViewId() {
        return R.layout.activity_market_chang_publish;
    }

    List<BiClassBean> biClasslist;
    String classname;
    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("外场发布");
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.mipmap.back);

        BiClassApi api = new BiClassApi();
        HttpClient.newInstance(this).loadingRequest(api, new BeanRequest.SuccessListener<NoPageListBean<BiClassBean>>() {
            @Override
            public void onResponse(NoPageListBean<BiClassBean> response) {
                 biClasslist = response.data;
                setSpinner(CommonUtils.getBiClassStringList(biClasslist));
            }
        });
    }



    int cudo = 0;

    @OnClick({R.id.ll_out, R.id.ll_in})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_out:
                cudo = 0;
                outChooseIv.setImageResource(R.mipmap.choose_on);
                inChooseIv.setImageResource(R.mipmap.choose_off);
                break;
            case R.id.ll_in:
                cudo = 1;
                outChooseIv.setImageResource(R.mipmap.choose_off);
                inChooseIv.setImageResource(R.mipmap.choose_on);
                break;
        }
    }

    private void setSpinner(List<String> list) {
        mSpinner.setListData(list);
        mSpinner.setOnItemSelectedListener(new KaiSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(String itemStr) {
                classname = itemStr;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_publish, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_publish:
                //发布的时候
                loadPublish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadPublish() {
        if (StringUtils.isEmpty(biClasslist)){
            UIHelper.ToastMessage(this,"没有可选的交易币种，无法提交");
            return;
        }
        String num = CommonUtils.getStringByEditText(numEt);
        if (StringUtils.isEmpty(num)){
            UIHelper.ToastMessage(this,"请输入数量");
            return;
        }
        String price = CommonUtils.getStringByEditText(priceEt);
        if (StringUtils.isEmpty(price)){
            UIHelper.ToastMessage(this,"请输入价格");
            return;
        }
        BiClassPublishApi api = new BiClassPublishApi();
        api.classid = CommonUtils.getBiClassId(biClasslist,classname);
        api.cudo = cudo+"";
        api.num = num;
        api.price = price;
        api.userid = api.getUserId(this);
        HttpClient.newInstance(this).loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                UIHelper.ToastMessage(MarketChangPublishActivity.this,getString(R.string.publish_succeed));
                EventBus.getDefault().post(new PublishBiClassEvent());
                onBackPressed();
            }
        });
    }
}
