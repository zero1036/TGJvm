package com.tg.base.function.functionInterfaces;

/**
 * Function2
 * Created by linzc on 2017/8/4.
 */
@FunctionalInterface
public interface Function2<P1, P2, P3, R> {
    R apply(P1 p1, P2 p2, P3 p3);
}
