package com.lanmei.bilan.ui.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.webkit.WebView;

import com.lanmei.bilan.R;
import com.lanmei.bilan.api.BlApi;
import com.lanmei.bilan.bean.BlBean;
import com.lanmei.bilan.webviewpage.WebViewPhotoBrowserUtil;
import com.xson.common.app.BaseActivity;
import com.xson.common.bean.DataBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.utils.L;
import com.xson.common.utils.StringUtils;
import com.xson.common.widget.CenterTitleToolbar;

import butterknife.InjectView;

public class BlActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    CenterTitleToolbar mToolbar;
    @InjectView(R.id.webView)
    WebView webView;
    String id;
//    String name;

    @Override
    public int getContentViewId() {
        return R.layout.activity_bl;
    }

    @Override
    public void initIntent(Intent intent) {
        super.initIntent(intent);
        Bundle bundle = intent.getBundleExtra("bundle");
        if (bundle != null){
            id = bundle.getString("id");
//            name = bundle.getString("name");
        }
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        setSupportActionBar(mToolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
//        actionbar.setTitle(name);
        actionbar.setTitle("");
        actionbar.setHomeAsUpIndicator(R.mipmap.back);

        load(actionbar);
    }

    public void load(final ActionBar actionbar) {
        BlApi api = new BlApi();
        api.id = id;
        HttpClient.newInstance(getContext()).loadingRequest(api, new BeanRequest.SuccessListener<DataBean<BlBean>>() {
            @Override
            public void onResponse(DataBean<BlBean> response) {
                if (isFinishing()){
                    return;
                }
                BlBean bean = response.data;
                if (StringUtils.isEmpty(bean)){
                    return;
                }
                actionbar.setTitle(bean.getTitle());
                WebViewPhotoBrowserUtil.photoBrowser(getContext(), webView, bean.getContent());
                L.d("BeanRequest",bean.getTitle()+","+bean.getContent());
            }
        });
    }

}
