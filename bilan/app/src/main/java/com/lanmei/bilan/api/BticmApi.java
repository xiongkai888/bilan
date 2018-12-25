package com.lanmei.bilan.api;

import android.content.Context;

import com.lanmei.bilan.bean.UserBean;
import com.lanmei.bilan.helper.UserHelper;
import com.xson.common.api.ApiV2;

/**
 * Created by xkai on 2018/1/8.
 */

public abstract class BticmApi extends ApiV2{

    public String key = "btb";//固定值，各接口需要（建议统一保存）


    @Override
    public  String getUserId(Context context) {
        UserBean userBean = UserHelper.getInstance(context).getUserBean();
        if(userBean != null) {
            return userBean.getId();
        }
        return "";
    }

    @Override
    public String getToken(Context context) {
        UserBean userBean = UserHelper.getInstance(context).getUserBean();
        if(userBean != null) {
            return userBean.getToken();
        }
        return "";
    }

}
