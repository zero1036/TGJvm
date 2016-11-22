package com.base;

import java.util.ArrayList;
import java.util.List;

/**
 * 数字运算符基础
 * Created by linzc on 2016/11/21.
 */
public class MathBase {

    public static void main(String[] args) {
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

    /**
     * 输出一个int的二进制数
     *
     * @param num
     */
    private static void printInfo(int num) {
        System.out.println("二进制：" + Integer.toBinaryString(num) + "；十进制" + num);
    }
}
