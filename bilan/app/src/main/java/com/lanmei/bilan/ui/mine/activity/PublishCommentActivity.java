package com.lanmei.bilan.ui.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.widget.EditText;
import android.widget.RatingBar;

import com.lanmei.bilan.R;
import com.lanmei.bilan.api.OrderCommentsApi;
import com.lanmei.bilan.bean.MineOrderBean;
import com.lanmei.bilan.event.CommentEvent;
import com.lanmei.bilan.event.OrderDetailsEvent;
import com.lanmei.bilan.utils.CommonUtils;
import com.xson.common.app.BaseActivity;
import com.xson.common.bean.BaseBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.utils.StringUtils;
import com.xson.common.utils.UIHelper;
import com.xson.common.widget.CenterTitleToolbar;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 发表评论
 */
public class PublishCommentActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    CenterTitleToolbar mToolbar;
    @InjectView(R.id.content_et)
    EditText contentEt;
    @InjectView(R.id.ratingbar)
    RatingBar ratingBar;
    MineOrderBean bean;//我的订单item信息
    int point = 1;

    @Override
    public int getContentViewId() {
        return R.layout.activity_publish_comment;
    }

    @Override
    public void initIntent(Intent intent) {
        super.initIntent(intent);
        Bundle bundle = intent.getBundleExtra("bundle");
        if (!StringUtils.isEmpty(bundle)) {
            bean = (MineOrderBean) bundle.getSerializable("bean");
        }
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        setSupportActionBar(mToolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle("发布评论");
        actionbar.setHomeAsUpIndicator(R.mipmap.back);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                point = (int) rating;
            }
        });
    }


    @OnClick(R.id.publish_bt)
    public void onViewClicked() {
        String content = CommonUtils.getStringByEditText(contentEt);
        if (StringUtils.isEmpty(content) || content.length() < 5) {
            UIHelper.ToastMessage(this, R.string.five_character);
            return;
        }
        OrderCommentsApi api = new OrderCommentsApi();
        api.content = content;
        api.orderid = bean.getId();
        api.uid = api.getUserId(this);
        api.proid = getProid();
        api.point = point;
        HttpClient.newInstance(this).loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                if (isFinishing()) {
                    return;
                }
                int code = response.getCode();
                switch (code) {//1|10|100|404 成功|缺失参数|更改错误|未找到订单
                    case 1:
                        UIHelper.ToastMessage(PublishCommentActivity.this, "评论成功");
                        EventBus.getDefault().post(new OrderDetailsEvent());//刷新订单列表
                        EventBus.getDefault().post(new CommentEvent());//评论成功
                        finish();
                        break;
                    case 10:
                        UIHelper.ToastMessage(PublishCommentActivity.this, "缺失参数");
                        break;
                    case 100:
                        UIHelper.ToastMessage(PublishCommentActivity.this, "更改错误");
                        break;
                    case 404:
                        UIHelper.ToastMessage(PublishCommentActivity.this, "未找到订单");
                        break;
                }
            }
        });
    }

    private String getProid() {
        String id = "";
        if (bean == null) {
            return id;
        }
        List<MineOrderBean.ProductBean> list = bean.getProduct();
        if (StringUtils.isEmpty(list)) {
            return id;
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            MineOrderBean.ProductBean bean = list.get(i);
            if (!StringUtils.isEmpty(bean) && !StringUtils.isEmpty(bean.getId())){
                id += bean.getId()+",";
            }
        }
        if (!StringUtils.isEmpty(id)){
            id = id.substring(0,id.length()-1);
        }
        return id;
    }
}
