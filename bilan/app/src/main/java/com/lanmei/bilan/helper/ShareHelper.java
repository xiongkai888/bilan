package com.lanmei.bilan.helper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lanmei.bilan.R;
import com.lanmei.bilan.event.TaskEvent;
import com.lanmei.bilan.webviewpage.FileUtils;
import com.umeng.socialize.Config;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.xson.common.app.BaseActivity;
import com.xson.common.utils.StringUtils;
import com.xson.common.utils.UIHelper;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by xkai on 2017/8/10.
 * 分享帮助类
 */

public class ShareHelper {

    private UMShareAPI mShareAPI;
    private Context context;
    String shareUrl = "";

    public ShareHelper(Context context) {
        this.context = context;
        //分享初始化
        mShareAPI = UMShareAPI.get(context);
    }


    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public void showShareDialog() {

        if (StringUtils.isEmpty(shareUrl)) {
            return;
        }
        Config.isJumptoAppStore = true;//其中qq 微信会跳转到下载界面进行下载，其他应用会跳到应用商店进行下载

        UMImage thumb = new UMImage(context, R.mipmap.logo);//资源文件  SHARE_MEDIA.SINA,SHARE_MEDIA.QQ,, SHARE_MEDIA.QZONE
        UMWeb web = new UMWeb(shareUrl);
        web.setTitle(context.getString(R.string.app_name));//标题
        web.setThumb(thumb);  //缩略图
        web.setDescription("币澜财经，全民狂欢");//描述

        ShareAction shareAction = new ShareAction((Activity) context);

        shareAction.withText("快来加入我们吧！")
                .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE)
//                .setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE)
                .withMedia(web)
                .setCallback(umShareListener);

        ShareBoardConfig config = new ShareBoardConfig();//新建ShareBoardConfig
        config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_CIRCULAR);
//                config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_CENTER);//设置位置
//                config.setTitleVisibility(false);
        config.setIndicatorVisibility(false);
//                config.setCancelButtonVisibility(false);
        shareAction.open(config);//传入分享面板中
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //分享开始的回调
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            shareMediaToastMessage(platform, "分享成功!", "收藏成功!");
            EventBus.getDefault().post(new TaskEvent());//调用任务界面中的方法taskEvent()
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            shareMediaToastMessage(platform, "分享失败!", "收藏失败!");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            shareMediaToastMessage(platform, "分享取消!", "收藏取消!");
        }
    };

    private void shareMediaToastMessage(SHARE_MEDIA platform, String s1, String s2) {
        if (platform == SHARE_MEDIA.WEIXIN || platform == SHARE_MEDIA.WEIXIN_CIRCLE) {
            UIHelper.ToastMessage(context, s1);
        } else {
            UIHelper.ToastMessage(context, s2);
        }
    }

    public void share(final ScrollView viewCapture) {
        // 设置style 控制默认dialog带来的边距问题
        final Dialog dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        View view = LayoutInflater.from(context).inflate(R.layout.customshare_layout, null);
        dialog.setContentView(view);
        dialog.show();
        LinearLayout llCapture = (LinearLayout) view.findViewById(R.id.ll_capture);//截图容器
        llCapture.addView(viewCapture);
        // 监听
        View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                switch (v.getId()) {

                    case R.id.view_share_weixin:
                        // 分享到微信
                        share(SHARE_MEDIA.WEIXIN, viewCapture);
                        break;
                    case R.id.view_share_weixinfriend:
                        // 分享到朋友圈
                        share(SHARE_MEDIA.WEIXIN_CIRCLE, viewCapture);
                        break;
                    case R.id.view_share_weixin_favorite:
                        //微信收藏
                        share(SHARE_MEDIA.WEIXIN_FAVORITE, viewCapture);
                        break;
                    case R.id.view_save:
                        //保存
                        savePhotoToLocal(shotScrollView(viewCapture),true);
                        break;
                    case R.id.share_cancel_btn:
                         //取消
                        break;
                }
                dialog.dismiss();
            }

        };
        ViewGroup mViewWeixin = (ViewGroup) view.findViewById(R.id.view_share_weixin);
        ViewGroup mViewPengyou = (ViewGroup) view.findViewById(R.id.view_share_weixinfriend);
        ViewGroup mViewWeixinFavorite = (ViewGroup) view.findViewById(R.id.view_share_weixin_favorite);
        ViewGroup viewSave = (ViewGroup) view.findViewById(R.id.view_save);
//        ViewGroup mViewqqzone = (ViewGroup) view.findViewById(R.id.share_qqzone);
        TextView mBtnCancel = (TextView) view.findViewById(R.id.share_cancel_btn);
        mViewWeixin.setOnClickListener(listener);
        mViewPengyou.setOnClickListener(listener);
        mViewWeixinFavorite.setOnClickListener(listener);
        viewSave.setOnClickListener(listener);//保存图片
//        mViewqqzone.setOnClickListener(listener);
        mBtnCancel.setOnClickListener(listener);

        // 设置相关位置，一定要在 show()之后
        Window window = dialog.getWindow();
//        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }

    /**
     * @param view，当前想要创建截图的View             //     * @param width，设置截图的宽度
     *                                       //     * @param height，设置截图的高度
     * @param scroll，如果为真则从View的当前滚动位置开始绘制截图
     * @param config，Bitmap的质量，比如ARGB_8888等
     * @return 截图的Bitmap
     */
    public Bitmap capture(View view, boolean scroll, Bitmap.Config config) {
        if (!view.isDrawingCacheEnabled()) {
            view.setDrawingCacheEnabled(true);
        }
        int width = view.getWidth();
        int height = view.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);
        bitmap.eraseColor(Color.WHITE);

        Canvas canvas = new Canvas(bitmap);
        int left = view.getLeft();
        int top = view.getTop();
        if (scroll) {
            left = view.getScrollX();
            top = view.getScrollY();
        }
        int status = canvas.save();
        canvas.translate(-left, -top);

        float scale = width / view.getWidth();
        canvas.scale(scale, scale, left, top);

        view.draw(canvas);
        canvas.restoreToCount(status);

        Paint alphaPaint = new Paint();
        alphaPaint.setColor(Color.TRANSPARENT);

        canvas.drawRect(0f, 0f, 1f, height, alphaPaint);
        canvas.drawRect(width - 1f, 0f, width, height, alphaPaint);
        canvas.drawRect(0f, 0f, width, 1f, alphaPaint);
        canvas.drawRect(0f, height - 1f, width, height, alphaPaint);
        canvas.setBitmap(null);

        return bitmap;
    }

    /**
     * 普通截屏的实现
     *
     * @param v
     * @return
     */
    public Bitmap getViewBp(View v) {
        if (null == v) {
            return null;
        }
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache();
        if (Build.VERSION.SDK_INT >= 11) {
            v.measure(View.MeasureSpec.makeMeasureSpec(v.getWidth(),
                    View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(
                    v.getHeight(), View.MeasureSpec.EXACTLY));
            v.layout((int) v.getX(), (int) v.getY(),
                    (int) v.getX() + v.getMeasuredWidth(),
                    (int) v.getY() + v.getMeasuredHeight());
        } else {
            v.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        }
        Bitmap b = Bitmap.createBitmap(v.getDrawingCache(), 0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());

        v.setDrawingCacheEnabled(false);
        v.destroyDrawingCache();
        return b;
    }

    /**
     * ScrollView截屏
     *
     * @param scrollView
     * @return
     */
    public Bitmap shotScrollView(ScrollView scrollView) {
        int h = 0;
        Bitmap bitmap = null;
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
            scrollView.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));
        }
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h, Bitmap.Config.RGB_565);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        return bitmap;
    }

    public void share(SHARE_MEDIA share_media, ScrollView view) {
//        UMImage image = new UMImage(context, capture(view, true, Bitmap.Config.ARGB_8888));//资源文件
//        UMImage image = new UMImage(context, getViewBp(view));//资源文件
        Bitmap bitmap = shotScrollView(view);
        UMImage image = new UMImage(context, bitmap);//资源文件
        new ShareAction((BaseActivity) context)
                .setPlatform(share_media)//传入平台
                // .withText("hello")//分享内容
                .withMedia(image)
                .setCallback(umShareListener)//回调监听器
                .share();
        savePhotoToLocal(bitmap,false);
    }

    private void savePhotoToLocal(Bitmap bitmap,final boolean isHint) {
        FileUtils.savePhoto(context, bitmap, new FileUtils.SaveResultCallback() {
            @Override
            public void onSavedSuccess() {
                ((BaseActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isHint)
                        UIHelper.ToastMessage(context, "保存成功");
                    }
                });
            }

            @Override
            public void onSavedFailed() {
                ((BaseActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isHint)
                        UIHelper.ToastMessage(context, "保存失败");
                    }
                });
            }
        });
    }

    public void onDestroy() {
        mShareAPI.get(context).release();
    }

    /**
     * 结果返回
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mShareAPI.get(context).onActivityResult(requestCode, resultCode, data);
    }

}
