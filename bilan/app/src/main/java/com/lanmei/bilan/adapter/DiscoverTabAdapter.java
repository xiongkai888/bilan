package com.lanmei.bilan.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lanmei.bilan.ui.discover.fragment.DiscoverMallNewFragment;
import com.lanmei.bilan.ui.discover.fragment.DiscoverXiangFragment;
import com.lanmei.bilan.ui.discover.fragment.DiscoverZhiFragment;


/**
 * Created by xkai on 2018/1/31.
 * 发现Adapter
 */

public class DiscoverTabAdapter extends FragmentPagerAdapter {

    Bundle bundle = new Bundle();

    public DiscoverTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                bundle.putString("id","130");
                fragment = new DiscoverXiangFragment();
                break;
            case 1:
                fragment = new DiscoverMallNewFragment();
                break;
            case 2:
                bundle.putString("id","127");
                fragment = new DiscoverZhiFragment();
                break;
        }
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "项目速递";
            case 1:
                return "币澜商城";
            case 2:
                return "活动报名";
        }
        return "";
    }
}
