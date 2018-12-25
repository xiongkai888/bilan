package com.lanmei.bilan.ui.home.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lanmei.bilan.R;
import com.lanmei.bilan.adapter.HomeQuDetailsAdapter;
import com.lanmei.bilan.api.DeleteDynamicApi;
import com.lanmei.bilan.api.HomeQuDetailsCommApi;
import com.lanmei.bilan.api.HomeQuDetailsCommListApi;
import com.lanmei.bilan.bean.HomeQuBean;
import com.lanmei.bilan.bean.InviteBean;
import com.lanmei.bilan.bean.homeQuCommBean;
import com.lanmei.bilan.event.AddDynamicEvent;
import com.lanmei.bilan.event.HomeQuLikeEvent;
import com.lanmei.bilan.helper.ShareHelper;
import com.lanmei.bilan.helper.ShareListener;
import com.lanmei.bilan.utils.AKDialog;
import com.lanmei.bilan.utils.CommonUtils;
import com.lanmei.bilan.utils.SharedAccount;
import com.lanmei.bilan.widget.DetailsMoreView;
import com.lanmei.bilan.widget.ShareNewsView;
import com.xson.common.app.BaseActivity;
import com.xson.common.bean.BaseBean;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.helper.SwipeRefreshController;
import com.xson.common.utils.StringUtils;
import com.xson.common.utils.SysUtils;
import com.xson.common.utils.UIBaseUtils;
import com.xson.common.utils.UIHelper;
import com.xson.common.widget.CenterTitleToolbar;
import com.xson.common.widget.DividerItemDecoration;
import com.xson.common.widget.SmartSwipeRefreshLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 动态  动态详情
 */
public class HomeQuDetailsActivity extends BaseActivity {


    @InjectView(R.id.toolbar)
    CenterTitleToolbar mToolbar;
    @InjectView(R.id.compile_comment_et)
    EditText mCompileCommentEt;

    @InjectView(R.id.pull_refresh_rv)
    SmartSwipeRefreshLayout smartSwipeRefreshLayout;
    HomeQuDetailsAdapter mAdapter;
    private SwipeRefreshController<NoPageListBean<homeQuCommBean>> controller;
    String uid;//用户id
    String id;//动态（帖子）id
    HomeQuBean mbean;
    boolean isSelf;//是不是自己的动态
    int who;//1区块圈2动态点进来的

    InviteBean bean;
    private ShareHelper mShareHelper;

    @Override
    public int getContentViewId() {
        return R.layout.activity_home_qu_details;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        setSupportActionBar(mToolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle("动态详情");
        actionbar.setHomeAsUpIndicator(R.mipmap.back);
        initSwipeRefreshLayout();
    }


    @Override
    public void initIntent(Intent intent) {
        super.initIntent(intent);
        Bundle bundle = intent.getBundleExtra("bundle");
        mbean = (HomeQuBean) bundle.getSerializable("bean");
        who = bundle.getInt("who");
        if (mbean != null) {
            uid = mbean.getUid();
            id = mbean.getId();
            isSelf = StringUtils.isSame(uid, CommonUtils.getUserId(this));
        }
    }

    private void initSwipeRefreshLayout() {

        smartSwipeRefreshLayout.initWithLinearLayout();
        smartSwipeRefreshLayout.getRecyclerView().addItemDecoration(new DividerItemDecoration(this));

        HomeQuDetailsCommListApi api = new HomeQuDetailsCommListApi();
        api.posts_id = mbean.getId();
        mAdapter = new HomeQuDetailsAdapter(this, mbean, isSelf, who);
        smartSwipeRefreshLayout.setAdapter(mAdapter);
        controller = new SwipeRefreshController<NoPageListBean<homeQuCommBean>>(this, smartSwipeRefreshLayout, api, mAdapter) {
        };
        controller.loadFirstPage();

        //分享帮助初始化
        mShareHelper = new ShareHelper(this);
        bean = SharedAccount.getInstance(this).getInviteBean();
        mAdapter.setShareListener(new ShareListener() {
            @Override
            public void share(String content, String time, List<String> list,String type) {
                ShareNewsView view = (ShareNewsView) LayoutInflater.from(getContext()).inflate(R.layout.view_capture, null);
                view.share(content,time,list,type);
                mShareHelper.share(view);
//                shareEvent();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_more, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_more:
                if (!CommonUtils.isLogin(this)) {
                    break;
                }
                if (isSelf) {
                    popupWindow();
                } else {
                    shareEvent();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void shareEvent(){
        ShareNewsView view = (ShareNewsView) LayoutInflater.from(this).inflate(R.layout.view_capture, null);
        view.share(mbean.getTitle(),mbean.getAddtime(),mbean.getFile(),CommonUtils.isTwo);
        mShareHelper.share(view);

//        if (bean != null) {
//            mShareHelper.setShareUrl(bean.getShare_url());
//            mShareHelper.share();
//        } else {
//            CommonUtils.loadSystem(this, new CommonUtils.LoadSystemListener() {
//                @Override
//                public void loadSystem(InviteBean invitebean) {
//                    bean = invitebean;
//                    mShareHelper.setShareUrl(bean.getShare_url());
//                    mShareHelper.share();
//                }
//            });
//        }
    }

    private void popupWindow() {
        DetailsMoreView view = (DetailsMoreView) View.inflate(this, R.layout.view_details_more, null);
        int width = UIBaseUtils.dp2pxInt(this, 80);
        TextView compileTv = (TextView)view.findViewById(R.id.compile_tv);
        compileTv.setVisibility(View.GONE);
        final PopupWindow window = new PopupWindow(view, width, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setContentView(view);
        window.setFocusable(true);
        window.setOutsideTouchable(true);
        window.setBackgroundDrawable(new BitmapDrawable());
        int paddingRight = UIBaseUtils.dp2pxInt(this, 0);
        int xoff = SysUtils.getScreenWidth(this) - width - paddingRight;
        window.showAsDropDown(mToolbar, xoff, 2);
        view.setDetailsMoreListener(new DetailsMoreView.DetailsMoreListener() {
            @Override
            public void delete() {
                window.dismiss();
                dialogDelete();
            }

            @Override
            public void shareMore() {
                window.dismiss();
//                UIHelper.ToastMessage(HomeQuDetailsActivity.this, R.string.developing);
                shareEvent();
            }

            @Override
            public void compile() {
                window.dismiss();
            }
        });
    }


    //删除
    private void dialogDelete() {
        AKDialog.getAlertDialog(this, "确定要删除该动态？", new AKDialog.AlertDialogListener() {
            @Override
            public void yes() {
                loadDeleteActivity();
            }
        });
    }


    private void loadDeleteActivity() {//删除动态
        HttpClient httpClient = HttpClient.newInstance(this);
        DeleteDynamicApi api = new DeleteDynamicApi();
        api.id = id;
        api.uid = api.getUserId(this);
        httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                if (isFinishing()) {
                    return;
                }
                UIHelper.ToastMessage(HomeQuDetailsActivity.this, response.getInfo());
                EventBus.getDefault().post(new AddDynamicEvent());
                finish();
            }
        });
    }


    @OnClick(R.id.send_info_tv)
    public void sendInfo() {//发送评论
        if (!CommonUtils.isLogin(this)) {
            return;
        }
        String content = mCompileCommentEt.getText().toString().trim();
        if (StringUtils.isEmpty(content)) {
            UIHelper.ToastMessage(this, "请输入评论内容");
            return;
        }
        ajaxSend(content);
    }

    private void ajaxSend(String content) {
        if (!CommonUtils.isLogin(this)) {
            return;
        }
        HttpClient httpClient = HttpClient.newInstance(this);
        HomeQuDetailsCommApi api = new HomeQuDetailsCommApi();
        api.content = content;
        api.at_uid = uid;
        api.uid = api.getUserId(this);
        api.posts_id = id;
        //        api.uid = api.getUrl();
        httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                if (isFinishing()) {
                    return;
                }
                UIHelper.ToastMessage(HomeQuDetailsActivity.this, "评论成功");
                mbean.setReviews((Integer.valueOf(mbean.getReviews()) + 1) + "");
                EventBus.getDefault().post(new HomeQuLikeEvent(mbean.getLike(), mbean.getLiked(), mbean.getReviews(),mbean.getId()));
                if (controller != null) {
                    controller.loadFirstPage();
                }
                mCompileCommentEt.setText("");
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mShareHelper.onDestroy();
    }

    /**
     * 结果返回
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mShareHelper.onActivityResult(requestCode, resultCode, data);
    }
}
