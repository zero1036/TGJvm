package com.tg.aop.cutbean;

import org.springframework.aop.framework.AopContext;

public class Driver {
    public void drivingCar() {
        //直接调用this本身方法，Spring AOP无法切入
        //this.startEngine();

        //通过AopContext获取当前对象的代理类，通过代理调用对象的方法，Spring AOP可以切入
        ((Driver) AopContext.currentProxy()).startEngine();
    }

    public void startEngine() {
        // try to start engine
    }
}
