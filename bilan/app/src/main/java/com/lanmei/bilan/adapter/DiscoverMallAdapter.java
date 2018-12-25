package com.lanmei.bilan.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lanmei.bilan.ui.discover.fragment.DiscoverFaFragment;

/**
 * Created by xkai on 2018/1/24.
 * 比特商城
 */
public class DiscoverMallAdapter extends FragmentPagerAdapter {

    public DiscoverMallAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        DiscoverFaFragment fragment = new DiscoverFaFragment();
        Bundle bundle = new Bundle();
        switch (position){
            case 0:
                bundle.putString("id","126");
                break;
            case 1:
                bundle.putString("id","125");
                break;
        }
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public int getCount() {
        return 2;
    }

}
