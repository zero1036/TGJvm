package com.tg.base.proxy;

import net.sf.cglib.proxy.Enhancer;

//import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import net.sf.cglib.proxy.InvocationHandler;

/**
 * Created by linzc on 2017/5/19.
 */
public class CglibProxy {

    @SuppressWarnings("unchecked")
    public static <T> T createProxy(final T t) {
        Enhancer enhancer = new Enhancer();
        enhancer.setClassLoader(CglibProxy.class.getClassLoader());
        enhancer.setSuperclass(t.getClass());
        enhancer.setCallback(new InvocationHandler() {
            //            @Override
            public Object invoke(Object proxy, Method method, Object[] args)
                    throws Throwable {
                Object ret = null;
                if (method.getName().equals("sayHello")) {
                    doBefore();
                    try {
                        ret = method.invoke(t, args);
                    } catch (Exception e) {
                        doThrowing();
                    }
                    doAfter();
                }
                return ret;
            }

        });
        return (T) enhancer.create();
    }

    private static void doThrowing() {
        System.out.println("AOP say throw a exception");
    }

    private static void doBefore() {
        System.out.println("AOP before say");
    }

    private static void doAfter() {
        System.out.println("AOP after say");
    }
}
