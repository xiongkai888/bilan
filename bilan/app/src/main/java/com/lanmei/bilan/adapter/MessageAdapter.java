package com.lanmei.bilan.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hyphenate.chatuidemo.ui.ConversationListFragment;
import com.lanmei.bilan.ui.message.fragment.MessageSheFragment;

/**
 * Created by xkai on 2018/1/3.
 * 消息 -- 消息与社区
 */
public class MessageAdapter extends FragmentPagerAdapter {

    public MessageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 1:
                ConversationListFragment fragment = new ConversationListFragment();
                Bundle bundle = new Bundle();
                bundle.putString("hide","hide");//隐藏titleBar
                fragment.setArguments(bundle);
                return fragment;
//                fragment = new MessageXiaoFragment();//消息
//                break;
            case 0:
                return new MessageSheFragment();//社区

        }
        return null;
    }


    @Override
    public int getCount() {
        return 2;
    }

}
