package com.lanmei.bilan.ui.mine.activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lanmei.bilan.R;
import com.lanmei.bilan.api.InviteRecordApi;
import com.lanmei.bilan.bean.InviteBean;
import com.lanmei.bilan.bean.InviteListBean;
import com.lanmei.bilan.bean.InviteRecordBean;
import com.lanmei.bilan.helper.ShareHelper;
import com.lanmei.bilan.utils.CommonUtils;
import com.lanmei.bilan.utils.SharedAccount;
import com.lanmei.bilan.widget.InvitePosterView;
import com.xson.common.app.BaseActivity;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.utils.StringUtils;
import com.xson.common.utils.UIHelper;
import com.xson.common.widget.CenterTitleToolbar;
import com.xson.common.widget.FormatTextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 邀请好友赚糖果
 */
public class InviteActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    CenterTitleToolbar mToolbar;
    @InjectView(R.id.ll_invite_record)
    LinearLayout llInviteRecord;
    @InjectView(R.id.share_url_tv)
    TextView shareUrlTv;
    @InjectView(R.id.count_tv)
    FormatTextView countTv;//获取糖果数量
    private ShareHelper mShareHelper;

    InviteBean bean;
    String shareQr;
    String shareUrl;

    @Override
    public int getContentViewId() {
        return R.layout.activity_invite;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        setSupportActionBar(mToolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle(R.string.invite_friend);
        actionbar.setHomeAsUpIndicator(R.mipmap.back_w);
        //分享帮助初始化
        mShareHelper = new ShareHelper(this);
        InviteRecordApi api = new InviteRecordApi();
        api.uid = api.getUserId(this);
        HttpClient.newInstance(this).loadingRequest(api, new BeanRequest.SuccessListener<InviteListBean<InviteRecordBean>>() {
            @Override
            public void onResponse(InviteListBean<InviteRecordBean> response) {
                if (isFinishing()) {
                    return;
                }
                if (StringUtils.isEmpty(response)){
                    return;
                }
                shareUrl = response.getUrl();
                shareUrlTv.setText("邀请链接：" + shareUrl);
                countTv.setTextValue(response.getCount());
                List<InviteRecordBean> list = response.data;
                if (StringUtils.isEmpty(list)) {
                    return;
                }
                initInvite(list);
            }
        });

        bean = SharedAccount.getInstance(this).getInviteBean();
        if (StringUtils.isEmpty(bean)) {
            CommonUtils.loadSystem(this, new CommonUtils.LoadSystemListener() {
                @Override
                public void loadSystem(InviteBean invitebean) {
                    bean = invitebean;
                    setShareUrl();
                }
            });
        } else {
            setShareUrl();
        }
    }


    public void setShareUrl() {
        if (StringUtils.isEmpty(bean)) {
            return;
        }
        shareQr = bean.getShare_qr();
//        shareUrl = bean.getShare_url();
//        shareUrlTv.setText("邀请链接：" + shareUrl);
    }

    private void initInvite(List<InviteRecordBean> list) {
        for (InviteRecordBean bean : list) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_invite, null);
            new ViewHolder(view, bean);
            llInviteRecord.addView(view);
        }
    }


    public class ViewHolder {

        @InjectView(R.id.m_tv)
        TextView mTv;
        @InjectView(R.id.price_tv)
        TextView priceTv;
        @InjectView(R.id.price2_tv)
        TextView price2Tv;
        @InjectView(R.id.count2_tv)
        TextView count2Tv;

        public ViewHolder(View view, InviteRecordBean bean) {
            ButterKnife.inject(this, view);

            mTv.setText(bean.getM());
            priceTv.setText(bean.getPrice());
            price2Tv.setText(bean.getPrice2());
            count2Tv.setText(bean.getCount());
        }
    }

    /**
     * 结果返回
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mShareHelper.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
//                UIHelper.ToastMessage(this,R.string.developing);
//                if (bean != null) {
//                    mShareHelper.setShareUrl(bean.getShare_url());
//                    mShareHelper.showShareDialog();
//                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mShareHelper.onDestroy();
    }


    @OnClick({R.id.invite1_tv, R.id.invite2_tv, R.id.invite3_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.invite1_tv://点击生成我的海报
                shareEvent();
                break;
            case R.id.invite2_tv://立即生成
                shareEvent();
                break;
            case R.id.invite3_tv://复制地址
                if (StringUtils.isEmpty(shareUrl)) {
                    UIHelper.ToastMessage(this,"邀请链接不存在");
                    return;
                }
                setClipboard(shareUrl);
                break;
        }
    }

    public void setClipboard(String text) {
        ClipboardManager clipboard = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.setText(text);
        clipboard.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
            @Override
            public void onPrimaryClipChanged() {
                UIHelper.ToastMessage(getContext(),getString(R.string.copy_succeed));
            }
        });
    }

    private void shareEvent() {
        InvitePosterView view = (InvitePosterView) LayoutInflater.from(this).inflate(R.layout.poster_capture, null);
        view.share(shareQr);
        mShareHelper.share(view);
    }

}
