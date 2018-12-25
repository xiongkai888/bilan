package com.lanmei.bilan.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lanmei.bilan.ui.mine.fragment.MineOrderFragment;


/**
 * 我的订单 0全部、1未付款、2待收货、3已完成
 */
public class MineOrderAdapter extends FragmentPagerAdapter {

    public MineOrderAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new MineOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("status",position);//未完成
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }



}
