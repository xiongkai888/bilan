package com.lanmei.bilan.ui.mine.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.widget.TextView;

import com.lanmei.bilan.R;
import com.lanmei.bilan.api.AboutApi;
import com.lanmei.bilan.bean.AboutUsBean;
import com.lanmei.bilan.webviewpage.WebViewPhotoBrowserUtil;
import com.xson.common.app.BaseActivity;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;

import java.util.List;

import butterknife.InjectView;

/**
 * 关于我们
 */
public class AboutBticmActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.about_tv)
    TextView mAboutTv;
    @InjectView(R.id.web_view)
    WebView webView;

    @Override
    public int getContentViewId() {
        return R.layout.activity_about_btcim;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        setSupportActionBar(mToolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle(R.string.about_btcim);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.mipmap.back);

        AboutApi api = new AboutApi();
        api.title = "关于我们";
        HttpClient.newInstance(this).loadingRequest(api, new BeanRequest.SuccessListener<NoPageListBean<AboutUsBean>>() {
            @Override
            public void onResponse(NoPageListBean<AboutUsBean> response) {
                if (isFinishing()){
                    return;
                }
                List<AboutUsBean> list = response.data;
                if (com.xson.common.utils.StringUtils.isEmpty(list)){
                    return;
                }
                AboutUsBean bean = list.get(0);
                mAboutTv.setText(bean.getTitle());
                WebViewPhotoBrowserUtil.photoBrowser(AboutBticmActivity.this, webView, bean.getContent());
            }
        });
    }

}
