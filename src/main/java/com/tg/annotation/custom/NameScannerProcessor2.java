package com.tg.annotation.custom;

import com.google.common.base.Strings;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Thinkpads on 2018/3/15.
 */
public class NameScannerProcessor2 {
    public static void init(Class<?> clazz) {
        if (clazz.isAnnotationPresent(NameScanner.class)) {
            //扫描类型
            print("type", (NameScanner) clazz.getAnnotation(NameScanner.class), clazz.getName());

            //扫描字段
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(NameScanner.class)) {
                    print("field", (NameScanner) field.getAnnotation(NameScanner.class), field.getName());
                }

            }

            //扫描函数
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(NameScanner.class)) {
                    print("method", (NameScanner) method.getAnnotation(NameScanner.class), method.getName());
                }
            }
        }
    }

    private static void print(String type, NameScanner scanner, String name) {
        name = !Strings.isNullOrEmpty(scanner.name()) ? scanner.name() : name;
        System.out.println(type + " result:" + name);
    }
}
