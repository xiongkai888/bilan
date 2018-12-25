package com.lanmei.bilan.api;

/**
 * Created by xkai on 2018/1/8.
 * 注册
 */

public class RegisterApi extends BticmApi{

    public String username;//用户名
    public String nickname;//昵称
    public String user_type;//身份  0|1 => 普通会员|商家
    public String password;//密码
    public String phone;//手机号
    public String rtid;//推荐人手机号
    public String open_type;//注册方式 0|1|2|3 => 本地注册|微信|QQ|微博

    @Override
    protected String getPath() {
        return "app/registered";
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
