package com.base;

import java.lang.reflect.Field;

/**
 * Created by linzc on 2016/11/9.
 */
public class StringChange {

    //String不可变，但可以通过反射得出String的成员变量value（char[]），并修改其数组的字符达到修改string效果
    //补充一点：string的value由private final char value[]修饰，final只是表示不可改变value指向的数组，但可以改变该数组
    //结论：证明String其实是可变的，但通过反射修改所谓的不可变对象是不推荐的
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        //创建字符串"Hello World"， 并赋给引用s
        String s = "Hello World";

        System.out.println("s = " + s); //Hello World

        //获取String类中的value字段
        Field valueFieldOfString = String.class.getDeclaredField("value");

        //改变value属性的访问权限
        valueFieldOfString.setAccessible(true);

        //获取s对象上的value属性的值
        char[] value = (char[]) valueFieldOfString.get(s);

        //改变value所引用的数组中的第5个字符
        value[5] = '_';

        System.out.println("s = " + s);  //Hello_World
    }
}
