package com.lanmei.bilan.helper;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lanmei.bilan.adapter.SignInAdapter;
import com.lanmei.bilan.bean.SignInBean;
import com.lanmei.bilan.bean.TaskBean;
import com.xson.common.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xkai on 2018/6/25.
 */

public class SignInHelper {

    private Context context;
    private RecyclerView recyclerView;
    String[] strings;
    TaskBean bean;

    public SignInHelper(Context context, RecyclerView recyclerView, TaskBean bean) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.bean = bean;
        loadList();
    }

    private void loadList() {
        if (bean == null) {
            return;
        }
        strings = bean.getBl().split(",");
        if (StringUtils.isEmpty(strings)) {
            return;
        }
        int size = strings.length;
        List<SignInBean> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            SignInBean signInBean = new SignInBean();
            signInBean.setTime(i + 1);
            signInBean.setIntegral(strings[i]);
            list.add(signInBean);
        }
        initList(list);
    }

    private void initList(List<SignInBean> list) {
        if (StringUtils.isEmpty(list)) {
            return;
        }
        SignInAdapter adapter = new SignInAdapter(context);
        adapter.setData(getSignInBeanList(list));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 7));
    }


    private List<SignInBean> getSignInBeanList(List<SignInBean> list) {
        int size = list.size();
        if (size > 7) {//只要后面七个
            List<SignInBean> beanList = new ArrayList<>();
            for (int i = size - 7; i < size; i++) {
                beanList.add(list.get(i));
            }
            return beanList;
        } else {
            return list;
        }
    }
}
