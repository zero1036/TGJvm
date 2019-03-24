package com.tg.annotation.inside;

import java.lang.annotation.*;

/**
 * Created by linzc on 2019/2/1.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface InsideAnno {
    String name();
}
