package com.lanmei.bilan.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.lanmei.bilan.ui.discover.DiscoverFragment;
import com.lanmei.bilan.ui.home.HomeFragment;
import com.lanmei.bilan.ui.market.fragment.MarketBi2Fragment;
import com.lanmei.bilan.ui.mine.MineFragment;
import com.lanmei.bilan.ui.news.NewsFragment;


public class MainPagerAdapter extends FragmentStatePagerAdapter {

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();//快讯
            case 1:
                return new NewsFragment();//资讯
            case 2:
                return new MarketBi2Fragment();//行情
            case 3:
                return new DiscoverFragment();//发现
            case 4:
                return new MineFragment();//我的

        }
        return null;
    }

    @Override
    public int getCount() {
        return 5;
    }

}
