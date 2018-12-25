package com.lanmei.bilan.ui.home.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.exceptions.HyphenateException;
import com.lanmei.bilan.R;
import com.lanmei.bilan.adapter.MessageSheSubAdapter;
import com.lanmei.bilan.adapter.SearchUserAdapter;
import com.lanmei.bilan.api.QunSearchApi;
import com.lanmei.bilan.api.SearchUserApi;
import com.lanmei.bilan.bean.SearchUserBean;
import com.lanmei.bilan.bean.SheQuQunBean;
import com.lanmei.bilan.event.JoinGroupEvent;
import com.lanmei.bilan.monitor.JoinGroupListener;
import com.lanmei.bilan.utils.CommonUtils;
import com.xson.common.app.BaseActivity;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.utils.L;
import com.xson.common.utils.StringUtils;
import com.xson.common.utils.UIHelper;
import com.xson.common.widget.DrawClickableEditText;
import com.xson.common.widget.EmptyRecyclerView;

import org.greenrobot.eventbus.EventBus;

import butterknife.InjectView;
import butterknife.OnClick;

/***
 * 搜索用户
 */
public class SearchUserActivity extends BaseActivity implements TextView.OnEditorActionListener {


    @InjectView(R.id.keywordEditText)
    DrawClickableEditText mKeywordEditText;
    @InjectView(R.id.empty_view)
    View mEmptyView;
    @InjectView(R.id.recyclerView)
    EmptyRecyclerView mRecyclerView;

    SearchUserAdapter mUserAdapter;
    MessageSheSubAdapter adapter;
    int type;//1为搜索群

    @Override
    public int getContentViewId() {
        return R.layout.activity_search_user;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        type = getIntent().getIntExtra("type",0);

        mKeywordEditText.setOnEditorActionListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setEmptyView(mEmptyView);
        if (type == 1){//群
            adapter = new MessageSheSubAdapter(this);
            mRecyclerView.setAdapter(adapter);
            adapter.setJoinGroupListener(new JoinGroupListener() {
                @Override
                public void joinGroup(final String groupId) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            addGroup(groupId);
                        }
                    }).start();
                }
            });
            mKeywordEditText.setHint(R.string.search_qun);
        }else {//用户
            mUserAdapter = new SearchUserAdapter(this);
            mRecyclerView.setAdapter(mUserAdapter);
            mKeywordEditText.setHint(R.string.search_friends);
        }
    }

    private void addGroup(final String groupid) {
        try {
            EMGroup group = EMClient.getInstance().groupManager().getGroupFromServer(groupid);
            boolean join = group.isMembersOnly();
            L.d("EMGroup", "join:" + join);
            if (!join) {//如果群开群是自由加入的，即group.isMembersOnly()为false，直接join
                EMClient.getInstance().groupManager().asyncJoinGroup(group.getGroupId(), new EMCallBack() {

                    @Override
                    public void onSuccess() {
                        CommonUtils.startChatActivity(SearchUserActivity.this, groupid, true);
//                    UIHelper.ToastMessage(context, "请求加入群成功");
                        EventBus.getDefault().post(new JoinGroupEvent(getString(R.string.join_succeed)));
                        L.d("EMGroup", "请求加入群成功");
                    }

                    @Override
                    public void onError(int i, String s) {
//                    UIHelper.ToastMessage(context, s);
                        EventBus.getDefault().post(new JoinGroupEvent(s));
                        L.d("EMGroup", s);
                    }

                    @Override
                    public void onProgress(int i, String s) {
                        L.d("EMGroup", "i:" + i + ", s:" + s);
                    }
                });
//                                EMClient.getInstance().groupManager().joinGroup(groupid);//需异步处理
            } else {//需要申请和验证才能加入的，即group.isMembersOnly()为true，调用下面方法
                EMClient.getInstance().groupManager().asyncApplyJoinToGroup(group.getGroupId(), "求加入", new EMCallBack() {
                    @Override
                    public void onSuccess() {
//                        CommonUtils.startChatActivity(context, groupid, true);
//                    UIHelper.ToastMessage(context, "请求加入群成功");
                        EventBus.getDefault().post(new JoinGroupEvent(getString(R.string.wait_verify)));
                        L.d("EMGroup", "已申请添加，等待群主验证");
                    }

                    @Override
                    public void onError(int i, String s) {
//                    UIHelper.ToastMessage(context, s);
                        EventBus.getDefault().post(new JoinGroupEvent(s));
                        L.d("EMGroup", s);
                    }

                    @Override
                    public void onProgress(int i, String s) {
                        L.d("EMGroup", "i:" + i + ", s:" + s);
                    }
                });
//                                EMClient.getInstance().groupManager().applyJoinToGroup(groupid, "求加入");//需异步处理
            }
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            String key = v.getText().toString().trim();
            if (StringUtils.isEmpty(key)) {
                UIHelper.ToastMessage(SearchUserActivity.this, R.string.input_keyword);
                return false;
            }
            if (type == 1){
                ajaxSearchQun(key);
            }else {
                ajaxSearchUser(key);
            }
            return true;
        }
        return false;
    }

    //搜索用户
    private void ajaxSearchUser(String key) {

        SearchUserApi api = new SearchUserApi();
        api.keyword = key;
        api.token = api.getToken(this);
        api.uid = api.getUserId(this);
        HttpClient.newInstance(this).loadingRequest(api, new BeanRequest.SuccessListener<NoPageListBean<SearchUserBean>>() {
            @Override
            public void onResponse(NoPageListBean<SearchUserBean> response) {
                if (isFinishing()){
                    return;
                }
                mUserAdapter.setData(response.data);
                mUserAdapter.notifyDataSetChanged();
                if (StringUtils.isEmpty(response.data)){
                    UIHelper.ToastMessage(SearchUserActivity.this,"无相关用户");
                }
            }
        });
    }
    //搜索用户
    private void ajaxSearchQun(String key) {
        QunSearchApi api = new QunSearchApi();
        api.name = key;
        HttpClient.newInstance(this).loadingRequest(api, new BeanRequest.SuccessListener<NoPageListBean<SheQuQunBean>>() {
            @Override
            public void onResponse(NoPageListBean<SheQuQunBean> response) {
                if (isFinishing()){
                    return;
                }
                adapter.setData(response.data);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @OnClick(R.id.back_iv)
    public void showBack(){//返回
        onBackPressed();
    }

}
