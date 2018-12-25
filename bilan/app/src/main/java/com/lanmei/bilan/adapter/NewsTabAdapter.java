package com.lanmei.bilan.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lanmei.bilan.ui.home.fragment.HomeLianFragment;
import com.lanmei.bilan.ui.home.fragment.HomeMingFragment;
import com.lanmei.bilan.ui.news.fragment.NewsGuanFragment;
import com.lanmei.bilan.ui.news.fragment.NewsJingFragment;
import com.lanmei.bilan.ui.news.fragment.NewsXinFragment;


/**
 * Created by xkai on 2018/1/3.
 * 资讯Adapter
 */

public class NewsTabAdapter extends FragmentPagerAdapter {


    public NewsTabAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new NewsGuanFragment();
            case 1:
                return new NewsXinFragment();
            case 2:
                return new NewsJingFragment();
            case 3:
                return new HomeMingFragment();
            case 4:
                return new HomeLianFragment();
        }
        return null;
    }


    @Override
    public int getCount() {
        return 5;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "关注";
            case 1:
                return "新闻";
            case 2:
                return "精选";
            case 3:
                return "名人堂";
            case 4:
                return "链世界";
        }
        return "";
    }
}
