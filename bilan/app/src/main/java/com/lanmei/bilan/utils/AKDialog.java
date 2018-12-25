package com.lanmei.bilan.utils;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanmei.bilan.R;
import com.lanmei.bilan.bean.TaskBean;
import com.lanmei.bilan.helper.SignInHelper;
import com.lanmei.bilan.widget.ChangePhoneView;
import com.xson.common.utils.StringUtils;

/**
 * Dialog工具类
 * Created by benio on 2015/10/11.
 */
public class AKDialog {

    public static AlertDialog.Builder getDialog(Context context) {
        return new AlertDialog.Builder(context);
    }

    public static ProgressDialog getProgressDialog(Context context, String message) {
        ProgressDialog waitDialog = new ProgressDialog(context);
        if (!TextUtils.isEmpty(message)) {
            waitDialog.setMessage(message);
        }
        return waitDialog;
    }

    /**
     * 提示信息Dialog
     */
    public static AlertDialog.Builder getMessageDialog(Context context, String msg) {
        return getMessageDialog(context, null, msg, null);
    }

    /**
     * 提示信息Dialog
     */
    public static AlertDialog.Builder getMessageDialog(Context context, String title, String msg) {
        return getMessageDialog(context, title, msg, null);
    }

    /**
     * 提示信息Dialog
     */
    public static AlertDialog.Builder getMessageDialog(Context context, String msg, DialogInterface.OnClickListener okListener) {
        return getMessageDialog(context, null, msg, okListener);
    }

    /**
     * 提示信息Dialog
     */
    public static AlertDialog.Builder getMessageDialog(Context context, String title, String msg, DialogInterface.OnClickListener okListener) {
        AlertDialog.Builder builder = getDialog(context);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        if (!TextUtils.isEmpty(msg)) {
            builder.setMessage(msg);
        }
        builder.setPositiveButton("确定", okListener);
        return builder;
    }

    /**
     * 确认对话框
     */
    public static AlertDialog.Builder getConfirmDialog(Context context, String msg,
                                                       DialogInterface.OnClickListener okListener) {
        return getConfirmDialog(context, null, msg, okListener, null);
    }

    /**
     * 确认对话框
     */
    public static AlertDialog.Builder getConfirmDialog(Context context, String title, String msg,
                                                       DialogInterface.OnClickListener okListener) {
        return getConfirmDialog(context, title, msg, okListener, null);
    }

    /**
     * 确认对话框
     */
    public static AlertDialog.Builder getConfirmDialog(Context context, String title, String msg,
                                                       DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener cancelListener) {
        AlertDialog.Builder builder = getDialog(context);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        if (!TextUtils.isEmpty(msg)) {
            builder.setMessage(msg);
        }
        builder.setPositiveButton("确定", okListener);
        builder.setNegativeButton("取消", cancelListener);
        return builder;
    }

    /**
     * 列表对话框
     */
    public static AlertDialog.Builder getSelectDialog(Context context, String[] arrays, DialogInterface.OnClickListener onClickListener) {
        return getSelectDialog(context, null, arrays, onClickListener);
    }

    /**
     * 列表对话框
     */
    public static AlertDialog.Builder getSelectDialog(Context context, String title,
                                                      String[] arrays, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setItems(arrays, onClickListener);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        return builder;
    }

    /**
     * 单选对话框
     */
    public static AlertDialog.Builder getSingleChoiceDialog(Context context, String[] arrays,
                                                            int selectIndex, DialogInterface.OnClickListener onClickListener) {
        return getSingleChoiceDialog(context, null, arrays, selectIndex, onClickListener);
    }

    /**
     * 单选对话框
     */
    public static AlertDialog.Builder getSingleChoiceDialog(Context context, String title, String[] arrays,
                                                            int selectIndex, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setSingleChoiceItems(arrays, selectIndex, onClickListener);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        // builder.setNegativeButton("取消", null);
        return builder;
    }

    public AKDialog() {
    }

    /**
     * 拍照、选择相册底部弹框提示
     *
     * @param context
     * @param activity
     * @param listener
     */
    public static void showBottomListDialog(Context context, Activity activity, final AlbumDialogListener listener) {
        final Dialog dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        View inflate = LayoutInflater.from(context).inflate(R.layout.album_dialog_layout, null);
        Button choosePhoto = (Button) inflate.findViewById(R.id.choosePhoto);
        Button takePhoto = (Button) inflate.findViewById(R.id.takePhoto);
        Button cancel = (Button) inflate.findViewById(R.id.btn_cancel);
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {//拍照
                    listener.photograph();
                }
                dialog.cancel();
            }
        });
        choosePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//相册
                if (listener != null) {
                    listener.photoAlbum();
                }
                dialog.cancel();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//取消
                dialog.cancel();
            }
        });
        dialog.setContentView(inflate);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        WindowManager m = activity.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        lp.width = d.getWidth();
        dialogWindow.setAttributes(lp);
        dialog.show();
    }

    public interface AlbumDialogListener {
        void photograph();//拍照

        void photoAlbum();//相册
    }

    public static void getAlertDialog(Context context, String content, final AlertDialogListener l) {
        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setMessage(content)
                .setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (l != null) {
                            l.yes();
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(false).create();
        dialog.show();
    }

    public interface AlertDialogListener {
        void yes();
    }


    /**
     * 提现  添加提现账户 弹框
     *
     * @param context
     * @param text1
     * @param text2
     * @return
     */
    public static AlertDialog getBottomDialog(Context context, String text1, String text2, final DialogOnClickListener dialogOnClickListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = View.inflate(context, R.layout.add_account_dialog, null);
        builder.setView(view);
        builder.setCancelable(true);
        final AlertDialog dialog = builder.create();
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        Button button1 = (Button) view.findViewById(R.id.button_text1);
        Button button2 = (Button) view.findViewById(R.id.button_text2);
        Button buttonCancel = (Button) view.findViewById(R.id.button_cancel);
        button1.setText(text1);//
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialogOnClickListener != null) {
                    dialogOnClickListener.OnClickTitle();
                    dialog.cancel();
                }
            }
        });
        if (!StringUtils.isEmpty(text2)) {
            button2.setText(text2);//
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (dialogOnClickListener != null) {
                        dialogOnClickListener.OnClickTitleSub();
                        dialog.cancel();
                    }
                }
            });
        } else {
            button2.setVisibility(View.GONE);//为空隐藏
        }

        buttonCancel.setOnClickListener(new View.OnClickListener() {//取消
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.show();
        return dialog;
    }

    public interface DialogOnClickListener {
        void OnClickTitle();

        void OnClickTitleSub();
    }

    public static AlertDialog getChangePhoneDialog(Context context, String title, String phone, String type, final ChangePhoneListener l) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        ChangePhoneView view = (ChangePhoneView) View.inflate(context, R.layout.dialog_change_phone, null);
        view.setTitle(title);
        view.setPhone(phone);
        view.setType(type);
        view.setChangePhoneListener(new ChangePhoneView.ChangePhoneListener() {
            @Override
            public void succeed(String newPhone) {
                if (l != null) {
                    l.succeed(newPhone);
                }
            }

            @Override
            public void unBound() {
                if (l != null) {
                    l.unBound();
                }
            }
        });
        builder.setView(view);
        builder.setCancelable(true);
        AlertDialog dialog = builder.create();
        return dialog;
    }


    public interface ChangePhoneListener {
        void succeed(String newPhone);//更换手机号

        void unBound();//解绑银行卡
    }

    //签到弹框
    public static void showSignDialog(final Context context, TaskBean bean, final DoSignInListener listener) {

        final Dialog dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_sign, null);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        new SignInHelper(context, recyclerView, bean);
        TextView signTv = (TextView) view.findViewById(R.id.sign_tv);
        ImageView crossIv = (ImageView) view.findViewById(R.id.crossIv);
        ImageView rotateIv = (ImageView) view.findViewById(R.id.rotate_iv);
        final ObjectAnimator animator = ObjectAnimator.ofFloat(rotateIv, "rotation", 0, 360);
        //旋转不停顿
        animator.setInterpolator(new LinearInterpolator());
        //设置动画重复次数
        animator.setRepeatCount(-1);
        //旋转时长
        animator.setDuration(5000);
        //开始旋转
        animator.start();
        dialog.setCancelable(false);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
//        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        WindowManager m = dialogWindow.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        lp.width = d.getWidth();
        dialogWindow.setAttributes(lp);
        dialog.show();
        signTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.sign();
                }
                animator.cancel();
                dialog.cancel();
            }
        });
        crossIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animator.cancel();
                dialog.cancel();
            }
        });
    }

    public interface DoSignInListener {
        void sign();//
    }
}
