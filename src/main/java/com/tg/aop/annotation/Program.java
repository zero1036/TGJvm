package com.tg.aop.annotation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by linzc on 2017/5/9.
 */
public class Program {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("aopannotation.xml");
        Driver2 driver2 = ctx.getBean("driver2", Driver2.class);
        driver2.drivingCar();
    }
}
