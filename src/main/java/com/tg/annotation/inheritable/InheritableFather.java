package com.tg.annotation.inheritable;

/**
 * Created by linzc on 2017/5/3.
 */
@Inheritable
public class InheritableFather {
    public InheritableFather() {
        // InheritableBase是否具有 Inheritable Annotation
        System.out.println("InheritableFather:" + InheritableFather.class.isAnnotationPresent(Inheritable.class));
    }
}
