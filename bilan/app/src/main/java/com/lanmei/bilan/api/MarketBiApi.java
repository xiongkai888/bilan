package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/1/3.
 * 行情 （币种信息分类）
 */

public class MarketBiApi extends BticmApi{

    public String coinName;//查询的币种名称(参考首页币种英文名称)
    public String payCoinName;//支付币种名称(港币:HKD,其余币种取币种英文名称)

    @Override
    protected String getPath() {
        return "";
    }

    @Override
    public Method requestMethod() {
        return Method.GET;
    }
}
