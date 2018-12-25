package com.lanmei.bilan.ui.goods;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lanmei.bilan.R;
import com.lanmei.bilan.api.AddressListApi;
import com.lanmei.bilan.api.BalancePayApi;
import com.lanmei.bilan.bean.AddressListBean;
import com.lanmei.bilan.bean.GoodsDetailsBean;
import com.lanmei.bilan.event.AlterAddressEvent;
import com.lanmei.bilan.event.ChooseAddressEvent;
import com.lanmei.bilan.event.PaySucceedEvent;
import com.lanmei.bilan.ui.goods.shop.DBhelper;
import com.lanmei.bilan.ui.mine.activity.MineOrderActivity;
import com.lanmei.bilan.utils.AKDialog;
import com.lanmei.bilan.utils.CommonUtils;
import com.xson.common.app.BaseActivity;
import com.xson.common.bean.BaseBean;
import com.xson.common.bean.NoPageListBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.helper.ImageHelper;
import com.xson.common.utils.DoubleUtil;
import com.xson.common.utils.IntentUtil;
import com.xson.common.utils.L;
import com.xson.common.utils.StringUtils;
import com.xson.common.utils.UIHelper;
import com.xson.common.widget.CenterTitleToolbar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * （确认订单）
 */
public class ConfirmOrderActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    CenterTitleToolbar mToolbar;
    @InjectView(R.id.name_tv)
    TextView nameTv;
    @InjectView(R.id.address_tv)
    TextView addressTv;
    @InjectView(R.id.image)
    ImageView image;
    @InjectView(R.id.content_tv)
    TextView contentTv;
    @InjectView(R.id.price_tv)
    TextView priceTv;
    @InjectView(R.id.num_tv)
    TextView numTv;
    @InjectView(R.id.goods_num_tv)
    TextView goodsNumTv;
    @InjectView(R.id.fee_tv)
    TextView feeTv;
    @InjectView(R.id.goods_price_tv)
    TextView goodsPriceTv;
    @InjectView(R.id.total_price_tv)
    TextView totalPriceTv;
    GoodsDetailsBean bean;
    AddressListBean addressBean;//地址信息
    int num;//购买的数量(总的数量)
    String numStr = "";
    @InjectView(R.id.yu_e_cn)
    CheckBox yuECn;
    @InjectView(R.id.zhifubao_cb)
    CheckBox zhifubaoCb;
    @InjectView(R.id.weixin_cb)
    CheckBox weixinCb;
    int payWay = 1;//支付方式
    @InjectView(R.id.ll_item_goods)
    LinearLayout llItemGoods;

    @Override
    public int getContentViewId() {
        return R.layout.activity_confirm_order;
    }

    @Override
    public void initIntent(Intent intent) {
        super.initIntent(intent);
        Bundle bundle = intent.getBundleExtra("bundle");
        if (!StringUtils.isEmpty(bundle)) {
            bean = (GoodsDetailsBean) bundle.getSerializable("bean");
            num = bundle.getInt("num");
        }
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        llItemGoods.removeAllViews();
        setSupportActionBar(mToolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle(R.string.order_details);
        actionbar.setHomeAsUpIndicator(R.mipmap.back);

        checkAddress();
        setData();

        EventBus.getDefault().register(this);
        loadAddressList();
        setCheckBoxListener();
    }

    private void setCheckBoxListener() {
        yuECn.setChecked(true);
        yuECn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                setCheckBoxStatue(zhifubaoCb, weixinCb, b, 1);
            }
        });
        zhifubaoCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                setCheckBoxStatue(yuECn, weixinCb, b, 2);
            }
        });
        weixinCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                setCheckBoxStatue(yuECn, zhifubaoCb, b, 3);
            }
        });
    }

    private void setCheckBoxStatue(CheckBox cb1, CheckBox cb2, boolean check, int way) {
        if (check) {
            cb1.setChecked(false);
            cb2.setChecked(false);
            payWay = way;
        } else {
            payWay = -1;
        }
    }

    private void loadAddressList() {
        AddressListApi api = new AddressListApi();
        api.userid = api.getUserId(this);
        api.operation = "4";
        HttpClient.newInstance(this).request(api, new BeanRequest.SuccessListener<NoPageListBean<AddressListBean>>() {
            @Override
            public void onResponse(NoPageListBean<AddressListBean> response) {
                List<AddressListBean> list = response.data;
                if (!StringUtils.isEmpty(list)) {
                    for (AddressListBean bean : list) {
                        if (!StringUtils.isEmpty(bean) && StringUtils.isSame("1", bean.getDefaultX())) {
                            chooseAddress(bean);
                            return;
                        }
                    }
                }
                checkAddress();
            }
        });
    }

    private void checkAddress() {
        nameTv.setVisibility(View.GONE);
        addressTv.setText(R.string.choose_address);
    }

    private void chooseAddress(AddressListBean bean) {
        addressBean = bean;
        nameTv.setVisibility(View.VISIBLE);
        nameTv.setText(bean.getName() + "\u3000" + bean.getPhone());
        addressTv.setText(bean.getAddress());
    }

    String proId = "";

    private void setData() {
        if (StringUtils.isEmpty(bean)) {
            return;
        }
        double price = 0;
        List<GoodsDetailsBean.ProductsBean> list = bean.getProducts();
        if (!StringUtils.isEmpty(list)) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                GoodsDetailsBean.ProductsBean productsBean = list.get(i);
                proId += productsBean.getId()+",";
                numStr += productsBean.getCount()+",";
                price += productsBean.getCount()*Double.valueOf(productsBean.getSell_price());
                addView(llItemGoods,productsBean);
            }

        }
        if (!StringUtils.isEmpty(proId)){
            proId = proId.substring(0,proId.length()-1);
        }
        if (!StringUtils.isEmpty(numStr)){
            numStr = numStr.substring(0,numStr.length()-1);
        }
        L.d(DBhelper.TAG,proId+"  ,  "+numStr);
        numTv.setText(num + "件");
        String fee = bean.getFee();
        if (StringUtils.isEmpty(fee)){
            fee = CommonUtils.isZero ;
        }
        feeTv.setText(String.format(getString(R.string.price_sub),fee));
        goodsPriceTv.setText(String.format(getString(R.string.price_sub), DoubleUtil.formatFloatNumber(price)));
        totalPriceTv.setText("总价：￥" + DoubleUtil.formatFloatNumber(price));
    }

    private void addView(LinearLayout root, GoodsDetailsBean.ProductsBean bean) {
        View view = LayoutInflater.from(this).inflate(R.layout.item_discover_two, null);
        root.addView(view);
        new GoodsViewViewHolder(view, bean);
    }

    public class GoodsViewViewHolder {

        @InjectView(R.id.image)
        ImageView image;
        @InjectView(R.id.content_tv)
        TextView contentTv;
        @InjectView(R.id.price_tv)
        TextView priceTv;
        @InjectView(R.id.goods_num_tv)
        TextView goodsNumTv;

        public GoodsViewViewHolder(View view, GoodsDetailsBean.ProductsBean bean) {
            ButterKnife.inject(this, view);
            ImageHelper.load(ConfirmOrderActivity.this,bean.getProducts_img(),image,null,true, R.mipmap.default_pic,R.mipmap.default_pic);
            contentTv.setText(bean.getName());
            priceTv.setText(String.format(ConfirmOrderActivity.this.getString(R.string.price_sub),bean.getSell_price()));
            goodsNumTv.setText(bean.getCount()+ConfirmOrderActivity.this.getString(R.string.jian));
        }

    }

    //选择地址时候调用
    @Subscribe
    public void chooseAddressEvent(ChooseAddressEvent event) {
        AddressListBean addressListBean = event.getBean();
        chooseAddress(addressListBean);
    }
    //编辑、删除地址时候调用
    @Subscribe
    public void alterAddressEvent(AlterAddressEvent event) {
        addressBean = null;
        loadAddressList();
    }


    @OnClick({R.id.ll_address, R.id.submit_order_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_address:
                IntentUtil.startActivity(this, AddressListActivity.class);
                break;
            case R.id.submit_order_tv:
                submitOrder();
                break;
        }
    }

    private void submitOrder() {
        if (StringUtils.isEmpty(addressBean)) {
            UIHelper.ToastMessage(this, R.string.choose_address);
            return;
        }
        if (payWay == -1) {
            UIHelper.ToastMessage(this, "请选择支付方式");
            return;
        }
        AKDialog.getAlertDialog(this, "确认要提交订单？", new AKDialog.AlertDialogListener() {
            @Override
            public void yes() {
                loadOrder();
            }
        });
    }

    private void loadOrder() {
        BalancePayApi api = new BalancePayApi();
        api.userid = api.getUserId(this);
        api.pro_id = proId;
        api.num = numStr;
        api.uname = addressBean.getUname();
        api.uphone = addressBean.getUphone();
        api.uarea = addressBean.getAddress();
        api.address_id = addressBean.getId();
        HttpClient.newInstance(this).loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                if (isFinishing()) {
                    return;
                }
                int code = response.getCode();
                if (code == 1) {
                    UIHelper.ToastMessage(ConfirmOrderActivity.this, "支付成功");
                    EventBus.getDefault().post(new PaySucceedEvent());
                    IntentUtil.startActivity(ConfirmOrderActivity.this, MineOrderActivity.class);
                    finish();
                } else if (code == 10) {
                    UIHelper.ToastMessage(ConfirmOrderActivity.this, getString(R.string.lack_parameter));
                } else {
                    UIHelper.ToastMessage(ConfirmOrderActivity.this, "余额不足");
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
