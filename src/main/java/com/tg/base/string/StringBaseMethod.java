package com.tg.base.string;

import org.junit.Test;

/**
 * Created by linzc on 2016/11/9.
 */
public class StringBaseMethod {
    @Test
    public void test() {
        String a = "a:10";
        String b = "b:8";
        String c = "b:8";
        String d = new String("b:8");

        System.out.println(a.compareTo(b));//-1:小于
        System.out.println(b.compareTo(a));//1:大于
        System.out.println(c.compareTo(b));//0:等于
        System.out.println(d.compareTo(b));//compareTo只是值比较
    }
}
