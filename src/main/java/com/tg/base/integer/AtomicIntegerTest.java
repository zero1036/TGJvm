package com.tg.base.integer;

import org.junit.Test;

import java.security.SecureRandom;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by linzc on 2018/9/18.
 */
public class AtomicIntegerTest {
    private static final AtomicInteger NEXT_COUNTER = new AtomicInteger((new SecureRandom()).nextInt());

    @Test
    public void test() {
        int target = NEXT_COUNTER.getAndIncrement();
        System.out.println(target);

        target = NEXT_COUNTER.getAndIncrement();
        System.out.println(target);

        target = NEXT_COUNTER.getAndIncrement();
        System.out.println(target);
    }


}
