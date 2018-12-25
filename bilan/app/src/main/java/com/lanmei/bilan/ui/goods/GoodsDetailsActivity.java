package com.lanmei.bilan.ui.goods;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.lanmei.bilan.BtcimApp;
import com.lanmei.bilan.R;
import com.lanmei.bilan.adapter.GoodsDetailsPagerAdapter;
import com.lanmei.bilan.api.CollectGoodsApi;
import com.lanmei.bilan.api.GoodsDetailsApi;
import com.lanmei.bilan.bean.GoodsDetailsBean;
import com.lanmei.bilan.bean.InviteBean;
import com.lanmei.bilan.event.PaySucceedEvent;
import com.lanmei.bilan.event.ShowShopCountEvent;
import com.lanmei.bilan.helper.ShareHelper;
import com.lanmei.bilan.helper.UserHelper;
import com.lanmei.bilan.ui.goods.shop.DBShopCartHelper;
import com.lanmei.bilan.ui.goods.shop.ShopCarActivity;
import com.lanmei.bilan.utils.CommonUtils;
import com.lanmei.bilan.utils.SharedAccount;
import com.xson.common.app.BaseActivity;
import com.xson.common.bean.BaseBean;
import com.xson.common.bean.DataBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.utils.IntentUtil;
import com.xson.common.utils.StringUtils;
import com.xson.common.utils.UIHelper;
import com.xson.common.widget.CenterTitleToolbar;
import com.xson.common.widget.NoScrollViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 商品详情（商品、详情、评论）
 */
public class GoodsDetailsActivity extends BaseActivity implements TabLayout.OnTabSelectedListener {

    @InjectView(R.id.tabLayout)
    TabLayout tabLayout;
    @InjectView(R.id.toolbar)
    CenterTitleToolbar toolbar;
    @InjectView(R.id.viewPager)
    NoScrollViewPager viewPager;
    @InjectView(R.id.collect_iv)
    ImageView collectIv;
    GoodsDetailsBean bean;//商品详情信息
    String id;//商品详情ID
    AddShopCarDialogFragment mDialog;
    private static final String DIALOG = "dialog_fragment_btcim";
    private ShareHelper mShareHelper;
    InviteBean inviteBean;

    @Override
    public void initIntent(Intent intent) {
        super.initIntent(intent);
        id = intent.getStringExtra("value");
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_goods_details;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle("");
        actionbar.setHomeAsUpIndicator(R.mipmap.back_w);

        EventBus.getDefault().register(this);

        GoodsDetailsApi api = new GoodsDetailsApi();
        api.id = id;
        api.uid = api.getUserId(this);
        HttpClient.newInstance(this).loadingRequest(api, new BeanRequest.SuccessListener<DataBean<GoodsDetailsBean>>() {
            @Override
            public void onResponse(DataBean<GoodsDetailsBean> response) {
                if (isFinishing()) {
                    return;
                }
                bean = response.data;
                if (!StringUtils.isEmpty(bean)) {
                    init(bean);
                }
            }
        });

        //分享帮助初始化
        mShareHelper = new ShareHelper(this);
        inviteBean = SharedAccount.getInstance(this).getInviteBean();
    }


    int favorite;//是否收藏了该商品

    private void init(GoodsDetailsBean bean) {
        favorite = bean.getFavorite();
        if (favorite == 1) {
            collectIv.setImageResource(R.mipmap.icon_collect_on);
        } else {
            collectIv.setImageResource(R.mipmap.icon_collect_off);
        }
        tabLayout.addOnTabSelectedListener(this);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(new GoodsDetailsPagerAdapter(getSupportFragmentManager(), bean));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    public void operaTitleBar(boolean scroAble, boolean titleVisiable) {
        viewPager.setNoScroll(scroAble);
        if (titleVisiable) {
            toolbar.setTitle("图文详情");
        } else {
            toolbar.setTitle("");
        }
        tabLayout.setVisibility(titleVisiable ? View.GONE : View.VISIBLE);
    }

    //到评论
    public void toCommentPager() {
        viewPager.setCurrentItem(2);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();
        viewPager.setCurrentItem(position);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    ShopActionProvider mActionProvider;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_goods_details, menu);

        MenuItem menuItem = menu.findItem(R.id.action_shop_car);
        mActionProvider = (ShopActionProvider) MenuItemCompat.getActionProvider(menuItem);
        mActionProvider.setOnClickListener(new ShopActionProvider.OnClickListener() {
            @Override
            public void onClick() {
                if (!CommonUtils.isLogin(GoodsDetailsActivity.this)) {
                    return;
                }
                IntentUtil.startActivity(GoodsDetailsActivity.this, ShopCarActivity.class);
            }
        });// 设置点击监听。
        if (!UserHelper.getInstance(this).hasLogin()) {
            mActionProvider.setCount(0);
        } else {
            mActionProvider.setCount(DBShopCartHelper.getInstance(BtcimApp.applicationContext).getShopCarListCount());
        }
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (!CommonUtils.isLogin(this)) {
            return super.onOptionsItemSelected(item);
        }
        switch (item.getItemId()) {
            case R.id.action_share:
//                mShareHelper.showShareDialog();
                if (inviteBean != null) {
                    mShareHelper.setShareUrl(inviteBean.getShare_url());
                    mShareHelper.showShareDialog();
                } else {
                    CommonUtils.loadSystem(this, new CommonUtils.LoadSystemListener() {
                        @Override
                        public void loadSystem(InviteBean invitebean) {
                            inviteBean = invitebean;
                            mShareHelper.setShareUrl(inviteBean.getShare_url());
                            mShareHelper.showShareDialog();
                        }
                    });
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @OnClick({R.id.ll_collect, R.id.ll_service, R.id.add_shop_car_tv, R.id.pay_now_tv})
    public void onViewClicked(View view) {
        if (!CommonUtils.isLogin(this)) {
            return;
        }
        if (StringUtils.isEmpty(bean)) {
            return;
        }
        switch (view.getId()) {
            case R.id.ll_collect://收藏
                loadCollect();
                break;
            case R.id.ll_service://在线客服
                UIHelper.ToastMessage(this, R.string.developing);
//                CommonUtils.startChatActivity(this, SharedAccount.getInstance(this).getServiceId(), false);
                break;
            case R.id.add_shop_car_tv://加入购物车
                addShopCar();
                break;
            case R.id.pay_now_tv://立即购买
                addShopCar();
                break;
        }
    }

    private void addShopCar() {
        if (mDialog == null) {
            mDialog = new AddShopCarDialogFragment();
            mDialog.setData(bean);
        }
        mDialog.show(getSupportFragmentManager(), DIALOG);
    }

    private void loadCollect() {
        if (StringUtils.isEmpty(id)) {
            return;
        }
        CollectGoodsApi api = new CollectGoodsApi();
        api.pro_id = id;
        api.uid = api.getUserId(this);
        HttpClient.newInstance(this).loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                if (isFinishing()) {
                    return;
                }
                String collect = "";
                if (favorite == 1) {
                    favorite = 0;
                    collect = getString(R.string.cancel_collected);
                    collectIv.setImageResource(R.mipmap.icon_collect_off);
                } else {
                    collect = getString(R.string.collected);
                    favorite = 1;
                    collectIv.setImageResource(R.mipmap.icon_collect_on);
                }
                UIHelper.ToastMessage(GoodsDetailsActivity.this, collect);
            }
        });
    }

    //支付成功调用
    @Subscribe
    public void paySucceedEvent(PaySucceedEvent event) {
        finish();
    }

    //(加入购物车、删除购物车)显示购物车数量事件
    @Subscribe
    public void showShopCarCountEvent(ShowShopCountEvent event) {
        mActionProvider.setCount(DBShopCartHelper.getInstance(BtcimApp.applicationContext).getShopCarListCount());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        mShareHelper.onDestroy();
    }

    /**
     * 结果返回
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mShareHelper.onActivityResult(requestCode, resultCode, data);
    }

}
