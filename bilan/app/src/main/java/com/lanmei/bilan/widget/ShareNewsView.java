package com.lanmei.bilan.widget;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lanmei.bilan.R;
import com.lanmei.bilan.utils.CommonUtils;
import com.lanmei.bilan.utils.FormatTime;
import com.lanmei.bilan.webviewpage.WebViewPhotoBrowserUtil;
import com.xson.common.utils.StringUtils;
import com.ybao.zxing.CreateDCode;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by xkai on 2018/7/2.
 */

public class ShareNewsView extends ScrollView{

    @InjectView(R.id.sudokuView)
    SudokuView sudokuView;
    @InjectView(R.id.logo_iv)
    ImageView logoIv;
    @InjectView(R.id.img_code)
    ImageView imgCode;
    @InjectView(R.id.time_tv)
    TextView timeTv;
//    @InjectView(R.id.content_tv)
//    TextView contentTv;
    @InjectView(R.id.webView)
    WebView webView;


    public ShareNewsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.inject(this);

    }


    /**
     *
     * @param content
     * @param time
     * @param list
     * @param type  1快讯2动态
     */
    public void share(final String content, String time, List<String> list, final String type){
        if (!StringUtils.isSame(type, CommonUtils.isOne)) {
            logoIv.setImageResource(R.mipmap.share_dialog_icon2);
        }
        WebViewPhotoBrowserUtil.photoBrowser(getContext(),webView,content);
//        if (StringUtils.isSame(CommonUtils.isOne,type)){
//            WebViewPhotoBrowserUtil.photoBrowser(getContext(),webView,content);
////            contentTv.setVisibility(GONE);
//        }else {
////            contentTv.setText(content);
//            webView.setVisibility(GONE);
//        }
        FormatTime formatTime = new FormatTime(time);
        timeTv.setText(formatTime.formatterTimeNoSeconds());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                imgCode.setImageBitmap(CreateDCode.CreateQRCode("http://h5.bilancaijing.com/index.html?uid=" + CommonUtils.getUserId(getContext()) + "&type=" + type, 1000));
            }
        }, 100);
        sudokuView.setListData(list);
    }

}
