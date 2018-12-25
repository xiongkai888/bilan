package com.lanmei.bilan.widget;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanmei.bilan.R;
import com.lanmei.bilan.bean.TaskBean;
import com.lanmei.bilan.helper.SignInHelper;

/**
 * Created by xkai on 2018/6/27.
 */

public class SignDialog extends Dialog{

    public SignDialog(Context context,TaskBean bean) {
        //重写dialog默认的主题
        this(context, R.style.quick_option_dialog, bean);
    }

    public SignDialog(Context context, int themeResId,TaskBean bean) {
        super(context, themeResId);
        View convertView = getLayoutInflater().inflate(R.layout.dialog_sign, null);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCancelable(false);
        setContentView(convertView);

        RecyclerView recyclerView = (RecyclerView) convertView.findViewById(R.id.recyclerView);
        new SignInHelper(context,recyclerView,bean);
        TextView signTv = (TextView) convertView.findViewById(R.id.sign_tv);
        ImageView crossIv = (ImageView) convertView.findViewById(R.id.crossIv);
        ImageView rotateIv = (ImageView) convertView.findViewById(R.id.rotate_iv);
        ObjectAnimator animator = ObjectAnimator.ofFloat(rotateIv,"rotation",0,360);
        //旋转不停顿
        animator.setInterpolator(new LinearInterpolator());
        //设置动画重复次数
        animator.setRepeatCount(1000);
        //旋转时长
        animator.setDuration(3000);
        //开始旋转
        animator.start();
        signTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null){
                    listener.sign();
                }
                cancel();
            }
        });
        crossIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
//        getWindow().setGravity(Gravity.BOTTOM); //显示在底部

        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = d.getWidth(); //设置dialog的宽度为当前手机屏幕的宽度
        getWindow().setAttributes(p);
    }

    DoSignInListener listener;

    public void setDoSignInListener(DoSignInListener listener){
        this.listener = listener;
    }

    public interface DoSignInListener{
        void sign();//
    }
}
