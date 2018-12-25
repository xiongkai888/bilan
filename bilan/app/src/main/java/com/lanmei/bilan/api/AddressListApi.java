package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/1/22.
 * 收货地址
 */

public class AddressListApi extends BticmApi {

    public String operation;//1|2|3|4=>添加|修改|删除|列表
    public String userid;//用户ID
    public String uname;//姓名
    public String uphone;//手机号
    public String uarea;//地址
    public String province;//省ID
    public String city;//市ID
    public String area;//区ID
    public String mDefault;//默认地址
    public String id;//删除时需要



    @Override
    protected String getPath() {
        return "app/address";
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
