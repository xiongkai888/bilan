package com.lanmei.bilan.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.lanmei.bilan.ui.market.fragment.MarketBi2Fragment;
import com.lanmei.bilan.ui.market.fragment.MarketChangFragment;
import com.lanmei.bilan.ui.market.fragment.MarketJiaoFragment;
import com.lanmei.bilan.ui.market.fragment.MarketXinFragment;


/**
 * Created by xkai on 2018/1/3.
 * 行情Adapter
 */

public class MarketTabAdapter extends FragmentStatePagerAdapter {


    public MarketTabAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new MarketBi2Fragment();
            case 1:
                return new MarketJiaoFragment();
            case 2:
                return new MarketXinFragment();
            case 3:
                return new MarketChangFragment();
        }
        return null;
    }


    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "币种信息";
            case 1:
                return "交易所";
            case 2:
                return "新币上线";
            case 3:
                return "外场发布";
        }
        return "";
    }
}
