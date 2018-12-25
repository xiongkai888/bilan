package com.lanmei.bilan.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.hyphenate.chatuidemo.Constant;
import com.hyphenate.chatuidemo.ui.ChatActivity;
import com.lanmei.bilan.BtcimApp;
import com.lanmei.bilan.R;
import com.lanmei.bilan.api.GetUserInfoApi;
import com.lanmei.bilan.api.SysSettingsApi;
import com.lanmei.bilan.bean.BiClassBean;
import com.lanmei.bilan.bean.InviteBean;
import com.lanmei.bilan.bean.UserBean;
import com.lanmei.bilan.event.SetUserInfoEvent;
import com.lanmei.bilan.helper.UserHelper;
import com.lanmei.bilan.ui.login.LoginActivity;
import com.lanmei.bilan.webviewpage.PhotoBrowserActivity;
import com.xson.common.bean.BaseBean;
import com.xson.common.bean.DataBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.utils.IntentUtil;
import com.xson.common.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CommonUtils {

    public final static String isOne = "1";
    public final static String isZero = "0";
    public final static String isTwo = "2";

    public static int quantity = 3;
    public static final String OTHER_MONEY = "其他金额";  //

    /**
     * 账号类型
     */
    public static final String User_Type = "User_Type";

    /**
     * List<String> 传化未String[]
     *
     * @param list
     * @return
     */
    public static String[] getStringArr(List<String> list) {
        String[] arr = null;
        if (list != null && list.size() > 0) {
            int size = list.size();
            arr = new String[size];
            for (int i = 0; i < size; i++) {
                arr[i] = list.get(i);
            }
        }
        return arr;
    }
    /**
     * List<String> 传化未String
     *
     * @param list
     * @return
     */
    public static String getStringByList(List<String> list) {
        String pics = "";
        if (StringUtils.isEmpty(list)) {
            return pics;
        }
        for (String pic : list) {
            pics = pics + pic + ",";
        }
        return StringUtils.isEmpty(pics) ? pics : getSubString(pics);
    }

    /**
     * 去掉后面最后一个字符
     *
     * @param decs
     * @return
     */
    public static String getSubString(String decs) {
        if (StringUtils.isEmpty(decs)) {
            return "";
        }
        return decs.substring(0, decs.length() - 1);
    }

    public static String getString(BaseBean response) {
        return StringUtils.isEmpty(response.getMsg()) ? response.getInfo() : response.getMsg();
    }

    /**
     * 字符串转换成 List<String>
     *
     * @param pic "dfasf,fasdfa,fasdfa"
     * @return
     */
    public static List<String> getListString(String pic) {
        if (StringUtils.isEmpty(pic)) {
            return null;
        }
        String[] arr = pic.split(",");
        List<String> list = new ArrayList<>();
        if (arr != null && arr.length > 0) {
            int size = arr.length;
            for (int i = 0; i < size; i++) {
                list.add(arr[i]);
            }
        }
        return list;
    }
    /**
     * 字符串转换成 List<String>
     *
     * @param pic "dfasf、fasdfa、fasdfa"
     * @return
     */
    public static List<String> getListStringBy(String pic) {
        if (StringUtils.isEmpty(pic)) {
            return null;
        }
        String[] arr = pic.split("、");
        List<String> list = new ArrayList<>();
        if (arr != null && arr.length > 0) {
            int size = arr.length;
            for (int i = 0; i < size; i++) {
                list.add(arr[i]);
            }
        }
        return list;
    }



    public static boolean isWifi(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }


    /**
     * 获取TextView 字符串
     *
     * @param textView
     * @return
     */
    public static String getStringByTextView(TextView textView) {
        return textView.getText().toString().trim();
    }

    /**
     * 获取EditText 字符串
     *
     * @param editText
     * @return
     */
    public static String getStringByEditText(EditText editText) {
        return editText.getText().toString().trim();
    }


    /**
     * @param context 关闭输入法，需要一个activity
     */
    public static void closeInputMethod(Activity context) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            // TODO: handle exception
            Log.d("beanre", "关闭输入法异常");
        }
    }


    public static boolean isLogin(Context context) {
        if (!UserHelper.getInstance(context).hasLogin()) {
            IntentUtil.startActivity(context, LoginActivity.class);
            return false;
        }
        return true;
    }

    /**
     * 获取年月日
     */
    public static String getData() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH);
    }

    //币种分类列表
    public static List<String> getBiClassStringList(List<BiClassBean> list) {
        List<String> stringList = new ArrayList<>();
        if (StringUtils.isEmpty(list)) {
            return stringList;
        }
        for (BiClassBean bean : list) {
            if (!StringUtils.isEmpty(bean) && !StringUtils.isEmpty(bean.getClassname())) {
                stringList.add(bean.getClassname());
            }
        }
        return stringList;
    }

    //币种分类列表
    public static String getBiClassId(List<BiClassBean> list, String name) {
        if (StringUtils.isEmpty(list) || StringUtils.isEmpty(name)) {
            return "";
        }
        for (BiClassBean bean : list) {
            if (!StringUtils.isEmpty(bean) && StringUtils.isSame(bean.getClassname(), name)) {
                return bean.getId();
            }
        }
        return "";
    }


    //加载用户数据
    public static void loadUserInfo(final Context context, final LoadUserInfoListener l) {
        HttpClient httpClient = HttpClient.newInstance(context);
        GetUserInfoApi api = new GetUserInfoApi();
        api.uid = api.getUserId(context);
        httpClient.request(api, new BeanRequest.SuccessListener<DataBean<UserBean>>() {
            @Override
            public void onResponse(DataBean<UserBean> response) {
                if (context == null){
                    return;
                }
                if (l != null) {
                    l.succeed(response.data);
                }
                UserHelper.getInstance(context).saveBean(response.data);
                EventBus.getDefault().post(new SetUserInfoEvent());
            }
        });
    }

    LoadUserInfoListener l;

    public interface LoadUserInfoListener {
        void succeed(UserBean bean);
    }

    public static String getUserId(Context context) {
        UserBean bean = UserHelper.getInstance(context).getUserBean();
        if (StringUtils.isEmpty(bean)) {
            return "";
        }
        return bean.getId();
    }

    public static UserBean getUserBean(Context context) {
        return UserHelper.getInstance(context).getUserBean();
    }


    /**
     * 启动聊天界面
     *
     * @param context
     * @param userId  聊天对方的id
     * @param isGroup 是不是群聊
     */
    public static void startChatActivity(Context context, String userId, boolean isGroup) {
        Intent intent = new Intent(context, ChatActivity.class);
        if (isGroup) {
            intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_GROUP);
        } else {
            userId = BtcimApp.HX_USER_Head + userId;
        }
        intent.putExtra(Constant.EXTRA_USER_ID, userId);
        context.startActivity(intent);
    }

    /**
     * 判断是不是自己
     *
     * @param context
     * @param uid
     * @return
     */
    public static boolean isSelf(Context context, String uid) {
        return StringUtils.isSame(uid, getUserId(context));
    }

    /**
     * 设置字体的背景和颜色，文字。
     *
     * @param context
     * @param type     0或1
     * @param textView
     * @param strId
     * @param strIdEd
     */
    public static void setTextViewType(Context context, String type, TextView textView, int strId, int strIdEd) {
        textView.setText(StringUtils.isSame(CommonUtils.isZero,type)?strId:strIdEd);
        textView.setTextColor(StringUtils.isSame(CommonUtils.isZero,type)?context.getResources().getColor(R.color.white):context.getResources().getColor(R.color.color999));
        textView.setBackground(StringUtils.isSame(CommonUtils.isZero,type)?context.getResources().getDrawable(R.drawable.ming):context.getResources().getDrawable(R.drawable.ming_on));
    }

    /**
     * 加载系统信息（分享）
     *
     * @param context
     * @param l
     */
    public static void loadSystem(final Context context, final LoadSystemListener l) {
        SysSettingsApi api = new SysSettingsApi();
        api.uid = api.getUserId(context);
        HttpClient.newInstance(context).request(api, new BeanRequest.SuccessListener<DataBean<InviteBean>>() {
            @Override
            public void onResponse(DataBean<InviteBean> response) {
                if (context == null) {
                    return;
                }
                InviteBean bean = response.data;
                if (l != null) {
                    l.loadSystem(bean);
                }
                if (bean != null) {
                    SharedAccount.getInstance(context).saveInviteBean(bean);
                }

            }
        });
    }

    public interface LoadSystemListener {
        void loadSystem(InviteBean bean);
    }


    /**
     * HTML格式转化为一般的String类型
     *
     * @param s
     * @return
     */
    public static String convert2HTML(String s) {
        if (StringUtils.isEmpty(s)) {
            return "";
        }
        if (s.contains("<br />")) {
            s = s.replace("<p>", "");
            s = s.replace("</p>", "");
            s = s.replace("<br />", "");
            s = s.replace("</div>", "");
            s = s.replace("<div>", "");
        }
        return s;
    }


    /**
     * 浏览图片
     *
     * @param context
     * @param arry     图片地址数组
     * @param imageUrl 点击的图片地址
     */
    public static void showPhotoBrowserActivity(Context context, List<String> arry, String imageUrl) {
        Intent intent = new Intent();
        intent.putExtra("imageUrls", (Serializable) arry);
        intent.putExtra("curImageUrl", imageUrl);
        intent.setClass(context, PhotoBrowserActivity.class);
        context.startActivity(intent);
    }
}
