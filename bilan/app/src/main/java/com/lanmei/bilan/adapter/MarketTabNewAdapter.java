package com.lanmei.bilan.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.lanmei.bilan.bean.MarketClassifyBean;
import com.lanmei.bilan.ui.market.fragment.MarketXinFragment;
import com.xson.common.utils.StringUtils;

import java.util.List;


/**
 * Created by xkai on 2018/1/3.
 * 行情Adapter
 */

public class MarketTabNewAdapter extends FragmentStatePagerAdapter {


    private List<MarketClassifyBean> list;
    Bundle bundle = new Bundle();


    public MarketTabNewAdapter(FragmentManager fm,List<MarketClassifyBean> list) {
        super(fm);
        this.list = list;
    }


    @Override
    public Fragment getItem(int position) {
        MarketXinFragment fragment = new MarketXinFragment();
        bundle.putString("cid",list.get(position).getId());
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public int getCount() {
        return StringUtils.isEmpty(list)?0:list.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position).getClassname();
    }
}
