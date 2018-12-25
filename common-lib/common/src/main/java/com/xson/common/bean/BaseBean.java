package com.xson.common.bean;

/**
 * @author xson
 * 带状态和描述的bean
{
"status":0,
"msg":"成功"
}
 */
public class BaseBean {

    private int status;
    private String msg;


    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private int code = 1;
    private String info;//三人 人写三种不同的风格，不统一

    public String getMsg() {
        return msg;
    }


    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }



    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
