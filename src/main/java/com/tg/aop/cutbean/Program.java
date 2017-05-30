package com.tg.aop.cutbean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by linzc on 2017/5/9.
 */
public class Program {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("cutbean.xml");
        Driver driver = ctx.getBean("driver", Driver.class);
        driver.drivingCar();
    }
}
