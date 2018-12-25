package com.lanmei.bilan.ui.friend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.chatuidemo.ui.VideoCallActivity;
import com.lanmei.bilan.BtcimApp;
import com.lanmei.bilan.R;
import com.lanmei.bilan.api.BriefApi;
import com.lanmei.bilan.api.FollowApi;
import com.lanmei.bilan.bean.BrifBean;
import com.lanmei.bilan.bean.UserBean;
import com.lanmei.bilan.event.AttentionFriendEvent;
import com.lanmei.bilan.ui.mine.activity.TransferActivity;
import com.lanmei.bilan.utils.AKDialog;
import com.lanmei.bilan.utils.CommonUtils;
import com.lanmei.bilan.webviewpage.WebViewPhotoBrowserUtil;
import com.xson.common.app.BaseActivity;
import com.xson.common.bean.BaseBean;
import com.xson.common.bean.DataBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.helper.ImageHelper;
import com.xson.common.utils.IntentUtil;
import com.xson.common.utils.StringUtils;
import com.xson.common.utils.UIHelper;
import com.xson.common.widget.CenterTitleToolbar;
import com.xson.common.widget.CircleImageView;

import org.greenrobot.eventbus.EventBus;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 个人资料(名人堂资料)
 */
public class MingDataActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    CenterTitleToolbar mToolbar;
    @InjectView(R.id.head_iv)
    CircleImageView headIv;
    @InjectView(R.id.name_tv)
    TextView nameTv;
    @InjectView(R.id.content_tv)
    TextView contentTv;
    @InjectView(R.id.attention_tv)
    TextView attentionTv;
    @InjectView(R.id.web_view)
    WebView webView;
    @InjectView(R.id.ll_ming)
    LinearLayout llMing;
    @InjectView(R.id.signature_tv)
    TextView signatureTv;
    @InjectView(R.id.area_tv)
    TextView areaTv;
    @InjectView(R.id.ll_friends)
    LinearLayout llFriends;

    UserBean bean;


    @Override
    public int getContentViewId() {
        return R.layout.activity_ming_data;
    }

    @Override
    public void initIntent(Intent intent) {
        super.initIntent(intent);
        Bundle bundle = intent.getBundleExtra("bundle");
        bean = (UserBean) bundle.getSerializable("bean");
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        setSupportActionBar(mToolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle("详细资料");
        actionbar.setHomeAsUpIndicator(R.mipmap.back);

        if (bean == null) {
            return;
        }
        contentTv.setText("ID："+bean.getId());
        nameTv.setText(bean.getNickname());
        ImageHelper.load(this, bean.getPic(), headIv, null, true, R.mipmap.default_pic, R.mipmap.default_pic);
        if (StringUtils.isSame(CommonUtils.isOne,bean.getUser_type())){//名人堂
            llMing.setVisibility(View.VISIBLE);
            llFriends.setVisibility(View.GONE);
        }else {
            llMing.setVisibility(View.GONE);
            llFriends.setVisibility(View.VISIBLE);
            signatureTv.setText(bean.getSignature());
            areaTv.setText(bean.getArea());
        }
        loadDetails();//加载图文详情
    }

    int guanZhu;

    private void loadDetails() {
        BriefApi api = new BriefApi();
        api.uid = bean.getId();
        api.fid = api.getUserId(this);
        HttpClient.newInstance(this).loadingRequest(api, new BeanRequest.SuccessListener<DataBean<BrifBean>>() {
            @Override
            public void onResponse(DataBean<BrifBean> response) {
                BrifBean bean = response.data;
                if (bean == null){
                    return;
                }
                if (StringUtils.isSame(CommonUtils.isOne,bean.getUser_type())){//名人堂
                    WebViewPhotoBrowserUtil.photoBrowser(MingDataActivity.this, webView, bean.getContent());
                }else {
                    signatureTv.setText(bean.getSignature());
                    areaTv.setText(bean.getArea());
                }
                contentTv.setText("ID："+bean.getId());
                nameTv.setText(bean.getNickname());
                ImageHelper.load(MingDataActivity.this, bean.getPic(), headIv, null, true, R.mipmap.default_pic, R.mipmap.default_pic);
                guanZhu = bean.getGuanzhu();
                CommonUtils.setTextViewType(MingDataActivity.this,guanZhu+"",attentionTv,R.string.attention,R.string.attentioned);
                attentionTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!CommonUtils.isLogin(MingDataActivity.this)){
                            return;
                        }
                        String str = "";
                        if (guanZhu == 0){//0|1  未关注|已关注
                            str = getString(R.string.is_follow);
                        }else {
                            str = getString(R.string.is_no_follow);
                        }
                        AKDialog.getAlertDialog(MingDataActivity.this, str , new AKDialog.AlertDialogListener() {
                            @Override
                            public void yes() {
                                isFollowMing();
                            }
                        });
                    }
                });
            }
        });
    }


    private void isFollowMing() {
        FollowApi api = new FollowApi();
        api.uid = api.getUserId(this);
        api.mid = bean.getId();
        if (StringUtils.isSame(CommonUtils.isOne,bean.getUser_type())){//名人堂
            api.type = "2";//0|1|2=>普通会员|公众号|名人堂
        }else {
            api.type = "0";//0|1|2=>普通会员|公众号|名人堂
        }
        HttpClient.newInstance(this).loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                if (isFinishing()){
                    return;
                }
                if (guanZhu == 0){//0|1  未关注|已关注
                    guanZhu = 1;
                }else {
                    guanZhu = 0;
                }
                UIHelper.ToastMessage(MingDataActivity.this,response.getInfo());
                CommonUtils.loadUserInfo(MingDataActivity.this,null);
                EventBus.getDefault().post(new AttentionFriendEvent(bean.getId(),guanZhu+""));//该uid都设置为关注或不关注
                CommonUtils.setTextViewType(MingDataActivity.this,guanZhu+"",attentionTv,R.string.attention,R.string.attentioned);
            }
        });
    }

    @OnClick({R.id.send_bt, R.id.video_bt, R.id.give_bt})
    public void onViewClicked(View view) {
        if (bean == null) {
            return;
        }
        switch (view.getId()) {
            case R.id.send_bt:
                CommonUtils.startChatActivity(this,bean.getId(),false);
                break;
            case R.id.video_bt:
                startActivity(new Intent(this, VideoCallActivity.class).putExtra("username", BtcimApp.HX_USER_Head+bean.getId())
                        .putExtra("isComingCall", false));
                break;
            case R.id.give_bt:
                IntentUtil.startActivity(this,TransferActivity.class,bean.getId());
                break;
        }
    }
}
