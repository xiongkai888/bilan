package com.lanmei.bilan.ui.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lanmei.bilan.R;
import com.lanmei.bilan.bean.UserBean;
import com.lanmei.bilan.event.SetUserInfoEvent;
import com.lanmei.bilan.helper.UserHelper;
import com.lanmei.bilan.ui.goods.shop.ShopCarActivity;
import com.lanmei.bilan.ui.mine.activity.AppRecommendActivity;
import com.lanmei.bilan.ui.mine.activity.BlActivity;
import com.lanmei.bilan.ui.mine.activity.BurseActivity;
import com.lanmei.bilan.ui.mine.activity.InviteActivity;
import com.lanmei.bilan.ui.mine.activity.MineArticleActivity;
import com.lanmei.bilan.ui.mine.activity.MineBimActivity;
import com.lanmei.bilan.ui.mine.activity.MineDynamicActivity;
import com.lanmei.bilan.ui.mine.activity.MineFansActivity;
import com.lanmei.bilan.ui.mine.activity.MineFollowActivity;
import com.lanmei.bilan.ui.mine.activity.MineOrderActivity;
import com.lanmei.bilan.ui.mine.activity.PersonalDataActivity;
import com.lanmei.bilan.ui.mine.activity.PropertyActivity;
import com.lanmei.bilan.ui.mine.activity.SettingActivity;
import com.lanmei.bilan.ui.mine.activity.TaskActivity;
import com.lanmei.bilan.utils.CommonUtils;
import com.xson.common.app.BaseFragment;
import com.xson.common.helper.ImageHelper;
import com.xson.common.utils.IntentUtil;
import com.xson.common.utils.L;
import com.xson.common.utils.StringUtils;
import com.xson.common.utils.UIHelper;
import com.xson.common.widget.CircleImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xkai on 2018/1/3.
 * 我的
 */

public class MineFragment extends BaseFragment {


    @InjectView(R.id.head_iv)
    CircleImageView headIv;
    @InjectView(R.id.name_tv)
    TextView nameTv;
    @InjectView(R.id.signature_tv)
    TextView signatureTv;
    @InjectView(R.id.grade_tv)
    TextView gradeTv;//比特等级
    @InjectView(R.id.fans_num_tv)
    TextView fansNumTv;
    @InjectView(R.id.attention_num_tv)
    TextView attentionNumTv;
    @InjectView(R.id.ll_my_article)
    LinearLayout llMyArticle;
    @InjectView(R.id.num_tv)
    TextView numTv;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        setUser(UserHelper.getInstance(context).getUserBean());//初始化用户信息
    }

    //用户登录时调用
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(SetUserInfoEvent event) {
        L.d("beanre", "重新获取了用户数据");
        setUser(UserHelper.getInstance(context).getUserBean());
    }

    private void setUser(UserBean bean) {
        if (bean == null) {
            nameTv.setText("游客");
            headIv.setImageResource(R.mipmap.default_pic);
            gradeTv.setText("比特0级");
            signatureTv.setText("");
            fansNumTv.setText("粉丝0");
            attentionNumTv.setText("关注0");
            return;
        }

        if (StringUtils.isSame(bean.getInformation(),CommonUtils.isOne)){
            llMyArticle.setVisibility(View.VISIBLE);
        }else {
            llMyArticle.setVisibility(View.GONE);
        }
        nameTv.setText(bean.getNickname());
        numTv.setText(bean.getJifen());
        gradeTv.setText(bean.getLevname());
        signatureTv.setText(bean.getSignature());
        fansNumTv.setText(String.format(getString(R.string.fans), bean.getFans()));
        attentionNumTv.setText(String.format(getString(R.string.follow), bean.getFollow()));
        ImageHelper.load(context, bean.getPic(), headIv, null, true, R.mipmap.default_pic, R.mipmap.default_pic);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @OnClick({R.id.ll_BL1, R.id.ll_BL2, R.id.ll_BL3, R.id.data_iv, R.id.head_iv, R.id.fans_num_tv, R.id.attention_num_tv, R.id.ll_bim, R.id.ll_wallet, R.id.ll_property_book, R.id.ll_information_backups, R.id.ll_member_center, R.id.ll_my_order, R.id.ll_my_dynamic, R.id.ll_my_article, R.id.ll_setting, R.id.ll_my_shopping_cart, R.id.ll_app_recommend, R.id.ll_invite_friends})
    public void onViewClicked(View view) {
        if (!CommonUtils.isLogin(context)) {
            return;
        }
        switch (view.getId()) {
            case R.id.head_iv://头像
            case R.id.ll_member_center://会员中心
                IntentUtil.startActivity(context, PersonalDataActivity.class);
                break;
            case R.id.fans_num_tv://粉丝
                IntentUtil.startActivity(context, MineFansActivity.class);
                break;
            case R.id.attention_num_tv://关注
                IntentUtil.startActivity(context, MineFollowActivity.class);
                break;
            case R.id.ll_bim://糖果宝盒
                IntentUtil.startActivity(context, MineBimActivity.class);
                break;
            case R.id.ll_wallet://钱包
                IntentUtil.startActivity(context, BurseActivity.class);
                break;
            case R.id.ll_property_book://资产账本
                IntentUtil.startActivity(context, PropertyActivity.class);
                break;
            case R.id.ll_information_backups://资料备份
                UIHelper.ToastMessage(context, R.string.developing);
                break;
            case R.id.ll_my_dynamic://我的动态
                IntentUtil.startActivity(context, MineDynamicActivity.class);
                break;
            case R.id.ll_my_article://我的文章
//                IntentUtil.startActivity(context, MinePublishActivity.class);
                IntentUtil.startActivity(context, MineArticleActivity.class);
                break;
            case R.id.ll_setting://设置
            case R.id.data_iv://
                IntentUtil.startActivity(context, SettingActivity.class);
                break;
            case R.id.ll_my_shopping_cart://我的购物车
                IntentUtil.startActivity(context, ShopCarActivity.class);
                break;
            case R.id.ll_my_order://我的订单
                IntentUtil.startActivity(context, MineOrderActivity.class);
                break;
            case R.id.ll_app_recommend://应用推荐
                IntentUtil.startActivity(context, AppRecommendActivity.class);
                break;
            case R.id.ll_invite_friends://邀请好友赚糖果
                IntentUtil.startActivity(context, InviteActivity.class);
                break;
            case R.id.ll_BL1://币澜没有那么神秘
                load("1", "币澜没有那么神秘");
                break;
            case R.id.ll_BL2://BL糖果俱乐部
                load("2", "BL糖果俱乐部");
                break;
            case R.id.ll_BL3://币澜如何获取糖果？
//                load("3","币澜如何获取糖果？");
                IntentUtil.startActivity(context, TaskActivity.class);
                break;
        }
    }


    public void load(String id, String name) {
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
//        bundle.putString("name", name);
        IntentUtil.startActivity(context, BlActivity.class, bundle);
    }
}
