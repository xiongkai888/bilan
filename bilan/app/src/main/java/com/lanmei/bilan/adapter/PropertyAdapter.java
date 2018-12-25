package com.lanmei.bilan.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lanmei.bilan.ui.mine.fragment.RechargeRecordFragment;

/**
 * （消费明细、充值记录）
 */
public class PropertyAdapter extends FragmentPagerAdapter {

    public PropertyAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        RechargeRecordFragment fragment = new RechargeRecordFragment();
        Bundle bundle = new Bundle();
        switch (position) {
            case 0:
                bundle.putInt("type",1);
                break;
            case 1:
                bundle.putInt("type",0);//0|1 =>充值|消费
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
