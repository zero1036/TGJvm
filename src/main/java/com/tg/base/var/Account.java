package com.tg.base.var;

/**
 * Created by linzc on 2018/5/8.
 */
public class Account {

    public Account(String name) {
        this.name = name;
    }

    private String name;
    private Info info;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "Account{" +
                "name='" + name + '\'' +
                ", info=" + info +
                '}';
    }
}
