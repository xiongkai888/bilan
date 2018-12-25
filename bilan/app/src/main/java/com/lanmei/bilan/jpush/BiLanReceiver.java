package com.lanmei.bilan.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.lanmei.bilan.BtcimApp;
import com.lanmei.bilan.MainActivity;
import com.xson.common.utils.L;
import com.xson.common.utils.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;


/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class BiLanReceiver extends BroadcastReceiver {

    public static final String TAG = "JIGUANG-Example";
    private int count = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Bundle bundle = intent.getExtras();
            L.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                L.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
                //send the Registration Id to your server...

            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                L.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
                //				processCustomMessage(context, bundle);

            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                L.d(TAG, "[MyReceiver] 接收到推送下来的通知");
                int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                L.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

                try {
                    count += 1;
                    BadgeUtil.setBadgeCount(context, count); //实现badge
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                L.d(TAG, "[MyReceiver] 用户点击打开了通知");
                String content = bundle.getString(JPushInterface.EXTRA_ALERT);
                MainActivity.showMainActivity(context, BtcimApp.getInstance());
                if (!StringUtils.isEmpty(content) && content.endsWith("@TASK")) {//跳到任务
//                    MainActivity.showTask(context);
                } else {//@ALL //打开自定义的Activity
//                    Intent i = new Intent(context, TestActivity.class);
//                    i.putExtras(bundle);
//                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    context.startActivity(i);
                }
            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                L.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
                L.d(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
            } else {
                L.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
            }
        } catch (Exception e) {

        }

    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    L.d(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    L.d(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

//    send msg to MainActivity
//    	private void processCustomMessage(Context context, Bundle bundle) {
//    		if (MainActivity.isForeground) {
//    			String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
//    			String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
//    			Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
//    			msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
//    			if (!ExampleUtil.isEmpty(extras)) {
//    				try {
//    					JSONObject extraJson = new JSONObject(extras);
//    					if (extraJson.length() > 0) {
//    						msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
//    					}
//    				} catch (JSONException e) {
//
//    				}
//
//    			}
//    			LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent);
//    		}
//    	}
}
