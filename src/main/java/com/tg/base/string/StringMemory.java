package com.tg.base.string;

/**
 * Created by linzc on 2016/11/8.
 */
public class StringMemory {
    public static void main(String[] args) {
        String a = "hello";
        String b = "hello";

        String newA = new String("hello");
        String newB = new String("hello");

        System.out.println("****** Testing Object == ******");
        System.out.println("a==b ? :" + (a == b));                 //true，a、b都指向常量区同一块hello
        System.out.println("newA==newB ? :" + (newA == newB));     //false，string每次new，都会堆中新建一个hello
        System.out.println("a==newA ? :" + (a == newA));           //false，a指向常量区的hello，newA指向堆中hello

        //String的intern()方法是一个本地方法，定义为public native String intern();
        // intern()方法的价值在于让开发者能将注意力集中到String池上。
        // 当调用 intern 方法时，如果池已经包含一个等于此 String 对象的字符串（该对象由 equals(Object) 方法确定），则返回池中的字符串。
        // 否则，将此 String 对象添加到池中，并且返回此 String 对象的引用
        System.out.println("***** Testing String Object intern method******");
        System.out.println("a.intern()==b.intern() ? : " + (a.intern() == b.intern()));           //true，a与b的intern方法均返回常量区中的hello
        System.out.println("newA.intern()==newB.intern() ? :" + (newA.intern() == newB.intern()));//true，newA与newB的intern方法均返回常量区中的hello
        System.out.println("a.intern()==newA.intern() ? :" + (a.intern() == newA.intern()));      //true，a与newA的intern方法均返回常量区中的hello
        System.out.println("a=a.intern() ? :" + (a == a.intern()));                               //true，a与a的intern方法均返回常量区中的hello
        System.out.println("newA==newA.intern() ? : " + (newA == newA.intern()));                 //false，newA指向堆的hello，newA的intern指向常量区的hello

        //equals()方法是将参数，强转成string，再转换为char[]，然后迭代，逐个char进行比较，因此是两个string的值之间的比较
        //参考源码：public boolean equals(Object anObject) {
        System.out.println("****** Testing String Object equals method******");
        System.out.println("equals() method :" + a.equals(newA));       //true，参照以上说明，所以true

        String c = "hel";
        String d = "lo";
        final String finalc = "hel";
        final String finalgetc = getc();

        //"hel"+"lo" 指向常量区的hello，如果没有则创建一个
        //c + d，因为是不定式，不指向常量区，反而是在堆中每次都创建新的对象
        //finalc + finalgetc，因为加了final，是定式，所以指向常量区
        System.out.println("****** Testing Object splice ******");
        System.out.println("a==\"hel\"+\"lo\" ? :" + (a == "hel" + "lo"));
        System.out.println("a==c+d ? : " + (a == c + d));
        System.out.println("a==c+\"lo\" ? : " + (a == c + "lo"));
        System.out.println("a==finalc+\"lo\" ? :" + (a == finalc + "lo"));
        System.out.println("a==finalgetc+\"lo\" ? :" + (a == finalgetc + "lo"));

    }

    private static String getc() {
        return "hel";
    }
}
