package com.lanmei.bilan.ui.goods;

import android.os.Bundle;

import com.lanmei.bilan.R;
import com.lanmei.bilan.webviewpage.WebViewPhotoBrowserUtil;
import com.lanmei.bilan.widget.ItemWebView;
import com.xson.common.app.BaseFragment;

import butterknife.InjectView;


/**
 * 图文详情webview的Fragment
 */
public class GoodsInfoWebFragment extends BaseFragment {

    @InjectView(R.id.detail_webView)
    ItemWebView detailWebView;

    
    @Override
    public int getContentViewId() {
        return R.layout.fragment_item_info_web;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle == null){
            return;
        }
        String content = bundle.getString("content");
        WebViewPhotoBrowserUtil.photoBrowser(context, detailWebView, content);
    }

}
