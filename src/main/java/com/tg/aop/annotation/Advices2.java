package com.tg.aop.annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;

/**
 * Created by linzc on 2017/5/16.
 */
@Aspect
public class Advices2 {
    @After("execution(* com.tg.aop.annotation.*.*(..))")
    public void doAfter(JoinPoint joinPoint) {
        System.out.println("after method");
    }
}
