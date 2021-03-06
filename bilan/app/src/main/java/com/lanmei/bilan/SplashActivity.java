package com.lanmei.bilan;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.lanmei.bilan.adapter.GuideViewPagerAdapter;
import com.lanmei.bilan.utils.SharedAccount;
import com.xson.common.utils.IntentUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导页、启动也
 */
public class SplashActivity extends AppCompatActivity {

    private ViewPager vp;
    private GuideViewPagerAdapter adapter;
    private List<View> views;
    private ImageView mSkipIv;//立即体验
    private ImageView mLaunchIv;//启动图片
    int[] guide = new int[]{R.mipmap.guidance1,
//            R.mipmap.guidance2,
            R.mipmap.guidance3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏
        setContentView(R.layout.activity_splash);
        boolean isFirstLogin = SharedAccount.getInstance(SplashActivity.this).isFirstLogin();
        if (!isFirstLogin) {//第一次进入该应用
            initViewPager();
        } else {
            mLaunchIv = (ImageView) findViewById(R.id.launch_iv);
            mLaunchIv.setVisibility(View.VISIBLE);
            // 如果不是第一次启动app，则正常显示启动屏
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    enterHomeActivity();
                }
            }, 1000);
        }

    }

    private void enterHomeActivity() {
//        if (CommonUtils.isLogin(this)) {
//            IntentUtil.startActivity(this, MainActivity.class);
//        }
        IntentUtil.startActivity(this, MainActivity.class);
        finish();
    }

    private void initViewPager() {
        vp = (ViewPager) findViewById(R.id.vp_guide);
        mSkipIv = (ImageView) findViewById(R.id.skip_tv);
        mSkipIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedAccount.getInstance(SplashActivity.this).setNoFirstLogin(true);
                enterHomeActivity();
            }
        });
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1) {
                    mSkipIv.setVisibility(View.VISIBLE);
                } else {
                    mSkipIv.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        // 初始化引导页视图列表
        views = new ArrayList<View>();
        for (int i = 0; i < 2; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.guid_view, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
            imageView.setImageResource(guide[i]);
            views.add(view);
        }
        vp = (ViewPager) findViewById(R.id.vp_guide);
        // 初始化adapter
        adapter = new GuideViewPagerAdapter(views);
        vp.setAdapter(adapter);
    }
}
