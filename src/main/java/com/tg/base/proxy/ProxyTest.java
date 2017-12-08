package com.tg.base.proxy;

/**
 * Created by linzc on 2017/5/17.
 */
public class ProxyTest {
    public static void main(String[] args) {

        Say say1 = new Person("lg");
        ClassLoader classLoader = ProxyTest.class.getClassLoader();
        Class<?>[] interfaces = say1.getClass().getInterfaces();

        Class<?> interfaceClass= null;
        for (Class<?> intf : interfaces) {
            try {
                interfaceClass  = Class.forName(intf.getName(), false, classLoader);
            } catch (ClassNotFoundException ex) {
            }
        }

//        jdkProxyTest();

//        cglibTest();
    }

    private static void jdkProxyTest() {
        Say say1 = new Person("lg");
        say1 = (Say) JDKDynamicProxy.createProxy(say1);
        say1.sayHello();

        System.out.println("-------------------------------");

        Say say2 = new Animals();
        say2 = (Say) JDKDynamicProxy.createProxy(say2);
        say2.sayHello();
    }

    private static void cglibTest() {
        Person p = new Person("lg");
        p = CglibProxy.createProxy(p);
        p.sayHello();

        System.out.println("-------------------------------");

        Animals animals = new Animals();
        animals = CglibProxy.createProxy(animals);
        animals.sayHello();
    }
}
