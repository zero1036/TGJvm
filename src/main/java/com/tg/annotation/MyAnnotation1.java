package com.tg.annotation;

import java.lang.annotation.*;

/**
 * 第一个注解
 * Created by linzc on 2017/5/3.
 */
@Documented
//public enum ElementType {
//    TYPE,               /* 类、接口（包括注释类型）或枚举声明  */
//
//    FIELD,              /* 字段声明（包括枚举常量）  */
//
//    METHOD,             /* 方法声明  */
//
//    PARAMETER,          /* 参数声明  */
//
//    CONSTRUCTOR,        /* 构造方法声明  */
//
//    LOCAL_VARIABLE,     /* 局部变量声明  */
//
//    ANNOTATION_TYPE,    /* 注释类型声明  */
//
//    PACKAGE             /* 包声明  */
//}
@Target(ElementType.TYPE)//注解类型有以上
//public enum RetentionPolicy {
//    SOURCE,            /* Annotation信息仅存在于编译器处理期间，编译器处理完之后就没有该Annotation信息了  */
//
//    CLASS,             /* 编译器将Annotation存储于类对应的.class文件中。默认行为  */
//
//    RUNTIME            /* 编译器将Annotation存储于class文件中，并且可由JVM读入 */
//}
@Retention(RetentionPolicy.RUNTIME)//保留策略
public @interface MyAnnotation1 {
    String name();
}
