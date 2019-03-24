package com.tg.base.BigDecimal;

import org.junit.Test;

import java.math.BigDecimal;

/**
 * Created by linzc on 2018/7/11.
 */
public class MainTest {
    @Test
    public void test() {
        System.out.println(new BigDecimal(0.1).toString()); // 0.1000000000000000055511151231257827021181583404541015625
        System.out.println(new BigDecimal("0.1").toString()); // 0.1
        System.out.println(new BigDecimal(
                Double.toString(0.1000000000000000055511151231257827021181583404541015625)).toString());// 0.1
        System.out.println(new BigDecimal(Double.toString(0.1)).toString()); // 0.1

        System.out.println(new BigDecimal(0.1000000000000000055511151231257827021181583404541015625).toString());// 0.1000000000000000055511151231257827021181583404541015625



    }

    @Test
    public void test1(){
        System.out.println(0.05+0.01);
        System.out.println(1.0-0.42);
        System.out.println(4.015*100);
        System.out.println(123.3/100);
    }
}
