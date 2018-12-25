package com.lanmei.bilan.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.lanmei.bilan.R;
import com.xson.common.helper.ImageHelper;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by xkai on 2018/7/2.
 * 我的 邀请 海报
 */

public class InvitePosterView extends ScrollView{

    @InjectView(R.id.img_code)
    ImageView imgCode;


    public InvitePosterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.inject(this);

    }


    public void share(String qr){
        ImageHelper.load(getContext(),qr,imgCode,null,true,R.mipmap.default_pic,R.mipmap.default_pic);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                imgCode.setImageBitmap(CreateDCode.CreateQRCode("http://h5.bilancaijing.com/index.html?uid=" + CommonUtils.getUserId(getContext()) + "&type=" + type, 1000));
//            }
//        }, 100);
    }

}
