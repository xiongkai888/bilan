package com.hyphenate.chatuidemo.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.chatuidemo.DemoHelper;
import com.hyphenate.chatuidemo.runtimepermissions.PermissionsManager;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.widget.EaseChatPrimaryMenuBase;
import com.lanmei.bilan.BtcimApp;
import com.lanmei.bilan.R;
import com.lanmei.bilan.bean.MarketChangBean;
import com.lanmei.bilan.bean.UserBean;
import com.lanmei.bilan.helper.UserHelper;
import com.lanmei.bilan.utils.CommonUtils;
import com.lanmei.bilan.utils.FormatTime;
import com.lanmei.bilan.utils.SharedAccount;
import com.xson.common.utils.L;
import com.xson.common.utils.StringUtils;

import static com.hyphenate.chatuidemo.ui.ConversationListFragment.REFRESH_CONVERSATIONLIST;

/**
 * chat activity，EaseChatFragment was used {@link #}
 *
 */
public class ChatActivity extends BaseActivity{
    public static ChatActivity activityInstance;
    private EaseChatFragment chatFragment;
    public String toChatUsername;
    boolean isOnLine;//是否是在线客服
    EaseChatPrimaryMenuBase.EaseChatPrimaryMenuListener listener;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.em_activity_chat);

        IntentFilter filter = new IntentFilter();
        filter.addAction(REFRESH_CONVERSATIONLIST); //刷新环信会话列表广播
        registerReceiver(mReceiver, filter);

        activityInstance = this;
        //get user id or group id
        toChatUsername = getIntent().getExtras().getString("userId");
        isOnLine = getIntent().getExtras().getBoolean("isOnLine");
        //use EaseChatFratFragment
        chatFragment = new ChatFragment();
        //pass parameters to chat fragment
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityInstance = null;
        unregisterReceiver(mReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isOnLine){
            isOnLine = !isOnLine;
            chatFragment.setTitlebar("在线客服");
//            listener = chatFragment.getChatPrimaryMenuListener();
//            if (com.xson.common.utils.StringUtils.isEmpty(listener)){
//                return;
//            }
            MarketChangBean bean  = (MarketChangBean)getIntent().getExtras().getSerializable("bean");
            if (StringUtils.isEmpty(bean)){
                return;
            }
            String cudo = bean.getCudo();
            if (StringUtils.isSame(cudo,"0")){
                cudo = "出售";
            }else {
                cudo = "购进";
            }
            FormatTime time = new FormatTime(bean.getAddtime());
            String uid = BtcimApp.HX_USER_Head+ CommonUtils.getUserId(this);
            String content = String.format(getString(R.string.send_info), UserHelper.getInstance(this).getUserBean().getNickname(),cudo,bean.getClassname(),bean.getNum(),bean.getPrice(),time.formatterTime());
//            listener.onSendBtnClicked(String.format(getString(R.string.send_info),bean.getUsername(),cudo,bean.getClassname(),bean.getNum(),bean.getPrice(),time.formatterTime()));
            EMMessage message = EMMessage.createSendMessage(EMMessage.Type.TXT);
            message.addBody(new EMTextMessageBody(content));
            message.setFrom(SharedAccount.getInstance(this).getServiceId());
            message.setTo(uid);
            message.setDirection(EMMessage.Direct.RECEIVE);
            EMClient.getInstance().chatManager().sendMessage(message);
            message.setMessageStatusCallback(new EMCallBack() {
                @Override
                public void onSuccess() {
                    L.d("beanre","自动发送成功");
                }

                @Override
                public void onError(int i, String s) {
                    L.d("beanre","自动发送失败");
                }

                @Override
                public void onProgress(int i, String s) {

                }
            });
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
    	// make sure only one chat activity is opened
        String username = intent.getStringExtra("userId");
        if (toChatUsername.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }

    }
    
    @Override
    public void onBackPressed() {
        chatFragment.onBackPressed();
//        if (EasyUtils.isSingleActivity(this)) {
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
//        }
    }
    
    public String getToChatUsername(){
        return toChatUsername;
    }

    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
        @NonNull int[] grantResults) {
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            String userName = intent.getStringExtra("username");
            if (REFRESH_CONVERSATIONLIST.equals(action)) {
                if (DemoHelper.getInstance().userBeanMap.containsKey(userName)){
                    UserBean bean = DemoHelper.getInstance().userBeanMap.get(userName);
                    if (bean != null){
                        chatFragment.setTitlebar(bean.getNickname());
                    }
                }
            }
        }
    };

}
