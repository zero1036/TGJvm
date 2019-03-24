package com.tg.base.math;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * 数字运算符基础
 * Created by linzc on 2016/11/21.
 */
public class MathBase {

    @Test
    public void test_1() {
//        Object ccc = "3d56";


//        String ccc = "99.9995";
//        BigDecimal bigDecimal = new BigDecimal(ccc);
//        System.out.println(bigDecimal);


        String value = "99.9995";

        Double var1 = Double.parseDouble(value);
        BigDecimal var2 = new BigDecimal(var1);
        BigDecimal var3 = new BigDecimal(value);

        System.out.println(var1);
        System.out.println(var2);
        System.out.println(var3);
        System.out.println(Objects.equals(var2, var3));
    }

    /**
     * 保留两位小数测试
     */
    @Test
    public void test2() {
        System.out.println(new BigDecimal(0.2).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());// 0.2
        System.out.println(new BigDecimal(0.235).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());// 0.23
        System.out.println(new BigDecimal(0.2351).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());// 0.24
        System.out.println(new BigDecimal(42).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());// 42.0

        System.out.println(new BigDecimal(0.239).setScale(2, BigDecimal.ROUND_DOWN));
    }

    public static void main(String[] args) {
//        test1();
//        zuoyi();
//        youyi();
//        youyi2();
//        XOR();
//        learnHashMethod();
        mAND();
    }

    private static void test1() {
        int number = 10;
        //原始数二进制
        printInfo(number);
        number = number << 2;
        //左移一位
        printInfo(number);
        number = number >> 1;
        //右移一位
        printInfo(number);

//        打印结果
//        二进制：1010；十进制10
//        二进制：101000；十进制40
//        二进制：10100；十进制20
    }

    /*
    左移运算符，将运算符左边的对象向左移动运算符右边指定的位数（在低位补0）
     */
    private static void zuoyi() {
        int number = 3; //二进制：11
        int target = 0;
        printInfo(target = number << 1);//110-6
        printInfo(target = number << 2);//1100-12
        printInfo(target = number << 3);//11000-24
        printInfo(target = number << 4);//110000-48
    }

    /*
    "有符号"右移运算 符，将运算符左边的对象向右移动运算符右边指定的位数。使用符号扩展机制，也就是说，如果值为正，则在高位补0，如果值为负，则在高位补1.
     */
    private static void youyi() {
        int number = 30; //二进制：11110
        int target = 0;
        printInfo(target = number >> 1);//1111-15
        printInfo(target = number >> 2);//111-7
        printInfo(target = number >> 3);//11-3
        printInfo(target = number >> 4);//1-1
    }

    private static void youyi2() {
        int number = 30; //二进制：11110
        int target = 0;
        printInfo(target = number >>> 1);//1111-15
        printInfo(target = number >>> 2);//111-7
        printInfo(target = number >>> 3);//11-3
        printInfo(target = number >>> 4);//1-1


    }

    private static void mAND() {
        int num = 156546;
        int target = 0;

        printInfo(target = 15 & (num + 1));//二进制：11；十进制3
        printInfo(target = 15 & (num + 2));//二进制：100；十进制4
        printInfo(target = 15 & (num + 3));//二进制：101；十进制5
        printInfo(target = 15 & (num + 4));//二进制：110；十进制6
        printInfo(target = 15 & (num + 5));//二进制：111；十进制7
        printInfo(target = 15 & (num + 6));//二进制：1000；十进制8
        printInfo(target = 15 & (num + 7));//二进制：1001；十进制9
        printInfo(target = 15 & (num + 8));//二进制：1010；十进制10
    }

    private static void XOR() {
//        int number = 10;
        int target = 0;

        printInfo(target = 10 ^ 1);//二进制：1011；十进制11
        printInfo(target = 11 ^ 1);//二进制：1010；十进制10
        printInfo(target = 12 ^ 1);//二进制：1101；十进制13
        printInfo(target = 13 ^ 1);//二进制：1101；十进制12

        printInfo(target = 14 ^ 1);//二进制：1111；十进制15
        printInfo(target = 15 ^ 1);//二进制：1110；十进制14
        printInfo(target = 16 ^ 1);//二进制：10001；十进制17
        printInfo(target = 17 ^ 1);//二进制：10000；十进制16
    }

    private static void learnHashMethod() {
//        static final int hash(Object key) {
//            int h;
//            return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
//        }

//        1111111111111111111111111111111111110001110001111011001011010000


        int number = 65536; //二进制：65536

        int target = 0;
        printInfo(target = number);
        printInfo(target = number >>> 16);//65536-1

        number = 65537;
        printInfo(target = number);       //二进制：10000000000000001；十进制65537
        printInfo(target = number >>> 16);//二进制：1；十进制1

        number = 65536;
        printInfo(target = number << 1);  //二进制：100000000000000000；十进制131072
        printInfo(target = target >>> 16);//二进制：10；十进制2
    }

    /**
     * 输出一个int的二进制数
     *
     * @param num
     */
    private static void printInfo(int num) {
        System.out.println("二进制：" + Integer.toBinaryString(num) + "；十进制" + num);
    }
}
