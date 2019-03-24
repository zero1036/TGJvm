package com.tg.base.var;

/**
 * Created by linzc on 2018/5/8.
 */
public class Info {
    public Info(String msg) {
        this.msg = msg;
    }

    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Info{" +
                "msg='" + msg + '\'' +
                '}';
    }
}
