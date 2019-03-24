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

    @Test
    public void test2(){
        String str1 = new StringBuilder("计算机").append("软件").toString();
        System.out.println(str1.intern() == str1);

        String str2 = new StringBuilder("ja").append("va").toString();
        System.out.println(str2.intern() == str2);

        String str3 = "abc";
        String str4 = new String("abc");
        System.out.println(str3 == str4);

        System.out.println(str3 == str4.intern());
    }
}
