package com.lanmei.bilan.ui.goods;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.lanmei.bilan.R;
import com.lanmei.bilan.bean.GoodsDetailsBean;
import com.xson.common.app.BaseFragment;
import com.xson.common.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 商品详情
 */
public class GoodsDetailFragment extends BaseFragment {


    @InjectView(R.id.goods_detail)
    TextView goodsDetail;
    @InjectView(R.id.goods_config)
    TextView goodsConfig;
    @InjectView(R.id.tab_cursor)
    View tabCursor;

    private List<TextView> tabTextList = new ArrayList<>();
    private GoodsConfigFragment goodsConfigFragment;
    private GoodsInfoWebFragment goodsDetailWebFragment;
    private Fragment currFragment;
    private int currIndex;
    private  int fromX=0;
    GoodsDetailsBean bean;//商品信息bean

    @Override
    public int getContentViewId() {
        return R.layout.fragment_goods_detail;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (!StringUtils.isEmpty(bundle)) {
            bean = (GoodsDetailsBean) bundle.getSerializable("bean");
        }
        init();
    }

    private void init() {
        initView();
        initData();
    }

    private void initData() {
        tabTextList.add(goodsDetail);
        tabTextList.add(goodsConfig);
    }

    private void initView() {
        goodsConfigFragment = new GoodsConfigFragment();
        goodsDetailWebFragment = new GoodsInfoWebFragment();

        Bundle bundle = new Bundle();
        bundle.putString("content",bean.getContent());
        goodsDetailWebFragment.setArguments(bundle);

        currFragment = goodsDetailWebFragment;
        getChildFragmentManager().beginTransaction().replace(R.id.frameLayout_content, currFragment).commitAllowingStateLoss();
    }

    @OnClick({R.id.goods_detail, R.id.goods_config})
    public void onClick(View view) {
        switch (view.getId()) {
            //商品详情tab
            case R.id.goods_detail:
                switchFragment(currFragment, goodsDetailWebFragment);
                currIndex = 0;
                currFragment = goodsDetailWebFragment;
                scrollCursor();
                break;
            case R.id.goods_config:
                //规格参数tab
                switchFragment(currFragment, goodsConfigFragment);
                currIndex = 1;
                currFragment = goodsConfigFragment;
                scrollCursor();
                break;
        }
    }

    private void scrollCursor() {
        TranslateAnimation anim = new TranslateAnimation(fromX, currIndex * tabCursor.getWidth(), 0, 0);
        anim.setFillAfter(true);
        anim.setDuration(50);
        fromX = currIndex * tabCursor.getWidth();
        tabCursor.startAnimation(anim);

        //设置Tab切换颜色
        for (int i = 0; i < tabTextList.size(); i++) {
            tabTextList.get(i).setTextColor(i == currIndex ? getResources().getColor(R.color.colorPrimaryDark) : getResources().getColor(R.color.black));
        }
    }

    private void switchFragment(Fragment fromFragment, Fragment toFragment) {
        if (currFragment != toFragment) {
            if (!toFragment.isAdded()) {
                getChildFragmentManager().beginTransaction().hide(fromFragment).add(R.id.frameLayout_content, toFragment).commitAllowingStateLoss();
            } else {
                getChildFragmentManager().beginTransaction().hide(fromFragment).show(toFragment).commitAllowingStateLoss();
            }
        }
    }
}
