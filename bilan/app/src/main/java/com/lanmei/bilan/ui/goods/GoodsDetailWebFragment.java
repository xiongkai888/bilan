package com.lanmei.bilan.ui.goods;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lanmei.bilan.R;
import com.xson.common.app.BaseFragment;

import butterknife.InjectView;


/**
 * 图文详情webview的Fragment
 */
public class GoodsDetailWebFragment extends BaseFragment {
    
    @InjectView(R.id.webView)
    WebView webView;
    
    private WebSettings webSettings;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_item_detail_web;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        init();
    }

    private void init() {
        initWebView();
        initView();
    }

    public void initView() {
        String url = "http://m.okhqb.com/item/description/1000334264.html?fromApp=true";
        webView.setFocusable(false);
        webView.loadUrl(url);
    }

    private void initWebView() {
        webSettings = webView.getSettings();
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setBlockNetworkImage(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.setWebViewClient(new GoodsDetailWebViewClient());
    }

    private class GoodsDetailWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            webSettings.setBlockNetworkImage(false);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return true;
        }
    }
}
