package com.base;

import com.first.First;

/**
 * Created by Administrator on 2016/11/15.
 */
public class ClassLoaderBase {

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        //示例：通过ClassLoader获取类的实例
        ClassLoader classLoader1 = First.class.getClassLoader();
        Class c1 = classLoader1.loadClass("com.first.First");
        First first = (First) c1.newInstance();
        System.out.println("目标：" + first.GetTarget());
    }
}
