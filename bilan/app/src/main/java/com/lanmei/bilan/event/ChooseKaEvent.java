package com.lanmei.bilan.event;

/**
 * Created by xkai on 2018/1/24.
 * 选择银行卡事件
 */

public class ChooseKaEvent {

    private String carName;
    private String banksNo;

    public String getBanksNo() {
        return banksNo;
    }

    public String getCarName() {
        return carName;
    }

    public ChooseKaEvent(String carName,String banksNo) {
        this.carName = carName;
        this.banksNo = banksNo;
    }
}
