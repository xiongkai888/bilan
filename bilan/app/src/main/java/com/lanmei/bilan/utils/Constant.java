package com.lanmei.bilan.utils;

public class Constant {

    // 微信
    public static final String WEIXIN_APP_ID = "wx6f6feca9262310ff";
    public static final String  WEIXIN_APP_SECRET = "58c8a225259eea0913095b612e4c1fd1";
    public static final String WEIXIN_APP_PARTNER_SECRET = "qwertyuiop1234567890poiuytrewq12"; // 密钥
    public static final String WEIXIN_MCH_ID = "1444575002";
    public static final String WEIXIN_NOTIFY_URL = "" + "/api/pay/callbackWeiXinPay";

    // QQ
    public static final String QQ_APP_ID = "1106162164";
    public static final String QQ_APP_SECRET = "1Bsm8BgiDDtzrPcy";
    // 新浪微博
    public static final String SINA_APP_ID = "3662508514";
    public static final String SINA_APP_SECRET = "e5cd8fe16d371d1bfb9fcbd309b77204";
    //回调地址
    public static final String SINA_NOTIFY_URL = "http://www.sou37.net";


    // 这个是外网域名的后半部分(外网域名 = bucket.endpoint)，根据你申请的填一般是"http://oss-cn-hangzhou.aliyuncs.com"
    public static final String endpoint = "oss-cn-shenzhen.aliyuncs.com";

    public static final String accessKeyId = "LTAIeMCn6aSPlJ8H";  // 你申请的accessKeyId
    public static final String accessKeySecret = "3ZIjMTS4Elx4MXXVrZ00O0MjpNosKX"; // accessKeySecret

    public static final String testBucket = "stdrimages";  // 这个就是你申请的bucket名称

    //支付宝回调
    public static final String ALIPAY_NOTIFY_URL = "" + "payment/callback/_id/1";

}
