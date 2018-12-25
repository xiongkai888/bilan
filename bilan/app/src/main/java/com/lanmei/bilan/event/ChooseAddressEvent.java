package com.lanmei.bilan.event;

import com.lanmei.bilan.bean.AddressListBean;

/**
 * Created by xkai on 2018/1/22.
 * 选择地址
 */

public class ChooseAddressEvent {

    AddressListBean bean;

    public AddressListBean getBean() {
        return bean;
    }

    public ChooseAddressEvent(AddressListBean bean){
        this.bean = bean;
    }
}
