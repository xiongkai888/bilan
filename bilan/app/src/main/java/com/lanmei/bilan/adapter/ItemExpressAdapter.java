package com.lanmei.bilan.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lanmei.bilan.ui.discover.fragment.ItemExpressFragment;

/**
 * Created by xkai on 2018/1/24.
 * 速递、梦想
 */
public class ItemExpressAdapter extends FragmentPagerAdapter {

    String id;

    public ItemExpressAdapter(FragmentManager fm,String id) {
        super(fm);
        this.id = id;
    }

    @Override
    public Fragment getItem(int position) {
        ItemExpressFragment fragment = new ItemExpressFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id",id);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public int getCount() {
        return 2;
    }

}
