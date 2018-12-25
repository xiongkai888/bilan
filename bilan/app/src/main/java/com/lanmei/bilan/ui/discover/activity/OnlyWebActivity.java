package com.lanmei.bilan.ui.discover.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.webkit.WebView;

import com.lanmei.bilan.R;
import com.lanmei.bilan.webviewpage.WebViewPhotoBrowserUtil;
import com.xson.common.app.BaseActivity;
import com.xson.common.widget.CenterTitleToolbar;

import butterknife.InjectView;

/**
 * 项目速递详情
 */
public class OnlyWebActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    CenterTitleToolbar toolbar;
    @InjectView(R.id.web_view)
    WebView webView;

    @Override
    public int getContentViewId() {
        return R.layout.activity_only_web;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.mipmap.back);
        Bundle bundle = getIntent().getBundleExtra("bundle");
        if (bundle == null){
            return;
        }
        WebViewPhotoBrowserUtil.photoBrowser(this, webView, bundle.getString("content"));
        actionbar.setTitle(bundle.getString("title"));
    }


}
