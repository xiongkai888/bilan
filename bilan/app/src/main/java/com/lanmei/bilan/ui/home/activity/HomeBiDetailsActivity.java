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

import com.lanmei.bilan.R;
import com.lanmei.bilan.adapter.HomeBiDetailsAdapter;
import com.lanmei.bilan.api.DeleteArticleApi;
import com.lanmei.bilan.api.HomeBiDetailsApi;
import com.lanmei.bilan.api.HomeBiDetailsCommApi;
import com.lanmei.bilan.api.HomeBiDetailsCommListApi;
import com.lanmei.bilan.bean.InviteBean;
import com.lanmei.bilan.bean.NewsJingBean;
import com.lanmei.bilan.bean.homeBiCommBean;
import com.lanmei.bilan.event.CompileArticleEvent;
import com.lanmei.bilan.event.DeleteArticleEvent;
import com.lanmei.bilan.event.HomeBiCommEvent;
import com.lanmei.bilan.helper.ShareHelper;
import com.lanmei.bilan.ui.mine.activity.PublishArticleActivity;
import com.lanmei.bilan.utils.AKDialog;
import com.lanmei.bilan.utils.CommonUtils;
import com.lanmei.bilan.utils.SharedAccount;
import com.lanmei.bilan.widget.DetailsMoreView;
import com.lanmei.bilan.widget.ShareNewsView;
import com.xson.common.app.BaseActivity;
import com.xson.common.bean.BaseBean;
import com.xson.common.bean.DataBean;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.helper.SwipeRefreshController;
import com.xson.common.utils.IntentUtil;
import com.xson.common.utils.StringUtils;
import com.xson.common.utils.SysUtils;
import com.xson.common.utils.UIBaseUtils;
import com.xson.common.utils.UIHelper;
import com.xson.common.widget.CenterTitleToolbar;
import com.xson.common.widget.SmartSwipeRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.InjectView;
import butterknife.OnClick;


/**
 * 资讯详情
 */
public class HomeBiDetailsActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    CenterTitleToolbar mToolbar;
    @InjectView(R.id.compile_comment_et)
    EditText mCompileCommentEt;//写评论

    @InjectView(R.id.pull_refresh_rv)
    SmartSwipeRefreshLayout smartSwipeRefreshLayout;
    HomeBiDetailsAdapter mAdapter;
    String id;//资讯id
    int commentNum;//评论数
    int position;//币头条列表下标
    NewsJingBean bean;
    InviteBean inviteBean;
    private ShareHelper mShareHelper;
    boolean isSelf;//是不是自己的动态


    private SwipeRefreshController<NoPageListBean<homeBiCommBean>> controller;

    @Override
    public int getContentViewId() {
        return R.layout.activity_home_bi_details;
    }

    @Override
    public void initIntent(Intent intent) {
        super.initIntent(intent);

        Bundle bundle = intent.getBundleExtra("bundle");
        bean = (NewsJingBean) bundle.getSerializable("bean");
        position = bundle.getInt("position");
        if (bean != null) {
            id = bean.getId();
            commentNum = Integer.parseInt(StringUtils.isEmpty(bean.getReviews()) ? "0" : bean.getReviews());
            isSelf = StringUtils.isSame(bean.getUid(), CommonUtils.getUserId(this));
        }
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        setSupportActionBar(mToolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle("资讯详情");
        actionbar.setHomeAsUpIndicator(R.mipmap.back);

        smartSwipeRefreshLayout.initWithLinearLayout();

        HomeBiDetailsCommListApi api = new HomeBiDetailsCommListApi();
        api.id = id;
//        mAdapter = new HomeBiDetailsAdapter(this,bean,position);
        mAdapter = new HomeBiDetailsAdapter(this);
        smartSwipeRefreshLayout.setAdapter(mAdapter);
        controller = new SwipeRefreshController<NoPageListBean<homeBiCommBean>>(this, smartSwipeRefreshLayout, api, mAdapter) {
        };
//        controller.loadFirstPage();
        loadBiDetails();//加载资讯详情

        //分享帮助初始化
        mShareHelper = new ShareHelper(this);
        inviteBean = SharedAccount.getInstance(this).getInviteBean();

        EventBus.getDefault().register(this);
    }


    @Subscribe
    public void compileArticleEvent(CompileArticleEvent event){
        loadBiDetails();
    }

    private void loadBiDetails() {
        HttpClient httpClient = HttpClient.newInstance(this);
        HomeBiDetailsApi api = new HomeBiDetailsApi();
        api.postid = id;
        api.uid = StringUtils.isEmpty(api.getUserId(this))? CommonUtils.isZero:api.getUserId(this);
        httpClient.request(api, new BeanRequest.SuccessListener<DataBean<NewsJingBean>>() {
            @Override
            public void onResponse(DataBean<NewsJingBean> response) {
                if (isFinishing()) {
                    return;
                }
                bean = response.data;
                EventBus.getDefault().post(new HomeBiCommEvent(bean.getReviews(),bean.getId()));
                mAdapter.setHomeBiDetails(bean);
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


    private void popupWindow() {
        DetailsMoreView view = (DetailsMoreView) View.inflate(this, R.layout.view_details_more, null);
        int width = UIBaseUtils.dp2pxInt(this, 80);
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
                shareEvent();
                window.dismiss();
            }

            @Override
            public void compile() {
                window.dismiss();
                Bundle bundle = new Bundle();
                bundle.putSerializable("bean",bean);
                IntentUtil.startActivity(getContext(), PublishArticleActivity.class);
            }
        });
    }

    //删除
    private void dialogDelete() {
        AKDialog.getAlertDialog(this, "确定要删除该文章？", new AKDialog.AlertDialogListener() {
            @Override
            public void yes() {
                loadDeleteActivity();
            }
        });
    }

    private void loadDeleteActivity() {//删除文章
        HttpClient httpClient = HttpClient.newInstance(this);
        DeleteArticleApi api = new DeleteArticleApi();
        api.postid = id;
        api.uid = api.getUserId(this);
        httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                if (isFinishing()) {
                    return;
                }
                UIHelper.ToastMessage(getContext(), CommonUtils.getString(response));
                EventBus.getDefault().post(new DeleteArticleEvent());
                finish();
            }
        });
    }


    @OnClick({R.id.send_info_tv})
    public void showInfo(View view) {//发送消息
        if (!CommonUtils.isLogin(this)) {
            return;
        }
        switch (view.getId()) {
            case R.id.send_info_tv:
                ajaxSend(mCompileCommentEt.getText().toString());
                break;
        }

    }

    private void shareEvent(){
        ShareNewsView view = (ShareNewsView)LayoutInflater.from(this).inflate(R.layout.view_capture, null);
        view.share(bean.getContent(),bean.getAddtime(),bean.getFile(),CommonUtils.isOne);
        mShareHelper.share(view);
    }


    /**
     * @param content 评论内容
     */
    private void ajaxSend(String content) {
        if (StringUtils.isEmpty(content)){
            UIHelper.ToastMessage(this,getString(R.string.input_comment));
            return;
        }
        HttpClient httpClient = HttpClient.newInstance(this);
        HomeBiDetailsCommApi api = new HomeBiDetailsCommApi();
        api.content = content;
        api.id = id;
        api.uid = api.getUserId(this);
//        api.uid = api.getUrl();
        httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                if (isFinishing()) {
                    return;
                }
                UIHelper.ToastMessage(HomeBiDetailsActivity.this, "评论成功");
                if (controller != null){
                    controller.loadFirstPage();
                    loadBiDetails();//加载资讯详情
                }
                mCompileCommentEt.setText("");
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mShareHelper.onDestroy();
        EventBus.getDefault().unregister(this);
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
