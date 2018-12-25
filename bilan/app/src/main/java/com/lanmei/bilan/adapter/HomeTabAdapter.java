package com.lanmei.bilan.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lanmei.bilan.ui.home.fragment.HomeBiFragment;
import com.lanmei.bilan.ui.home.fragment.HomeQuFragment;


/**
 * Created by xkai on 2018/1/3.
 * 首页Adapter
 */

public class HomeTabAdapter extends FragmentPagerAdapter {


    public HomeTabAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new HomeBiFragment();
            case 1:
                return new HomeQuFragment();
//            case 2:
//                return new HomeMingFragment();
//            case 3:
//                return new HomeLianFragment();
        }
        return null;
    }


    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "快讯";
            case 1:
                return "动态";
//            case 2:
//                return "名人堂";
//            case 3:
//                return "链世界";
        }
        return "";
    }
}
