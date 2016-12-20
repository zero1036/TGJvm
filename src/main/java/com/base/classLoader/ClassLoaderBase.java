package com.base.classLoader;

import com.first.First;

/**
 * Created by Administrator on 2016/11/15.
 */
public class ClassLoaderBase {

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {

        //testSimple();
        testInstance();
    }

    /*
        ClassLoader与Class的简单引用
        反射效果
     */
    private static void testSimple() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        //示例：通过ClassLoader获取类的实例
        ClassLoader classLoader1 = First.class.getClassLoader();
        Class c1 = classLoader1.loadClass("com.first.First");
        First first = (First) c1.newInstance();
        System.out.println("目标：" + first.GetTarget());
    }

    /*
        1、验证同一个Class，从不同ClassLoder加载，虽然是同一份class字节码文件，
            但是由于被两个不同的ClassLoader实例所加载，所以JVM认为它们就是两个不同的类
        2、实现自定义ClassLoader--NetworkClassLoader，从远程服务器加载Class
     */
    private static void testInstance() {
        try {
            //测试加载网络中的class文件
            String rootUrl = "http://localhost:8080/httpweb/classes";
            String className = "com.base.classLoader";
            NetworkClassLoader ncl1 = new NetworkClassLoader(rootUrl);
            NetworkClassLoader ncl2 = new NetworkClassLoader(rootUrl);
            Class<?> clazz1 = ncl1.loadClass(className);
            Class<?> clazz2 = ncl2.loadClass(className);
            Object obj1 = clazz1.newInstance();
            Object obj2 = clazz2.newInstance();
            clazz1.getMethod("setNetClassLoaderSimple", Object.class).invoke(obj1, obj2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
